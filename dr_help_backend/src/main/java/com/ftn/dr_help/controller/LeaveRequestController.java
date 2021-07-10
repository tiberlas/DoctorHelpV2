package com.ftn.dr_help.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.dto.leave_requests.BlessingConflictsDTO;
import com.ftn.dr_help.dto.leave_requests.LeaveRequestDTO;
import com.ftn.dr_help.model.enums.LeaveRequestValidationEnum;
import com.ftn.dr_help.service.LeaveRequestService;

@RestController
@RequestMapping(value = "/api/leave-requests")
public class LeaveRequestController {
	
	@Autowired
	private LeaveRequestService leaveRequestService;
	
	
	@PostMapping(value="/add/nurse={id}")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<LeaveRequestDTO> addNurseRequest(@PathVariable("id") Long nurse_id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
		
		boolean ret = leaveRequestService.addNurseRequest(nurse_id, leaveRequestDTO);
		
		if(ret) {
			return new ResponseEntity<LeaveRequestDTO>(leaveRequestDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	
	@PostMapping(value="/add/doctor={id}")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<LeaveRequestDTO> addDoctorRequest(@PathVariable("id") Long doctor_id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
		
		boolean ret = leaveRequestService.addDoctorRequest(doctor_id, leaveRequestDTO);
		
		if(ret) {
			return new ResponseEntity<LeaveRequestDTO>(leaveRequestDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@GetMapping(value="/get-all/nurse={id}")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<List<LeaveRequestDTO>> getNurseRequests(@PathVariable("id") Long nurse_id) {
		
		List<LeaveRequestDTO> list = leaveRequestService.getNurseRequests(nurse_id);
		
		return new ResponseEntity<List<LeaveRequestDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/get-all/doctor={id}")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<LeaveRequestDTO>> getDoctorRequests(@PathVariable("id") Long doctor_id) {
		
		List<LeaveRequestDTO> list = leaveRequestService.getDoctorRequests(doctor_id);
		
		return new ResponseEntity<List<LeaveRequestDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/get-approved/nurse={id}")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<List<LeaveRequestDTO>> getApprovedNurseRequests(@PathVariable("id") Long nurse_id) {
		
		List<LeaveRequestDTO> list = leaveRequestService.getApprovedNurseRequests(nurse_id);
		
		return new ResponseEntity<List<LeaveRequestDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/get-approved/doctor={id}")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<LeaveRequestDTO>> getApprovedDoctorRequests(@PathVariable("id") Long doctor_id) {
		
		List<LeaveRequestDTO> list = leaveRequestService.getApprovedDoctorRequests(doctor_id);
		
		return new ResponseEntity<List<LeaveRequestDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/get-admin")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<LeaveRequestDTO>> getAdminRequests() {
		List<LeaveRequestDTO> list = leaveRequestService.getAdminRequests();
		
		return new ResponseEntity<List<LeaveRequestDTO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value="/get-admin/validate/nurse")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<BlessingConflictsDTO> validateNurseRequest(@RequestBody LeaveRequestDTO request) {
		BlessingConflictsDTO en00ms = leaveRequestService.validateNurseRequest(request);
		
		
		return new ResponseEntity<BlessingConflictsDTO>(en00ms, HttpStatus.OK);
	}
	
	@PostMapping(value="/get-admin/validate/doctor")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<BlessingConflictsDTO> validateDoctorRequest(@RequestBody LeaveRequestDTO request) {
		BlessingConflictsDTO en00ms = leaveRequestService.validateDoctorRequest(request);
		
		
		return new ResponseEntity<BlessingConflictsDTO>(en00ms, HttpStatus.OK);
	}
	
	@PutMapping(value="/decline-doctor/request={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<LeaveRequestDTO> declineDoctorRequest (@PathVariable("id") Long request_id, @RequestBody LeaveRequestDTO requestDTO) {
		LeaveRequestDTO dto = null;
		try {
		dto = leaveRequestService.declineDoctorRequest(requestDTO, request_id);
		} catch(Exception e) {
			System.out.println("Optimistic lock exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<LeaveRequestDTO>(dto, HttpStatus.OK);
	}
	
	
	
	
	@PutMapping(value="/decline-nurse/request={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<LeaveRequestDTO> declineNurseRequest (@PathVariable("id") Long request_id, @RequestBody LeaveRequestDTO requestDTO) {
		LeaveRequestDTO dto = null;
		try {
		
		dto = leaveRequestService.declineNurseRequest(requestDTO, request_id);
		} catch(Exception e) {
			System.out.println("Optimistic lock exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<LeaveRequestDTO>(dto, HttpStatus.OK);
	}
	
	
	
	@PutMapping(value="/accept-nurse/request={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<BlessingConflictsDTO> acceptNurseRequest (@PathVariable("id") Long request_id, @RequestBody LeaveRequestDTO requestDTO) {
		BlessingConflictsDTO dto = null;
		try {
		dto = leaveRequestService.acceptNurseRequest(requestDTO, request_id);
			if(!dto.getValidationEnum().equals(LeaveRequestValidationEnum.CAN_BLESS)) {
				return new ResponseEntity<BlessingConflictsDTO>(dto, HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			System.out.println("Optimistic lock exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<BlessingConflictsDTO>(dto, HttpStatus.OK);
	}
	
	
	@PutMapping(value="/accept-doctor/request={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<BlessingConflictsDTO> acceptDoctorRequest (@PathVariable("id") Long request_id, @RequestBody LeaveRequestDTO requestDTO) {
		
		BlessingConflictsDTO dto = null;
		try {
			dto = leaveRequestService.acceptDoctorRequest(requestDTO, request_id);
			if(!dto.getValidationEnum().equals(LeaveRequestValidationEnum.CAN_BLESS)) {
				return new ResponseEntity<BlessingConflictsDTO>(dto, HttpStatus.CONFLICT);
			}
		} catch(Exception e) {
			System.out.println("Optimistic lock exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<BlessingConflictsDTO>(dto, HttpStatus.OK);
	}
}
