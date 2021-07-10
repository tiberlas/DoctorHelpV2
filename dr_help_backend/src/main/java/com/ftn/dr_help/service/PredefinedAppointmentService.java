package com.ftn.dr_help.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.dto.NurseWIthFirstFreeDateInnerDTO;
import com.ftn.dr_help.dto.PredefinedAppointmentDTO;
import com.ftn.dr_help.dto.PredefinedAppointmentResponseDTO;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.enums.CreatingPredefinedAppointmentEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;

@Service
public class PredefinedAppointmentService {

	@Autowired
	private AppointmentRepository repository;
	
	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	@Autowired
	private ProcedureTypeRepository procedureRepository;
	
	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private RoomService roomService;
	
	public List<PredefinedAppointmentDTO> getAll(Long id) {
		if(id == null) {
			return null;
		}
		
		List<PredefinedAppointmentDTO> ret = new ArrayList<PredefinedAppointmentDTO>();
		List<AppointmentPOJO> all = repository.findAllPredefined();
		
		for(AppointmentPOJO appointment : all) {
			if(appointment.getRoom().getClinic().getId().equals(id)) {
				ret.add(new PredefinedAppointmentDTO(appointment));
			}
		}
		
		return ret;
	}
	
	public PredefinedAppointmentResponseDTO save(PredefinedAppointmentDTO newPredefined, String email) {
		try {
			Calendar cal = Calendar.getInstance();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
	
			cal.setTime(sdf.parse(newPredefined.getDateAndTime()));
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
	    	String AppointmentRequestingDate = dateConverter.dateForFrontEndString(cal);
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MILLISECOND, 0);
			
			if(now.after(cal)) {
				return null;
			}
			
			ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
			ClinicPOJO clinic = admin.getClinic();
	    	if(clinic == null) {
	    		return null;
	    	}
	    	
	    	RoomPOJO room = roomService.findOnePOJO(newPredefined.getRoomId(), email);
	    	if(room == null || !room.getClinic().getId().equals(clinic.getId())) {
	    		return null;
	    	}
	    	
	    	DoctorPOJO doctor = doctorService.findOne(newPredefined.getDoctorId());
	    	if(doctor == null || !doctor.getClinic().getId().equals(clinic.getId())) {
	    		return null;
	    	}
	    	
	    	ProceduresTypePOJO procedureType = procedureRepository.findById(newPredefined.getProcedureTypeId()).orElse(null);
	    	if(procedureType == null || !procedureType.getClinic().getId().equals(clinic.getId())) {
	    		return null;
	    	}
			
			String time = roomService.findFirstFreeScheduleFromDate(room, cal);
			if(!time.equals(AppointmentRequestingDate)) {
				return new PredefinedAppointmentResponseDTO(
						time, 
						CreatingPredefinedAppointmentEnum.ROOM_NOT_FREE);
			}
			
			//provera da li je doktor slobodan
			Calendar doctorsFreeDate = doctorService.checkSchedue(doctor.getEmail(), cal);
			if(!doctorsFreeDate.equals(cal)) {
				return new PredefinedAppointmentResponseDTO(
						dateConverter.dateForFrontEndString(doctorsFreeDate),
						CreatingPredefinedAppointmentEnum.DOCTOR_NOT_FREE);
			}
			
			//provera da li moze neka sestra da bude prisutna na pregledu
	    	Calendar duration = Calendar.getInstance();
	    	duration.setTime(procedureType.getDuration());
	    	NurseWIthFirstFreeDateInnerDTO freeNurse = nurseService.findFreeNurse(cal, duration, room.getClinic().getId());
	    	
	    	if(!freeNurse.getFirstFreeDate().equals(cal)) {
	    		//sestrama ne odgovara termin i predlaze se drugi
	    		return new PredefinedAppointmentResponseDTO(
	    				dateConverter.dateForFrontEndString(cal),
	    				CreatingPredefinedAppointmentEnum.NURSE_NOT_FREE);
	    	}
	    		
	    	//pregled je validan
	    	AppointmentPOJO appointment = new AppointmentPOJO();
	    	appointment.setDate(cal);
    		appointment.setRoom(room);
    		appointment.setDoctor(doctor);
    		appointment.setNurse(freeNurse.getNurse());
    		appointment.setProcedureType(procedureType);
    		appointment.setDiscount(newPredefined.getDisscount());
    		appointment.setStatus(AppointmentStateEnum.AVAILABLE);
    		appointment.setDeleted(false);
    		repository.save(appointment);	    	
	    	
	    	return new PredefinedAppointmentResponseDTO(
	    				dateConverter.dateForFrontEndString(freeNurse.getFirstFreeDate()),
	    				CreatingPredefinedAppointmentEnum.APPROVED);

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void delete(Long id) { 
		AppointmentPOJO finded = repository.findById(id).orElse(null);
		if(finded == null) {
			return;
		}
		
		finded.setDeleted(true);
		repository.save(finded);
	}
}
