package com.ftn.dr_help.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.AppointmentForSchedulingDTO;
import com.ftn.dr_help.dto.AppointmentInternalBlessedDTO;
import com.ftn.dr_help.dto.NurseWIthFirstFreeDateInnerDTO;
import com.ftn.dr_help.model.enums.AppointmentBlessing;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.enums.OperationStatus;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;

@Service
public class AppointmentBlessingService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private ProcedureTypeRepository procedureRepository;
	
	@Autowired
	private ClinicAdministratorRepository clinicAdminRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private Mail mailSender;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public AppointmentInternalBlessedDTO blessing(AppointmentForSchedulingDTO requested, String adminMeil) {
		/**
		 * provera da li requested appointment odgovara sobi i doktoru; 
		 * dodeli medicinsku sestru appointmentu;
		 * ako je sve uredu stavi stanje appointmenta na BLESSED; a ako doktoru ili sobi ne odgovara termin
		 * vrati prvi slobdan termin koji im odgovara;
		 * takodje ako ni jedna medicinska sestra ne moze da prisustvuje za requested appointment onda se vrati priv slobodan termin
		 * 
		 * */
		try {
			
			Calendar date = dateConverter.stringToDate(requested.getDateAndTime());
			String dateString = dateConverter.dateForFrontEndString(date);
			
			RoomPOJO room = roomService.findOnePOJO(requested.getRoomId(), adminMeil);
			String freeRoom = roomService.findFirstFreeScheduleFromDate(room, date);
			
			if(!freeRoom.equals(dateString)) {
				return new AppointmentInternalBlessedDTO(AppointmentBlessing.BAD_DATE, freeRoom);
			}
			
			Calendar freeDoctor = doctorService.checkSchedue(requested.getDoctorEmail(), date);
			String freeDoctorString = dateConverter.dateForFrontEndString(freeDoctor);
			
			if(!freeDoctorString.equals(dateString)) {
				return new AppointmentInternalBlessedDTO(AppointmentBlessing.BAD_DOCTOR, freeDoctorString);
			}
			
			Calendar duration = Calendar.getInstance();
			duration.setTime(procedureRepository.getOne(requested.getProcedureId()).getDuration());
			Long clinicId = clinicAdminRepository.findOneByEmail(adminMeil).getClinic().getId();
			NurseWIthFirstFreeDateInnerDTO freeNurse = nurseService.findFreeNurse(date, duration, clinicId);
			
			if(!freeNurse.getFirstFreeDate().getTime().equals(date.getTime())) {
				String freeDateString = dateConverter.dateForFrontEndString(freeNurse.getFirstFreeDate());
				return new AppointmentInternalBlessedDTO(AppointmentBlessing.BAD_DATE, freeDateString);
			}

			DoctorPOJO doctor = doctorRepository.findOneByEmail(requested.getDoctorEmail());
			NursePOJO nurse = freeNurse.getNurse();

			AppointmentPOJO appointment = appointmentRepository.getOne(requested.getAppointmentRequestedId());
			if(appointment.getRoom() != null) {
				throw new Exception();
			}

			appointment.setDate(date);
			appointment.setDoctor(doctor);
			appointment.setNurse(nurse);
			appointment.setRoom(room);
			appointment.setDeleted(false);
			
			if(appointment.getStatus() == AppointmentStateEnum.REQUESTED) {
				appointment.setStatus(AppointmentStateEnum.BLESSED);
			} else {
				appointment.setStatus(AppointmentStateEnum.APPROVED);
			}
			
			
			appointmentRepository.save(appointment); //update, moguci konflikt
			
			scheduleAndSendMail(appointment);
			
			return new AppointmentInternalBlessedDTO(AppointmentBlessing.BLESSED, "BLESSING RECIVED");
		} catch(Exception e) {
			e.printStackTrace();
			return new AppointmentInternalBlessedDTO(AppointmentBlessing.REFFUSED, "NOT WORTHY");
		}
	}
	
	public void scheduleAndSendMail(AppointmentPOJO appointment) {
		/*
		 * sending required mails
		 * */
		if(appointment.getStatus() == AppointmentStateEnum.REQUESTED) {
			//pacijent je trazio pregled, pa pitamo da li njemu odgovara
			
			mailSender.sendAppointmentBlessedEmail(appointment);
		} else {
			//doktor je trazio pregled pa ih obavestavamo o ishodu
			
			mailSender.sendAppointmentApprovedToDoctorEmail(appointment);
			mailSender.sendAppointmentApprovedToNurseEmail(appointment);
			mailSender.sendAppointmentApprovedToPatientEmail(appointment);
		}			
		
	}
	
	public void scheduleOperationAndSendMail(OperationPOJO operation, DoctorPOJO doctor1, DoctorPOJO doctor2, DoctorPOJO doctor3, RoomPOJO room, Calendar date) {
		operation.setDate(date);
		operation.setFirstDoctor(doctor1);
		operation.setSecondDoctor(doctor2);
		operation.setThirdDoctor(doctor3);
		
		operation.setRoom(room);
		operation.setDeleted(false);
		operation.setStatus(OperationStatus.APPROVED);
		
		mailSender.sendOperationApprovedToDoctorsEmail(doctor1, operation);
		mailSender.sendOperationApprovedToDoctorsEmail(doctor2, operation);
		mailSender.sendOperationApprovedToDoctorsEmail(doctor3, operation);
		
		mailSender.sendOperationApprovedToPatientEmail(operation);
		
		operationRepository.save(operation);
		
	}
}
