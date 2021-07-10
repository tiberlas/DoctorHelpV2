package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.dto.NurseWIthFirstFreeDateInnerDTO;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.service.AppointmentBlessingService;
import com.ftn.dr_help.service.DoctorService;
import com.ftn.dr_help.service.NurseService;

@Component
public class AutomaticallyReserveRooms {
	
	@Autowired 
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private CheckDoctorsInterface checkDoctors;
	
	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private CheckRooms checkRooms;
	
	@Autowired
	private AppointmentBlessingService blessing;
	
	@Autowired
	private DoctorService doctorService;
	
	@Scheduled(cron="59 59 23 * * ?")
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public void runAtMidnight() {
		/*
		 * u ponoc se pokrece funkcija i svi appointmentimenti se automatski blagosiljaju 
		 * */
		System.out.println("TIME FOR MAGIC");
		
		try {
			List<AppointmentPOJO> appointments = appointmentRepository.getAllRequests();
		
			for(AppointmentPOJO appointment : appointments) {
				System.out.println(appointment.getId());
				
				Long clinicId = appointment.getProcedureType().getClinic().getId();
				Long procedureId = appointment.getProcedureType().getId();
				Calendar requestedTime = appointment.getDate();
				Calendar duration = Calendar.getInstance();
				duration.setTime(appointment.getProcedureType().getDuration());
				
				FreeDoctorForAutomaticallyReserving doctorWithDate = null;
				
				if(appointment.getDoctor() != null) {
					//pregled ima zeljenog doktora pa njega uzmemo u obzir
					Calendar freeDateForDoctor = doctorService.checkSchedue(appointment.getDoctor().getEmail(), requestedTime);
					if(!freeDateForDoctor.equals(requestedTime)) {
						requestedTime = (Calendar) freeDateForDoctor.clone();
					}
					doctorWithDate = new FreeDoctorForAutomaticallyReserving(null, appointment.getDoctor());
					
				} else {
					doctorWithDate = checkDoctors.findFreeDoctor(requestedTime, clinicId, procedureId);
					if(doctorWithDate.getRecomendedDate() != null) {
						//definisemo novo vreme
						requestedTime = (Calendar) doctorWithDate.getRecomendedDate().clone();
					}
				}
				
				NurseWIthFirstFreeDateInnerDTO nurseWithDate = nurseService.findFreeNurse(requestedTime, duration, clinicId);
				if(!nurseWithDate.getFirstFreeDate().equals(requestedTime)) {
					//definisemo novo vreme
					requestedTime = (Calendar) nurseWithDate.getFirstFreeDate().clone();
				}
				
				FreeRoomWithDate freeRoomWithDate =  checkRooms.findFirstFreeRoom(requestedTime, clinicId, procedureId);
				if(freeRoomWithDate.getRecomendedDate() != null) {
					requestedTime = (Calendar) freeRoomWithDate.getRecomendedDate().clone();
				}
				
				appointment.setDate(requestedTime);
				appointment.setDoctor(doctorWithDate.getDoctor());
				appointment.setNurse(nurseWithDate.getNurse());
				appointment.setRoom(freeRoomWithDate.getFreeRoom());
				appointment.setDeleted(false);
				
				if(appointment.getStatus() == AppointmentStateEnum.REQUESTED) {
					appointment.setStatus(AppointmentStateEnum.BLESSED);
				} else {
					appointment.setStatus(AppointmentStateEnum.APPROVED);
				}
				
				appointmentRepository.save(appointment);
				
				//potvrdjujemo pregled
				blessing.scheduleAndSendMail(appointment);
				
			}
		
		} catch(Exception e) {
			System.out.println("CHRON FAILED");
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="59 59 23 * * ?")
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public void runOperationsAtMidnight() {
		
		List<OperationPOJO> operations = operationRepository.getAllOperationRequests();
		for (OperationPOJO operation : operations) {
			Long clinicId = operation.getOperationType().getClinic().getId();
			Long procedureId = operation.getOperationType().getId();
			
			Calendar requestedTime = operation.getDate();
			Calendar duration = Calendar.getInstance();
			duration.setTime(operation.getOperationType().getDuration());
			
			FreeDoctorForAutomaticallyReserving doctorsWithDate = null;
			
			doctorsWithDate = checkDoctors.findFreeDoctors(requestedTime, clinicId, procedureId);
			if(doctorsWithDate.getRecomendedDate() != null) {
				//definisemo novo vreme
				requestedTime = (Calendar) doctorsWithDate.getRecomendedDate().clone();
			}
			
			FreeRoomWithDate freeRoomWithDate =  checkRooms.findFirstFreeRoom(requestedTime, clinicId, procedureId);
			if(freeRoomWithDate.getRecomendedDate() != null) {
				requestedTime = (Calendar) freeRoomWithDate.getRecomendedDate().clone();
			}
			
			blessing.scheduleOperationAndSendMail(operation, 
					doctorsWithDate.getDoctor1(), 
					doctorsWithDate.getDoctor2(), 
					doctorsWithDate.getDoctor3(), 
					freeRoomWithDate.getFreeRoom(), 
					requestedTime);
		}
	}
	
	
}
