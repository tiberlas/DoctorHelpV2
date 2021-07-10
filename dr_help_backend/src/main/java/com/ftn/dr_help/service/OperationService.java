package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.comon.schedule.CalculateFirstFreeSchedule;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.OperationBlessingDTO;
import com.ftn.dr_help.dto.OperationBlessingInnerDTO;
import com.ftn.dr_help.dto.OperationDoctorRequestDTO;
import com.ftn.dr_help.dto.OperationRequestDTO;
import com.ftn.dr_help.dto.OperationRequestInfoDTO;
import com.ftn.dr_help.dto.ThreeDoctorsIdDTO;
import com.ftn.dr_help.dto.operations.DoctorOperationDTO;
import com.ftn.dr_help.model.enums.OperationBlessing;
import com.ftn.dr_help.model.enums.OperationStatus;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;
import com.ftn.dr_help.repository.RoomRepository;

@Service
public class OperationService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private ProcedureTypeRepository procedureRepository;
	
	@Autowired
	private LeaveRequestService  leaveRequestsService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	@Autowired
	private DateConverter dateConvertor;
	
	@Autowired
	private Mail mailSender;
	
	public boolean doctorRequestAppointment(OperationDoctorRequestDTO request, String emailOfRequestingDoctor) {
		
		try {
			DoctorPOJO requestedDoctor = doctorRepository.findOneByEmail(emailOfRequestingDoctor);
			 	
			AppointmentPOJO appointment = appointmentRepository.findOneById(request.getAppointmentId());
				
			ProceduresTypePOJO operationType = procedureRepository.getOne(request.getProcedureTypeId());
				
			PatientPOJO patient = appointment.getPatient();
				
			if(requestedDoctor == null || operationType == null || patient == null) {
				return false;
			}
				
			OperationPOJO operation = new OperationPOJO();
			operation.setDate(dateConvertor.stringToDate(request.getDateAndTimeString()));
			operation.setDeleted(false);
			operation.setRequestedDoctor(requestedDoctor);
			operation.setPatient(patient);
			operation.setOperationType(operationType);
			operation.setStatus(OperationStatus.REQUESTED);
				
			operationRepository.save(operation);
				
			//poslati mejl adminu
			List<ClinicAdministratorPOJO> admins = requestedDoctor.getClinic().getClinicAdminList();
				
			for(ClinicAdministratorPOJO admin : admins) {
				String requestingDoctorName = requestedDoctor.getFirstName() +" "+ requestedDoctor.getLastName();
				mailSender.sendOperationRequestEmail(
						admin.getEmail(), 
						requestingDoctorName,  
						operationType.getName(), 
						request.getDateAndTimeString());
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("GRESKA: ");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteRequested(Long operationId) {
		try {
			
			OperationPOJO operation = operationRepository.getOne(operationId);
			Calendar start = (Calendar) operation.getDate().clone();
			
			//provera da li ima vise od 24h pre pocetka pregleda
			Calendar now = Calendar.getInstance();
			start.add(Calendar.DAY_OF_YEAR, -1); //24 sate pre operacije
			if( now.before(start)) {
				//moze da se obrise operacija
				operation.setDeleted(true);
				
				operationRepository.save(operation);
				return true;
			}
			
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<OperationRequestInfoDTO> getAllRequests(String adminEmail) {
		try {
			
			List<OperationPOJO> finded = operationRepository.getAllOperationRequestsForAdmin(adminEmail);
			List<OperationRequestInfoDTO> operations = new ArrayList<>();
			
			Calendar duration = Calendar.getInstance();
			for(OperationPOJO operation : finded) {
				duration.setTime(operation.getOperationType().getDuration());

				operations.add(new OperationRequestInfoDTO(
						operation.getId(), 
						dateConvertor.dateForFrontEndString(operation.getDate()), 
						operation.getOperationType().getName(), 
						operation.getOperationType().getId(), 
						operation.getPatient().getEmail(),
						dateConvertor.timeToString(duration)));
			}
			
			return operations;
		} catch(Exception e) {
			return null;
		}
	}
	
	public OperationRequestInfoDTO getOneRequests(Long operaionId) {
		try {
			OperationPOJO finded = operationRepository.getOne(operaionId);
			Calendar duration = Calendar.getInstance();
			duration.setTime(finded.getOperationType().getDuration());

			OperationRequestInfoDTO operation = new OperationRequestInfoDTO(
					finded.getId(), 
					dateConvertor.dateForFrontEndString(finded.getDate()), 
					finded.getOperationType().getName(), 
					finded.getOperationType().getId(), 
					finded.getPatient().getEmail(),
					dateConvertor.timeToString(duration));
			
			return operation;
		} catch(Exception e) {
			return null;
		}
	}
	

	public List<DoctorOperationDTO> findDoctorOperations(Long doctor_id) {
		
		List<OperationPOJO> list = operationRepository.getDoctorOperations(doctor_id);
		
		List<DoctorOperationDTO> operations = new ArrayList<DoctorOperationDTO>();
		
		int i = 0;
		for (OperationPOJO operationPOJO : list) {
				System.out.println("-------------------" + i);
				DoctorOperationDTO dto = convertOperationToDoctorDTO(operationPOJO);
				operations.add(dto);
				i++;
		}
		return operations;
	}
	
	
	
	private DoctorOperationDTO convertOperationToDoctorDTO(OperationPOJO operation) {
		
		DoctorOperationDTO dto = new DoctorOperationDTO();
		
		DoctorPOJO doctor1 = operation.getFirstDoctor();
		DoctorPOJO doctor2 = operation.getSecondDoctor();
		DoctorPOJO doctor3 = operation.getThirdDoctor();
		

		dto.setFirstDoctor(doctor1.getFirstName() + ' ' + doctor1.getLastName());
		dto.setSecondDoctor(doctor2.getFirstName() + ' ' + doctor2.getLastName());
		dto.setThirdDoctor(doctor3.getFirstName() + ' ' + doctor3.getLastName());
		
		
		PatientPOJO patient = operation.getPatient();
		if(patient == null) {
			dto.setPatientFirstName("-");
			dto.setPatientLastName("-");
			
		}
		else {
			System.out.println("PACIJENT JE:" + patient.getFirstName());
			dto.setPatientFirstName(patient.getFirstName());
			dto.setPatientLastName(patient.getLastName());
			dto.setInsuranceNumber(String.valueOf(patient.getInsuranceNumber()));
		}
		
		ProceduresTypePOJO pt = doctor1.getProcedureType();
		dto.setProcedureName(pt.getName());
		
		dto.setStartDate(operation.getDate().getTime());
		System.out.println("START IS" + operation.getDate().getTime());
		
		Calendar end = Calendar.getInstance(); // creates calendar
		end.setTime(operation.getDate().getTime()); // sets calendar time/date
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(pt.getDuration());
		end.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR)); //dodaje sate
		end.add(Calendar.MINUTE, duration.get(Calendar.MINUTE)); //dodaje minute
		System.out.println("AND END IS: " + end.getTime());
		dto.setEndDate(end.getTime());
		
		
		dto.setProcedureName(pt.getName());
		
		dto.setPrice(String.valueOf(doctor1.getProcedureType().getPrice()));
		System.out.println("PRICE IS: " + dto.getPrice());
		dto.setStatus(String.valueOf(operation.getStatus()));
		
		dto.setRoomName(operation.getRoom().getName());
		dto.setRoomNumber(String.valueOf(operation.getRoom().getNumber()));
		
		
		return dto;	
	}
	
	public String findFirstFreeSchedueForOperation(ThreeDoctorsIdDTO doctors) {
		try {
			
			Calendar begin0 = Calendar.getInstance();
			begin0.add(Calendar.DAY_OF_MONTH, 1);
			
			Calendar free = findFirstOperationSchedule(doctors.getDoctor0(), doctors.getDoctor1(), doctors.getDoctor2(), begin0);
			//sva tri datuma su ista
			return dateConvertor.dateForFrontEndString(free);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String checkOperationSchedue(OperationRequestDTO request) {
		try {
			Calendar begin0 = dateConvertor.stringToDate(request.getDateAndTimeString());
			Calendar free = findFirstOperationSchedule(request.getDoctor0(), request.getDoctor1(), request.getDoctor2(), begin0);
			
			if(free.equals(begin0)) {
				return request.getDateAndTimeString();
			} else {
				return dateConvertor.dateForFrontEndString(free);
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public OperationBlessingInnerDTO blessOperation(OperationBlessingDTO request) {
		
		try {
			Calendar begin0 = dateConvertor.stringToDate(request.getDateAndTimeString());
			
			Calendar free = findFirstOperationSchedule(request.getDoctor0(), request.getDoctor1(), request.getDoctor2(), begin0);
			//sva tri datuma su ista
			
			if(free.equals(begin0)) {
				//doktorima odgovara vreme
				String roomFreeDate = roomService.findFirstFreeScheduleFromDate(request.getRoomId(), free);
				String freeString = dateConvertor.dateForFrontEndString(free);
				if(!freeString.equals(roomFreeDate)) {
					//soba predlaze drugi termin
					return new OperationBlessingInnerDTO(roomFreeDate, OperationBlessing.ROOM_REFUSED);
				}
				
				//zakazivanje operacije
				OperationPOJO operation = operationRepository.findOneById(request.getOperationId());
				
				if(operation.getRoom() != null) {
					return new OperationBlessingInnerDTO("", OperationBlessing.ERROR);
				}
				
				operation.setDate(free);
				operation.setFirstDoctor(doctorRepository.getOne(request.getDoctor0()));
				operation.setSecondDoctor(doctorRepository.getOne(request.getDoctor1()));
				operation.setThirdDoctor(doctorRepository.getOne(request.getDoctor2()));
				operation.setStatus(OperationStatus.APPROVED);
				
				RoomPOJO room = roomRepository.findOneById(request.getRoomId());
				operation.setRoom(room);
				
				operationRepository.save(operation);

				mailSender.sendOperationApprovedToDoctorsEmail(operation.getFirstDoctor(), operation);
				mailSender.sendOperationApprovedToDoctorsEmail(operation.getSecondDoctor(), operation);
				mailSender.sendOperationApprovedToDoctorsEmail(operation.getThirdDoctor(), operation);
				mailSender.sendOperationApprovedToPatientEmail(operation);
				
				return new OperationBlessingInnerDTO("BLESSED", OperationBlessing.BLESSED);
			} else {
				return new OperationBlessingInnerDTO(
						dateConvertor.dateForFrontEndString(free),
						OperationBlessing.DOCTORS_REFUSED
						);
			}
		} catch (Exception e) {
			System.out.println("YOU MADA FAKA");
			e.printStackTrace();
			return new OperationBlessingInnerDTO("", OperationBlessing.ERROR);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
	public Calendar findFirstOperationSchedule(Long drId0, Long drId1, Long drId2, Calendar begin) {
		try {
			DoctorPOJO dr0 = doctorRepository.findById(drId0).orElse(null);
			DoctorPOJO dr1 = doctorRepository.findById(drId1).orElse(null);
			DoctorPOJO dr2 = doctorRepository.findById(drId2).orElse(null);
			if(dr0 == null || dr1 == null || dr2 == null) {
				System.out.println("ALL IS NULL");
				return null;
			}
			
			List<Date> dates0 = doctorRepository.findAllReservedOperations(drId0);
			List<Date> dates1 = doctorRepository.findAllReservedOperations(drId1);
			List<Date> dates2 = doctorRepository.findAllReservedOperations(drId2);
			
			List<AbsenceInnerDTO> absence0 = leaveRequestsService.getAllDoctorAbsence(drId0);
			List<AbsenceInnerDTO> absence1 = leaveRequestsService.getAllDoctorAbsence(drId1);
			List<AbsenceInnerDTO> absence2 = leaveRequestsService.getAllDoctorAbsence(drId2);
			
			Calendar firstEqualShift = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, absence0, absence1, absence2, begin);
			System.out.println("----------- first equal shift: " + firstEqualShift.getTime());
			return firstEqualShift;
		} catch(Exception e) {
			System.out.println("EX1 ");
			e.printStackTrace();
			return null;
		}

	}
}
