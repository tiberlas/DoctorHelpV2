package com.ftn.dr_help.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.OperationBlessingDTO;
import com.ftn.dr_help.dto.OperationBlessingInnerDTO;
import com.ftn.dr_help.dto.OperationDoctorRequestDTO;
import com.ftn.dr_help.dto.OperationRequestDTO;
import com.ftn.dr_help.dto.OperationRequestInfoDTO;
import com.ftn.dr_help.dto.operations.DoctorOperationDTO;
import com.ftn.dr_help.dto.ThreeDoctorsIdDTO;
import com.ftn.dr_help.service.OperationService;

@RestController
@RequestMapping (value = "api/operations")
public class OperationController {

	@Autowired
	private OperationService operationServie;
	
	@Autowired
	private CurrentUser currentUser;
	
	
	@PostMapping(value = "/request/doctor", consumes = "application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> createDoctorRequestedOperation(@RequestBody OperationDoctorRequestDTO requested) {
		String email = currentUser.getEmail();
		boolean success = operationServie.doctorRequestAppointment(requested, email);
		
		if(!success)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/requested={id}/delete")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> deleteRequestedOperation(@PathVariable("id") Long operationID) {
		
		boolean deleted = operationServie.deleteRequested(operationID);
		
		if(deleted) {
			return new ResponseEntity<>("success", HttpStatus.ACCEPTED); //202
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
		}
	}
	
	@GetMapping(value = "/requested/all", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<OperationRequestInfoDTO>> getAllRequests() {
		String email = currentUser.getEmail();
		
		List<OperationRequestInfoDTO> operations = operationServie.getAllRequests(email);
		
		if(operations == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(operations,HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/requests/id={id}", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<OperationRequestInfoDTO> getOneRequests(@PathVariable("id") Long id) {
		OperationRequestInfoDTO operations = operationServie.getOneRequests(id);
		
		if(operations == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(operations,HttpStatus.OK);
		}
	}
	

	@GetMapping(value="/all-approved/doctor={id}")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<DoctorOperationDTO>> getDoctorApprovedOperations(@PathVariable("id") Long doctor_id) {
		List<DoctorOperationDTO> list = operationServie.findDoctorOperations(doctor_id);
		
		return new ResponseEntity<List<DoctorOperationDTO>>(list, HttpStatus.OK);
	}

	@PostMapping(value = "/schedules/bless", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> blessOperation(@RequestBody OperationBlessingDTO request) {
		
		try {
			OperationBlessingInnerDTO status = operationServie.blessOperation(request);
		
			switch(status.getBlessedLvl()) {
				case BLESSED:
					return new ResponseEntity<>(HttpStatus.OK);
				case DOCTORS_REFUSED:
					return new ResponseEntity<>("DOCTOR#"+status.getRecomendedDate(), HttpStatus.CONFLICT);//409
				case ROOM_REFUSED:
					return new ResponseEntity<>("ROOM#"+status.getRecomendedDate(), HttpStatus.CONFLICT);
				default:
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
			}
		} catch(Exception e) {
			System.out.println("UHVACEN");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/schedules/check", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> checkOperationSchedule(@RequestBody OperationRequestDTO request) {
			
		String schedule = operationServie.checkOperationSchedue(request);
		if(schedule == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(schedule.equals(request.getDateAndTimeString())) {
			return new ResponseEntity<>("OK", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(schedule, HttpStatus.CREATED);//201
		}
	}
	
	//treba izbaciti
	@PostMapping(value = "/schedules/first_free", produces = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> getFirstFreeScheduleForThreeDoctors(@RequestBody ThreeDoctorsIdDTO doctors) {
		
		String date = operationServie.findFirstFreeSchedueForOperation(doctors);
		
		if(date == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(date, HttpStatus.OK);
	}
}
