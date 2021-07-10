package com.ftn.dr_help.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.schedule.CalculateFirstFreeSchedule;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.DoctorAppointmentDTO;
import com.ftn.dr_help.dto.DoctorRequestAppointmentDTO;
import com.ftn.dr_help.dto.ExaminationReportDTO;
import com.ftn.dr_help.dto.MedicationDisplayDTO;
import com.ftn.dr_help.dto.PatientHistoryDTO;
import com.ftn.dr_help.dto.RequestingAppointmentDTO;
import com.ftn.dr_help.dto.nurse.NurseAppointmentDTO;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DiagnosisPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.DoctorRequestedAppointmentPOJO;
import com.ftn.dr_help.model.pojo.ExaminationReportPOJO;
import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.PerscriptionPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.TherapyPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.DiagnosisRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.ExaminationReportRepository;
import com.ftn.dr_help.repository.MedicationRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.repository.PerscriptionRepository;
import com.ftn.dr_help.repository.RequestedAppointmentsRepository;
import com.ftn.dr_help.repository.TherapyRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private RequestedAppointmentsRepository requestedAppointmentsReposotory;
	
	@Autowired
	private DiagnosisRepository diagnosisRepository;
	
	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private PerscriptionRepository perscriptionRepository;
	
	@Autowired
	private ExaminationReportRepository examinationRepository;
	
	@Autowired
	private TherapyRepository therapyRepository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private CalculateFirstFreeSchedule calculateSchedule;
	
	@Autowired
	private WorkScheduleAdapter workSchedule;
	
	@Autowired
	private LeaveRequestService leaveRequestService;
	
	public List<DoctorAppointmentDTO> findDoctorAppointments(Long doctor_id) {
		
		List<AppointmentPOJO> list = appointmentRepository.findDoctorAppointments(doctor_id);
		
		List<DoctorAppointmentDTO> appointments = new ArrayList<DoctorAppointmentDTO>();
		
		for (AppointmentPOJO appointmentPOJO : list) {
				DoctorAppointmentDTO dto = convertAppointmentToDoctorDTO(appointmentPOJO);
				appointments.add(dto);
		}
		return appointments;
	}
	
	public List<DoctorAppointmentDTO> findApprovedDoctorAppointmentsWithPatientId(Long patient_id, Long doctor_id) {
		
		List<AppointmentPOJO> list = appointmentRepository.findApprovedDoctorAppointmentsForPatientWithId(doctor_id, patient_id);
		
		List<DoctorAppointmentDTO> appointments = new ArrayList<DoctorAppointmentDTO>();
		
		for (AppointmentPOJO appointmentPOJO : list) {
				DoctorAppointmentDTO dto = convertAppointmentToDoctorDTO(appointmentPOJO);
				appointments.add(dto);
		}
		
		return appointments;
	}
	
	public List<DoctorAppointmentDTO> findDoctorDoneAppointmentsForPatientWithId(Long patient_id) { 
			
			List<AppointmentPOJO> list = appointmentRepository.findDoneAppointmentsForPatientWithId(patient_id);
			
			System.out.println("Done appointments za doktora");
			List<DoctorAppointmentDTO> appointments = new ArrayList<DoctorAppointmentDTO>();
			for (AppointmentPOJO appointmentPOJO : list) {
					DoctorAppointmentDTO dto = convertAppointmentToDoctorDTO(appointmentPOJO);
					appointments.add(dto);
			}
			
			return appointments;
	}

	public List<NurseAppointmentDTO> findNurseDoneAppointmentsForPatientWithId(Long patient_id) { 
		
		List<AppointmentPOJO> list = appointmentRepository.findDoneAppointmentsForPatientWithId(patient_id);
		
		System.out.println("Done appointments za nurse");
		List<NurseAppointmentDTO> appointments = new ArrayList<NurseAppointmentDTO>();
		for (AppointmentPOJO appointmentPOJO : list) {
				NurseAppointmentDTO dto = convertAppointmentToNurseDTO(appointmentPOJO);
				appointments.add(dto);
		}
		
		return appointments;
	}
	public List<NurseAppointmentDTO> findNurseAppointments(Long nurse_id) {
		
		List<AppointmentPOJO> list = appointmentRepository.findNurseAppointments(nurse_id);
		List<NurseAppointmentDTO> appointments = new ArrayList<NurseAppointmentDTO>();
		
		for (AppointmentPOJO appointmentPOJO : list) {
				NurseAppointmentDTO dto = convertAppointmentToNurseDTO(appointmentPOJO);
				appointments.add(dto);
		}
		return appointments;
	}
	
	
	public AppointmentPOJO finishAppointment(Long appointmentId, ExaminationReportDTO dto) {
		
		AppointmentPOJO app = appointmentRepository.findOneById(appointmentId);
		
		PerscriptionPOJO perscription = new PerscriptionPOJO();
		DiagnosisPOJO diagnosis = diagnosisRepository.findOneByDiagnosis(dto.getDiagnosis());
		perscription.setDiagnosis(diagnosis);
		perscription.setSigningNurse(null);
		
		List<MedicationPOJO> medicationList = new ArrayList<MedicationPOJO>();
		for (String medication : dto.getMedicationList()) {
			MedicationPOJO med = medicationRepository.findOneByMedicationName(medication).orElse(null);
			medicationList.add(med);
		}
		
		perscription.setMedicationList(medicationList);
		
		
		TherapyPOJO therapy = new TherapyPOJO();
		therapy.setAdvice(dto.getNote());
		perscription.setTherapy(therapy);
		
		ExaminationReportPOJO report = new ExaminationReportPOJO();
		report.setAppointment(app);
		report.setHealthRecord(app.getPatient().getHealthRecord());
		report.setPerscription(perscription);
		report.setClinic(app.getDoctor().getClinic());
		
		examinationRepository.save(report);

		perscription.setExaminationReport(report);
		perscription.setDiagnosis(diagnosis);
		
		perscriptionRepository.save(perscription);
		
		therapyRepository.save(therapy);
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dto.getDateStart());
		app.setDate(cal);
		
		
		app.setExaminationReport(report);
		app.setStatus(AppointmentStateEnum.DONE);
		
		appointmentRepository.save(app);
		
		return app;
	}
	
	private NurseAppointmentDTO convertAppointmentToNurseDTO(AppointmentPOJO appointment) {
		NurseAppointmentDTO dto = new NurseAppointmentDTO();
		
		DoctorPOJO doctor = appointment.getDoctor();
		
		if(doctor == null) {
			return null;
		} 
		
		dto.setDoctor_id(doctor.getId());
		dto.setDoctorFirstName(doctor.getFirstName());
		dto.setDoctorLastName(doctor.getLastName());
		
		PatientPOJO patient = appointment.getPatient();
		if(patient == null) {
			dto.setPatientFirstName("-");
			dto.setPatientLastName("-");
			
		}
		else {
			dto.setPatientFirstName(patient.getFirstName());
			dto.setPatientLastName(patient.getLastName());
			dto.setInsuranceNumber(String.valueOf(patient.getInsuranceNumber()));
		}
		
		ProceduresTypePOJO pt = appointment.getProcedureType();
		dto.setProcedureName(pt.getName());
		
		dto.setStartDate(appointment.getDate().getTime());
		System.out.println("START IS" + appointment.getDate().getTime());
		
		Calendar end = Calendar.getInstance(); // creates calendar
		end.setTime(appointment.getDate().getTime()); // sets calendar time/date
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(pt.getDuration());
		end.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR)); //dodaje sate
		end.add(Calendar.MINUTE, duration.get(Calendar.MINUTE)); //dodaje minute
		System.out.println("AND END IS: " + end.getTime());
		dto.setEndDate(end.getTime());
		
		
		dto.setProcedureName(pt.getName());
		
		dto.setPrice(String.valueOf(appointment.getProcedureType().getPrice()));
		System.out.println("PRICE IS: " + dto.getPrice());
		dto.setDiscount(String.valueOf(appointment.getDiscount()));
		System.out.println("DISCOUNT IS: " + dto.getDiscount());
		dto.setStatus(String.valueOf(appointment.getStatus()));
		
		dto.setRoomName(appointment.getRoom().getName());
		dto.setRoomNumber(String.valueOf(appointment.getRoom().getNumber()));
		
		dto.setAppointment_id(appointment.getId());
		
		
		return dto;
	}
	
	public boolean addAppointment (Long doctorId, String dateString, Long patientId) throws ParseException {

		DoctorPOJO doctor = doctorRepository.getOne(doctorId);
			
		AppointmentPOJO newAppointment = new AppointmentPOJO ();
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		Date date = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		newAppointment.setDate(calendar);
		newAppointment.setDeleted(false);
		newAppointment.setDoctor(doctor);
		newAppointment.setPatient(patientRepository.getOne(patientId));
		newAppointment.setStatus(AppointmentStateEnum.REQUESTED);
		newAppointment.setProcedureType(doctor.getProcedureType());
		newAppointment.setRoom(null);
		newAppointment.setNurse(null);
		newAppointment.setDiscount(-1); //ne diraj
		newAppointment.setExaminationReport(null);
		
		Shift shift = null;		
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY: 
				shift = doctor.getMonday();
				break;
			case Calendar.TUESDAY: 
				shift = doctor.getTuesday();
				break;
			case Calendar.WEDNESDAY: 
				shift = doctor.getWednesday();
				break;
			case Calendar.THURSDAY: 
				shift = doctor.getThursday();
				break;
			case Calendar.FRIDAY: 
				shift = doctor.getFriday();
				break;
			case Calendar.SATURDAY: 
				shift = doctor.getSaturday();
				break;
			case Calendar.SUNDAY: 
				shift = doctor.getSunday();
				break;
		}
		
		
		Date d = newAppointment.getProcedureType().getDuration();
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(d);
		
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		startTime.setTime(newAppointment.getDate().getTime());
		endTime.setTime(newAppointment.getDate().getTime());
		
		startTime.add(Calendar.HOUR_OF_DAY, - duration.get(Calendar.HOUR_OF_DAY));
		startTime.add(Calendar.MINUTE, - duration.get(Calendar.MINUTE) + 1);
		
		endTime.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
		endTime.add(Calendar.MINUTE, duration.get(Calendar.MINUTE) - 1);
		
		if ((startTime.get(Calendar.YEAR) != newAppointment.getDate().get(Calendar.YEAR)) 
					|| (startTime.get(Calendar.MONTH) != newAppointment.getDate().get(Calendar.MONTH)) 
					|| (startTime.get(Calendar.DAY_OF_YEAR) != newAppointment.getDate().get(Calendar.DAY_OF_YEAR))) {				
			startTime.add(Calendar.DAY_OF_MONTH, 1);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
		}
		if ((endTime.get(Calendar.YEAR) != newAppointment.getDate().get(Calendar.YEAR)) 
					|| (endTime.get(Calendar.MONTH) != newAppointment.getDate().get(Calendar.MONTH)) 
					|| (endTime.get(Calendar.DAY_OF_YEAR) != newAppointment.getDate().get(Calendar.DAY_OF_YEAR))) {				
			endTime.add(Calendar.DAY_OF_MONTH, -1);
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}
		
		if (insertNewAppointment(newAppointment, shift, doctor, startTime, endTime)) {
			return true;
		} 
		
		return false;
	}
	
	@Transactional (isolation = Isolation.READ_UNCOMMITTED)
	public Boolean insertNewAppointment (AppointmentPOJO newAppointment, Shift shift, DoctorPOJO doctor, Calendar startTime, Calendar endTime) {
		
		
		List<AppointmentPOJO> appointments = appointmentRepository.getDoctorsAppointments(doctor.getId(), startTime, endTime);
		
//		System.out.println("");
//		System.out.println("");
//		System.out.println("Before deciding: ");
//		System.out.println(doctor.getId());
//		System.out.println(startTime.getTime());
//		System.out.println(endTime.getTime());
//		System.out.println("");
//		System.out.println("");
		
		if (appointments.size() == 0) {
			appointmentRepository.save(newAppointment);
			return true;
		}
		
		return false;
	}
	
	
	
	private DoctorAppointmentDTO convertAppointmentToDoctorDTO(AppointmentPOJO appointment) {
		
		DoctorAppointmentDTO dto = new DoctorAppointmentDTO();
		
		DoctorPOJO doctor = appointment.getDoctor();
		
		if(doctor == null) {
			return null;
		} 
		
		System.out.println("TKO JE DOCA" + doctor.getFirstName());
		dto.setDoctorId(doctor.getId());
		dto.setDoctorFirstName(doctor.getFirstName());
		dto.setDoctorLastName(doctor.getLastName());
		
		if(appointment.getStatus().equals(AppointmentStateEnum.APPROVED) 
				|| appointment.getStatus().equals(AppointmentStateEnum.AVAILABLE)
				|| appointment.getStatus().equals(AppointmentStateEnum.DONE)) {
			NursePOJO nurse = appointment.getNurse();
			dto.setNurseFirstName(nurse.getFirstName());
			dto.setNurseLastName(nurse.getLastName());
		}
		
		
		PatientPOJO patient = appointment.getPatient();
		if(patient == null) {
			dto.setPatientFirstName("-");
			dto.setPatientLastName("-");
			
		}
		else {
			dto.setPatientFirstName(patient.getFirstName());
			dto.setPatientLastName(patient.getLastName());
			dto.setInsuranceNumber(String.valueOf(patient.getInsuranceNumber()));
		}
		
		ProceduresTypePOJO pt = appointment.getProcedureType();
		dto.setProcedureName(pt.getName());
		
		dto.setStartDate(appointment.getDate().getTime());
		System.out.println("START IS" + appointment.getDate().getTime());
		
		Calendar end = Calendar.getInstance(); // creates calendar
		end.setTime(appointment.getDate().getTime()); // sets calendar time/date
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(pt.getDuration());
		end.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR)); //dodaje sate
		end.add(Calendar.MINUTE, duration.get(Calendar.MINUTE)); //dodaje minute
		dto.setEndDate(end.getTime());
		
		
		dto.setProcedureName(pt.getName());
		
		dto.setPrice(String.valueOf(appointment.getProcedureType().getPrice()));
		dto.setDiscount(String.valueOf(appointment.getDiscount()));
		dto.setStatus(String.valueOf(appointment.getStatus()));
		
		dto.setIsOperation(pt.isOperation());
		
		if(!appointment.getStatus().equals(AppointmentStateEnum.DOCTOR_REQUESTED_APPOINTMENT)) {
			dto.setRoomName(appointment.getRoom().getName());
			dto.setRoomNumber(String.valueOf(appointment.getRoom().getNumber()));
		}
		
		dto.setAppointment_id(appointment.getId());
		
		return dto;
		
	}
	
	@Transactional
	public void delete (Long appointmentId) {
		AppointmentPOJO appointment = appointmentRepository.findOneById(appointmentId);
		if (appointment.getStatus() != AppointmentStateEnum.APPROVED) {
			appointmentRepository.deleteAppointment (appointmentId);
		}
		else {
			appointmentRepository.cancelAppointment(appointmentId);
		}
	}
	
	public boolean doctorRequestAppointment(DoctorRequestAppointmentDTO request) {
		try {
			
			AppointmentPOJO old = appointmentRepository.findOneById(request.getOldAppointmentID());
			System.out.println(request.getOldAppointmentID());
			System.out.println(old == null);
			System.out.println(old.getId());
			AppointmentPOJO newRequested = new AppointmentPOJO();
			
			Calendar date = dateConverter.stringToDate(request.getDateAndTime());
			
			//provera da li je doca slobodan
			List<Date> dates = doctorRepository.findAllReservedAppointments(old.getDoctor().getId());
			List<AbsenceInnerDTO> absence = leaveRequestService.getAllDoctorAbsence(old.getDoctor().getId());
			Calendar retVal = calculateSchedule.checkScheduleOrFindFirstFree(workSchedule.fromDoctor(old.getDoctor()), date, dates, absence);
			if(!retVal.equals(date)) {
				return false;
			}
			
			newRequested.setDate(date);
			newRequested.setDeleted(false);
			newRequested.setDiscount(-1);
			newRequested.setDoctor(old.getDoctor());
			newRequested.setPatient(old.getPatient());
			newRequested.setProcedureType(old.getProcedureType());
			newRequested.setStatus(AppointmentStateEnum.DOCTOR_REQUESTED_APPOINTMENT);
			
			//spoji appointment sa doktorom koji trazi
			DoctorRequestedAppointmentPOJO requestApp = new DoctorRequestedAppointmentPOJO();
			requestApp.setDoctor(old.getDoctor());
			requestApp.setAppointment(newRequested);
			requestedAppointmentsReposotory.save(requestApp);
			
			appointmentRepository.save(newRequested);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getAppointmentType(Long id) {
		AppointmentPOJO appointment = appointmentRepository.findOneById(id);
		
		return appointment.getProcedureType().getName();
	}
	
	public boolean canDelete(String email, Long id) {
		
		try {

			AppointmentPOJO appointment = appointmentRepository.getRequestedAppointment(email, id);
			if(appointment != null && appointment.isDeleted() == false && appointment.getStatus() != AppointmentStateEnum.DONE) {
				Calendar now = Calendar.getInstance();
				Calendar time = (Calendar) appointment.getDate().clone();
				time.add(Calendar.DAY_OF_MONTH, -1);
				
				if( now.before(time)) {
					return true;
				}
				
			}
			
			return false;
		} catch(Exception e) {
			return false;
		}
		
	}
	
	public boolean deleteRequested(String email, Long id) {
		try {
			
			AppointmentPOJO appointment = appointmentRepository.getRequestedAppointment(email, id);
			if(appointment != null && appointment.isDeleted() == false && appointment.getStatus() != AppointmentStateEnum.DONE) {
				Calendar now = Calendar.getInstance();
				Calendar time = (Calendar) appointment.getDate().clone();
				time.add(Calendar.DAY_OF_MONTH, -1);
					
				if( now.before(time)) {
					appointment.setDeleted(true);
					
					appointmentRepository.save(appointment);
					return true;
				}
					
			}
			
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public List<RequestingAppointmentDTO> getAllRequests(String email) {
		try {
			List<AppointmentPOJO> finded = appointmentRepository.getAllRequests(email);
			List<RequestingAppointmentDTO> requests = new ArrayList<RequestingAppointmentDTO>();
			
			String nurse;
			for(AppointmentPOJO request : finded) {
				if(request.getNurse() == null) {
					nurse = "UNASSIGNED";
				} else {
					nurse = request.getNurse().getEmail();
				}
				
				Calendar duration = Calendar.getInstance();
				duration.setTime(request.getProcedureType().getDuration());
				
				requests.add(new RequestingAppointmentDTO( 
						request.getId(), 
						dateConverter.dateForFrontEndString(request.getDate()), 
						request.getProcedureType().getName(), 
						request.getDoctor().getEmail(), 
						nurse, 
						request.getPatient().getEmail(),
						request.getProcedureType().getId(),
						dateConverter.timeToString(duration)));
			}
			
			return requests;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public RequestingAppointmentDTO getOneRequests(Long id) {
		try {
			
			AppointmentPOJO finded = appointmentRepository.getOne(id);
			
			Calendar duration = Calendar.getInstance();
			duration.setTime(finded.getProcedureType().getDuration());
			String nurse;
			
			if (finded.getNurse() == null) {
				nurse = "UNDEFINED";
			}
			else {
				nurse = finded.getNurse().getEmail();
			}
			
			return new RequestingAppointmentDTO(
					finded.getId(),
					dateConverter.dateForFrontEndString(finded.getDate()), 
					finded.getProcedureType().getName(), 
					finded.getDoctor().getEmail(), 
					nurse, 
					finded.getPatient().getEmail(),
					finded.getProcedureType().getId(),
					dateConverter.timeToString(duration));
		} catch(Exception e) {
			return null;
		}
	}
	
	public ExaminationReportDTO findExaminationReportForDoneAppointment(Long appointmentId, Long doctor_id) {
		AppointmentPOJO appointment = appointmentRepository.findOneById(appointmentId);
		PerscriptionPOJO perscription = appointment.getExaminationReport().getPerscription();
		
		
		ExaminationReportDTO dto = new ExaminationReportDTO();
		
		if(appointment.getDoctor().getId() == doctor_id) {
			dto.setMyExamination(true); 
		} else {
			dto.setMyExamination(false);
		}
		
		if(perscription.getDiagnosis() != null) {
			dto.setDiagnosis(perscription.getDiagnosis().getDiagnosis());
		} else {
			dto.setDiagnosis("");
		}
		
		List<MedicationDisplayDTO> medicationList = new ArrayList<MedicationDisplayDTO>();
		if(perscription.getMedicationList() != null) {
		
			for (MedicationPOJO m : perscription.getMedicationList()) {
				MedicationDisplayDTO mdDTO = new MedicationDisplayDTO();
				mdDTO.setMedicationName(m.getMedicationName());
				mdDTO.setMedicationDescription(m.getMedDescription());
				medicationList.add(mdDTO);
			}
			
			dto.setMedicationArray(medicationList);
			
			
		}
		
		if (medicationList.size() == 0) {
			medicationList.add(new MedicationDisplayDTO ("-", "-"));
		}
		
		
		TherapyPOJO therapy = perscription.getTherapy();
		if (therapy != null) {
			dto.setNote(therapy.getAdvice());
		} else {
			dto.setNote("");
		}
		
		if(perscription.getSigningNurse() == null) {
			dto.setNurseSigned(false);
		} else {
			dto.setNurseSigned(true);
			dto.setNurse(appointment.getNurse().getFirstName() + " " + appointment.getNurse().getLastName());
			dto.setNurseId(appointment.getNurse().getId());
		}
		
		return dto;
	}
	
	public boolean updateExaminationReportDTO(Long appointment_id, ExaminationReportDTO dto) {
		AppointmentPOJO appointment = appointmentRepository.findOneById(appointment_id);
		
		if(appointment.isDeleted()) {
			return false;
		}
		
		PerscriptionPOJO perscription = appointment.getExaminationReport().getPerscription();
		if(perscription == null) {
			return false;
		}
		System.out.println("Note is: " + dto.getNote());
		perscription.getTherapy().setAdvice(dto.getNote()); //set the advice
		perscriptionRepository.save(perscription);
		
		return true;
		
	}
	
	
	public List<NurseAppointmentDTO> findAvailableOrApprovedNurseAppointments(Long nurse_id) {
		List<AppointmentPOJO> list = appointmentRepository.findAvailableOrApprovedNurseAppointments(nurse_id);

		System.out.println("Leave request appointments za nurse");
		List<NurseAppointmentDTO> appointments = new ArrayList<NurseAppointmentDTO>();
		for (AppointmentPOJO appointmentPOJO : list) {
				NurseAppointmentDTO dto = convertAppointmentToNurseDTO(appointmentPOJO);
				appointments.add(dto);
		}
		
		return appointments;
	}
	
	
	public List<DoctorAppointmentDTO> findAvailableOrApprovedDoctorAppointments(Long doctor_id) {
		List<AppointmentPOJO> list = appointmentRepository.findAvailableOrApprovedDoctorAppointments(doctor_id);

		System.out.println("Leave request appointments za doktora");
		List<DoctorAppointmentDTO> appointments = new ArrayList<DoctorAppointmentDTO>();
		for (AppointmentPOJO appointmentPOJO : list) {
				DoctorAppointmentDTO dto = convertAppointmentToDoctorDTO(appointmentPOJO);
				appointments.add(dto);
		}
		
		return appointments;
	}

	public AppointmentListDTO getPredefinedAppointments(String doctorId, String procedureTypeId, String clinicId,
			String date) {
		AppointmentListDTO retVal = new AppointmentListDTO ();
		List<AppointmentPOJO> appointmentList = new ArrayList<AppointmentPOJO>();
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		appointmentList = appointmentRepository.getAllPredefinedAppointments();
		for (AppointmentPOJO app : appointmentList) {
			if(app.getDate().get(Calendar.YEAR) < now.get(Calendar.YEAR)){
				
	        }else if (app.getDate().get(Calendar.YEAR) > now.get(Calendar.YEAR)){
	        	retVal.getAppointmentList().add(new PatientHistoryDTO(app));
	        }else{
	            if(app.getDate().get(Calendar.MONTH) > now.get(Calendar.MONTH)){
	            	retVal.getAppointmentList().add(new PatientHistoryDTO(app));
	            }else if(app.getDate().get(Calendar.MONTH) < now.get(Calendar.MONTH)){
	            	
	            }else{
	                if(app.getDate().get(Calendar.DAY_OF_MONTH) < now.get(Calendar.DAY_OF_MONTH)){
	                
	                }
	                else {
	                	retVal.getAppointmentList().add(new PatientHistoryDTO(app));
	                }
	            }
	        }
		}
		System.out.println("");
		System.out.println("");
		
		List<String> dateList = new ArrayList<String>();
		dateList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : dateList) {
				if (str.split(" ")[0].equals(p.getDate().split(" ")[0])) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				dateList.add(p.getDate().split(" ")[0]);
			}
		}
		retVal.setPossibleDates(dateList);
		
		List<String> doctorList = new ArrayList<String> ();
		doctorList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : doctorList) {
				if (str.equals(p.getDoctor())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				doctorList.add(p.getDoctor());
			}
		}
		retVal.setPossibleDoctors(doctorList);
		
		List<String> clinicList = new ArrayList<String> ();
		clinicList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : clinicList) {
				if (str.equals(p.getClinicName())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				clinicList.add(p.getClinicName());
			}
		}
		retVal.setPossibleClinics(clinicList);
		
		List<String> typeList = new ArrayList<String> ();
		typeList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : typeList) {
				if (str.equals(p.getProcedureType())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				typeList.add(p.getProcedureType());
			}
		}
		retVal.setPossibleTypes(typeList);
		
		if (doctorId.equals("unfiltered") && doctorId.equals(procedureTypeId) && doctorId.equals(clinicId) && doctorId.equals(date)) {
			System.out.println("Getting all predefined appointments");
			return retVal;
		}
		
		if (!date.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getDate().split(" ")[0].equals(date + ".")) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!doctorId.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getDoctor().equals(doctorId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!clinicId.contentEquals(("unfiltered"))) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> (); 
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getClinicName().equals(clinicId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!procedureTypeId.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getProcedureType().equals(procedureTypeId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		return retVal;
	}
	
	@Transactional (isolation = Isolation.READ_UNCOMMITTED)
	public Boolean reserveAppointment (Long appointmentId, Long patientId) {
		AppointmentPOJO appointment = appointmentRepository.getOne(appointmentId);

		if (appointment == null) {
			return false;
		}
		
		if (appointment.isDeleted()) {
			return false;
		}
		
	
		if (appointment.getStatus() == AppointmentStateEnum.AVAILABLE) {
			appointmentRepository.reserveAppointment(appointmentId, patientId);
			return true;
		} 

		return false;
	}
	
	@Transactional (isolation = Isolation.READ_COMMITTED)
	public void confirmAppointment (Long appointmentId) {
		appointmentRepository.confirmAppointment(appointmentId);
	}
	
}
