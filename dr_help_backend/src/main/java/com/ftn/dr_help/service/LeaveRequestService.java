package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.leave_requests.BlessingConflictsDTO;
import com.ftn.dr_help.dto.leave_requests.LeaveRequestDTO;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.enums.LeaveRequestValidationEnum;
import com.ftn.dr_help.model.enums.LeaveStatusEnum;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.LeaveRequestPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.LeaveRequestRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.OperationRepository;

@Service
public class LeaveRequestService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired 
	private Mail mail;
	
	@Autowired
	private OperationRepository operationRepository;
	
	
	public boolean addNurseRequest(Long nurse_id, LeaveRequestDTO dto) {
		
		LeaveRequestPOJO leaveRequestPOJO = new LeaveRequestPOJO();
		NursePOJO nurse = nurseRepository.findOneById(nurse_id);
		
		if(nurse.isDeleted()) {
			System.out.println("Nurse is deleted.");
			return false;
		}
		
		leaveRequestPOJO.setNurse(nurse); 
		leaveRequestPOJO.setDoctor(null);
		
		leaveRequestPOJO.setRequestNote(dto.getNote());
		leaveRequestPOJO.setLeaveType(dto.getLeaveType());
		leaveRequestPOJO.setStaffRole(RoleEnum.NURSE);

		Calendar firstDay = Calendar.getInstance(); //set calendar instance based on dto date
		firstDay.setTime(dto.getStartDate()); 
		leaveRequestPOJO.setFirstDay(firstDay);
		
		Calendar lastDay = Calendar.getInstance();
		lastDay.setTime(dto.getEndDate());
		leaveRequestPOJO.setLastDay(lastDay);
		leaveRequestPOJO.setLeaveStatus(LeaveStatusEnum.REQUESTED);

		
		leaveRequestRepository.save(leaveRequestPOJO);
		System.out.println("Added new leave request for NURSE ID " + nurse_id + " FIRST_NAME: " + nurse.getFirstName());
		
		return true;
	}
	
	
	public boolean addDoctorRequest(Long doctor_id, LeaveRequestDTO dto) {
		
		LeaveRequestPOJO leaveRequestPOJO = new LeaveRequestPOJO();
		DoctorPOJO doctor = doctorRepository.findOneById(doctor_id);
		
		if(doctor.isDeleted()) {
			System.out.println("Doctor is deleted.");
			return false;
		}
		
		leaveRequestPOJO.setNurse(null); 
		leaveRequestPOJO.setDoctor(doctor);
		
		leaveRequestPOJO.setRequestNote(dto.getNote());
		leaveRequestPOJO.setLeaveType(dto.getLeaveType());
		leaveRequestPOJO.setStaffRole(RoleEnum.DOCTOR);

		Calendar firstDay = Calendar.getInstance(); //set calendar instance based on dto date
		firstDay.setTime(dto.getStartDate()); 
		leaveRequestPOJO.setFirstDay(firstDay);
		
		Calendar lastDay = Calendar.getInstance();
		lastDay.setTime(dto.getEndDate());
		leaveRequestPOJO.setLastDay(lastDay);
		leaveRequestPOJO.setLeaveStatus(LeaveStatusEnum.REQUESTED);

		
		leaveRequestRepository.save(leaveRequestPOJO);
		System.out.println("Added new leave request for DOCTOR ID " + doctor_id + " FIRST_NAME: " + doctor.getFirstName());
		
		return true;
	}
	
	
	
	//for request history table
	public List<LeaveRequestDTO> getNurseRequests(Long nurse_id) {
		List<LeaveRequestPOJO> list = leaveRequestRepository.getNurseLeaveRequests(nurse_id);
		
		List<LeaveRequestDTO> dtoList = new ArrayList<LeaveRequestDTO>();
		for (LeaveRequestPOJO leaveRequestPOJO : list) {
			LeaveRequestDTO dto = convertRequestToDTO(leaveRequestPOJO);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	//for schedule display
	public List<LeaveRequestDTO> getApprovedNurseRequests(Long nurse_id) {
		List<LeaveRequestPOJO> list = leaveRequestRepository.getNurseApprovedLeaveRequests(nurse_id);
		
		List<LeaveRequestDTO> dtoList = new ArrayList<LeaveRequestDTO>();
		for (LeaveRequestPOJO leaveRequestPOJO : list) {
			LeaveRequestDTO dto = convertRequestToDTO(leaveRequestPOJO);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	//for request history table
	public List<LeaveRequestDTO> getDoctorRequests(Long doctor_id) {
		List<LeaveRequestPOJO> list = leaveRequestRepository.getDoctorLeaveRequests(doctor_id);
		
		List<LeaveRequestDTO> dtoList = new ArrayList<LeaveRequestDTO>();
		for (LeaveRequestPOJO leaveRequestPOJO : list) {
			LeaveRequestDTO dto = convertRequestToDTO(leaveRequestPOJO);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	//for schedule display
	public List<LeaveRequestDTO> getApprovedDoctorRequests(Long doctor_id) {
		List<LeaveRequestPOJO> list = leaveRequestRepository.getDoctorApprovedLeaveRequests(doctor_id);
		
		List<LeaveRequestDTO> dtoList = new ArrayList<LeaveRequestDTO>();
		for (LeaveRequestPOJO leaveRequestPOJO : list) {
			LeaveRequestDTO dto = convertRequestToDTO(leaveRequestPOJO);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	public List<LeaveRequestDTO> getAdminRequests() {
		
		Calendar now = Calendar.getInstance();
		List<LeaveRequestPOJO> list = leaveRequestRepository.getAdminRequests(now.getTime());
		
		List<LeaveRequestDTO> dtoList = new ArrayList<LeaveRequestDTO>();
		for (LeaveRequestPOJO leaveRequestPOJO : list) {
			LeaveRequestDTO dto = convertRequestToDTO(leaveRequestPOJO);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	private LeaveRequestDTO convertRequestToDTO(LeaveRequestPOJO request) {
		
			LeaveRequestDTO dto = new LeaveRequestDTO();
			dto.setStartDate(request.getFirstDay().getTime());
			dto.setEndDate(request.getLastDay().getTime());
			dto.setLeaveType(request.getLeaveType());
			dto.setNote(request.getRequestNote());
			dto.setLeaveStatus(request.getLeaveStatus());
			dto.setStaffRole(request.getStaffRole());
			dto.setId(request.getId());
			
			if(request.getStaffRole().equals(RoleEnum.DOCTOR)) {
				System.out.println("doca request");
				dto.setFirstName(request.getDoctor().getFirstName());
				dto.setLastName(request.getDoctor().getLastName());
				dto.setStaffId(request.getDoctor().getId());
			} else {
				System.out.println("nurse request");
				dto.setFirstName(request.getNurse().getFirstName());
				dto.setLastName(request.getNurse().getLastName());
				dto.setStaffId(request.getNurse().getId());
			}
			return dto;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS, isolation=Isolation.READ_COMMITTED)
	public BlessingConflictsDTO validateNurseRequest(LeaveRequestDTO requestDTO) {
		Calendar now = Calendar.getInstance();
		
		now.set(Calendar.HOUR_OF_DAY, 24);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(requestDTO.getEndDate()); // sets calendar time/date
		
		endDate.set(Calendar.HOUR_OF_DAY, 23); //dodaje sate
		endDate.add(Calendar.MINUTE, 59); //dodaje minute
		System.out.println("End date is " + endDate.getTime());
		
		List<AppointmentPOJO> list = new ArrayList<AppointmentPOJO>();
		if(now.getTime().after(requestDTO.getStartDate())) {
			System.out.println("Start date is before now");
			
			
			list = appointmentRepository.getNurseAppointmentsBetweenRequestDates(requestDTO.getStaffId(), now.getTime(), endDate.getTime());
		} else {
			System.out.println("Start date is after now");
			
			list = appointmentRepository.getNurseAppointmentsBetweenRequestDates(requestDTO.getStaffId(),requestDTO.getStartDate(), endDate.getTime());
		}
		
		BlessingConflictsDTO blessConflictsDTO = new BlessingConflictsDTO();
		Integer approvedCount = 0;
		
		if(list.isEmpty()) {
			blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.CAN_BLESS);
			return blessConflictsDTO;
			
		} else {
			boolean onlyAvailable = true;
			
			for (AppointmentPOJO appointmentPOJO : list) {
				System.out.println("Appointments between " + requestDTO.getStartDate() +  " and " + endDate.getTime() +  "are: " 
						+ appointmentPOJO.getId());
				if(appointmentPOJO.getStatus().equals(AppointmentStateEnum.APPROVED)) {
					onlyAvailable = false;
					approvedCount++;
				}
			}
			
			if(onlyAvailable) {
				blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.AVAILABLE_CONFLICT);
				return blessConflictsDTO;
			}
		}
		
		blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.APPROVED_CONFLICT);
		blessConflictsDTO.setApprovedAppointmentsCount(approvedCount);
		
		return blessConflictsDTO;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS, isolation=Isolation.READ_COMMITTED)
	public BlessingConflictsDTO validateDoctorRequest(LeaveRequestDTO requestDTO) {
		
		DoctorPOJO doctor = doctorRepository.findOneById(requestDTO.getStaffId());
		boolean operatingDoctor = doctor.getProcedureType().isOperation(); //is he an operating doctor or not
		
		Calendar now = Calendar.getInstance();
		
		now.set(Calendar.HOUR_OF_DAY, 24);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(requestDTO.getEndDate()); // sets calendar time/date
		
		endDate.set(Calendar.HOUR_OF_DAY, 23); //dodaje sate
		endDate.add(Calendar.MINUTE, 59); //dodaje minute
		System.out.println("End date is " + endDate.getTime());
		
		List<AppointmentPOJO> list = new ArrayList<AppointmentPOJO>();
		if(now.getTime().after(requestDTO.getStartDate())) {
			System.out.println("Start date is before now");
			
			
			list = appointmentRepository.getDoctorAppointmentsBetweenRequestDates(requestDTO.getStaffId(), now.getTime(), endDate.getTime());
		} else {
			System.out.println("Start date is after now");
			
			list = appointmentRepository.getDoctorAppointmentsBetweenRequestDates(requestDTO.getStaffId(),requestDTO.getStartDate(), endDate.getTime());
		}
		
		BlessingConflictsDTO blessConflictsDTO = new BlessingConflictsDTO();
		if(operatingDoctor) {
			blessConflictsDTO.setOperatingDoctor(true);
		} else {
			blessConflictsDTO.setOperatingDoctor(false);
		}
		
		Integer approvedCount = 0;
		List<OperationPOJO> operationList = new ArrayList<OperationPOJO>();
		
		if(list.isEmpty()) { //empty list means no appointments are found between vacation requests -> GOOD SIGN
			
				//now check the exact same thing for operations, maybe he's an operating guy.
			if(now.getTime().after(requestDTO.getStartDate())) {
				System.out.println("Start date is before now, for operation");
				operationList = operationRepository.getDoctorOperationsBetweenDates(requestDTO.getStaffId(), now.getTime(), endDate.getTime());
			} else {
				operationList = operationRepository.getDoctorOperationsBetweenDates(requestDTO.getStaffId(), requestDTO.getStartDate(), endDate.getTime());
			}
			
			if(operationList.isEmpty()) {
				blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.CAN_BLESS);
				
				return blessConflictsDTO;
			} else {
				for (OperationPOJO operationPOJO : operationList) {
					System.out.println("Appointments between " + requestDTO.getStartDate() +  " and " + endDate.getTime() +  "are: " 
							+ operationPOJO.getId());
					approvedCount++;
				}
				
				blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.APPROVED_CONFLICT);
				blessConflictsDTO.setApprovedAppointmentsCount(approvedCount);
				return blessConflictsDTO;
			}
			
			
		} else {
			boolean onlyAvailable = true;
			
			for (AppointmentPOJO appointmentPOJO : list) {
				System.out.println("Appointments between " + requestDTO.getStartDate() +  " and " + endDate.getTime() +  "are: " 
						+ appointmentPOJO.getId());
				if(appointmentPOJO.getStatus().equals(AppointmentStateEnum.APPROVED)) {
					onlyAvailable = false;
					approvedCount++;
				}
			}
			
			if(onlyAvailable) {
				blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.AVAILABLE_CONFLICT);
				return blessConflictsDTO;
			}
		}
		
		blessConflictsDTO.setValidationEnum(LeaveRequestValidationEnum.APPROVED_CONFLICT);
		blessConflictsDTO.setApprovedAppointmentsCount(approvedCount);
		
		return blessConflictsDTO;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public LeaveRequestDTO declineNurseRequest(LeaveRequestDTO requestDTO, Long request_id) throws Exception {
		LeaveRequestPOJO request = leaveRequestRepository.findOneById(request_id);
		if(!request.getLeaveStatus().equals(LeaveStatusEnum.REQUESTED)) {
			throw new Exception("Status is already handled");
		}
		request.setLeaveStatus(LeaveStatusEnum.DECLINED);
		leaveRequestRepository.save(request);
		
		NursePOJO nurse = nurseRepository.findOneById(requestDTO.getStaffId());
		
		String leaveType = request.getLeaveType().toString().substring(0, 1) + request.getLeaveType().toString().substring(1, request.getLeaveType().toString().length()).toLowerCase();

		String startDate = (request.getFirstDay().getTime()).toString();
		String endDate = (request.getLastDay().getTime()).toString();
		mail.sendDeclineLeaveRequestEmail(nurse.getEmail(), requestDTO.getNote(), nurse.getFirstName(), nurse.getLastName(), leaveType, startDate, endDate);
		
		return requestDTO;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public LeaveRequestDTO declineDoctorRequest(LeaveRequestDTO requestDTO, Long request_id) throws Exception {
		LeaveRequestPOJO request = leaveRequestRepository.findOneById(request_id);
		if(!request.getLeaveStatus().equals(LeaveStatusEnum.REQUESTED)) {
			throw new Exception("Status is already handled");
		}
		request.setLeaveStatus(LeaveStatusEnum.DECLINED);
		leaveRequestRepository.save(request);
		
		DoctorPOJO doctor = doctorRepository.findOneById(requestDTO.getStaffId());
		
		String leaveType = request.getLeaveType().toString().substring(0, 1) + request.getLeaveType().toString().substring(1, request.getLeaveType().toString().length()).toLowerCase();

		String startDate = (request.getFirstDay().getTime()).toString();
		String endDate = (request.getLastDay().getTime()).toString();
		mail.sendDeclineLeaveRequestEmail(doctor.getEmail(), requestDTO.getNote(), doctor.getFirstName(), doctor.getLastName(), leaveType, startDate, endDate);
		
		return requestDTO;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public BlessingConflictsDTO acceptNurseRequest(LeaveRequestDTO requestDTO, Long request_id) throws Exception {
		LeaveRequestPOJO request = leaveRequestRepository.findOneById(request_id);
		if(!request.getLeaveStatus().equals(LeaveStatusEnum.REQUESTED)) {
			throw new Exception("Status is already handled");
		}
		BlessingConflictsDTO conf = validateNurseRequest(requestDTO);
		
		if(conf.getValidationEnum().equals(LeaveRequestValidationEnum.CAN_BLESS)) {
		
			request.setLeaveStatus(LeaveStatusEnum.APPROVED);
			leaveRequestRepository.save(request);
			
			NursePOJO nurse = nurseRepository.findOneById(requestDTO.getStaffId());
			
			String leaveType = request.getLeaveType().toString().substring(0, 1) + request.getLeaveType().toString().substring(1, request.getLeaveType().toString().length()).toLowerCase();
	
			String startDate = (request.getFirstDay().getTime()).toString();
			String endDate = (request.getLastDay().getTime()).toString();
			mail.sendAcceptLeaveRequestEmail(nurse.getEmail(), nurse.getFirstName(), nurse.getLastName(), leaveType, startDate, endDate);
		}
		
		return conf;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public BlessingConflictsDTO acceptDoctorRequest(LeaveRequestDTO requestDTO, Long request_id) throws Exception {
		LeaveRequestPOJO request = leaveRequestRepository.findOneById(request_id);
		
		if(!request.getLeaveStatus().equals(LeaveStatusEnum.REQUESTED)) {
			throw new Exception("Status is already handled");
		}
		
		BlessingConflictsDTO conf = validateDoctorRequest(requestDTO);
		
		if(conf.getValidationEnum().equals(LeaveRequestValidationEnum.CAN_BLESS)) {
		
			request.setLeaveStatus(LeaveStatusEnum.APPROVED);
			leaveRequestRepository.save(request);
			
			DoctorPOJO doctor = doctorRepository.findOneById(requestDTO.getStaffId());
			
			String leaveType = request.getLeaveType().toString().substring(0, 1) + request.getLeaveType().toString().substring(1, request.getLeaveType().toString().length()).toLowerCase();
	
			String startDate = (request.getFirstDay().getTime()).toString();
			String endDate = (request.getLastDay().getTime()).toString();
			mail.sendAcceptLeaveRequestEmail(doctor.getEmail(), doctor.getFirstName(), doctor.getLastName(), leaveType, startDate, endDate);
		}
		
		return conf;
	}
	
	
	public List<AbsenceInnerDTO> getAllDoctorAbsence(Long doctorId) {
		List<AbsenceInnerDTO> absenceDates = new ArrayList<>();
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		List<LeaveRequestPOJO> vacationDates = leaveRequestRepository.findAllForDoctor(doctorId, now.getTime());
	
		for(LeaveRequestPOJO absence : vacationDates) {
			absenceDates.add(new AbsenceInnerDTO(
					absence.getFirstDay().getTime(),
					absence.getLastDay().getTime()));
		}
		
		return absenceDates;
	}
	
	public List<AbsenceInnerDTO> getAllNurseAbsence(Long nurseId) {
		List<AbsenceInnerDTO> absenceDates = new ArrayList<>();
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		List<LeaveRequestPOJO> vacationDates = leaveRequestRepository.findAllForNurses(nurseId, now.getTime());
	
		for(LeaveRequestPOJO absence : vacationDates) {
			absenceDates.add(new AbsenceInnerDTO(
					absence.getFirstDay().getTime(),
					absence.getLastDay().getTime()));
		}
		
		return absenceDates;
	}

}
