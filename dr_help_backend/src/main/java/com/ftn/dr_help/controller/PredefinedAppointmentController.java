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
import com.ftn.dr_help.dto.PredefinedAppointmentDTO;
import com.ftn.dr_help.dto.PredefinedAppointmentResponseDTO;
import com.ftn.dr_help.model.enums.CreatingPredefinedAppointmentEnum;
import com.ftn.dr_help.service.PredefinedAppointmentService;

@RestController
@RequestMapping(value = "api/predefined+appointments")
public class PredefinedAppointmentController {
	
	@Autowired
	private PredefinedAppointmentService service;
	
	@Autowired
	private CurrentUser currentUser;
	
	@GetMapping(value = "/clinic={id}/all")
	public ResponseEntity<List<PredefinedAppointmentDTO>> getAll(@PathVariable("id") Long id) {
		List<PredefinedAppointmentDTO> ret = null;
		
		ret = service.getAll(id);
		
		if(ret == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@PostMapping(value = "/newPredefinedAppointment")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<PredefinedAppointmentResponseDTO> create(@RequestBody PredefinedAppointmentDTO newPredefinedAppointment) {
		String email = currentUser.getEmail();
		
		PredefinedAppointmentResponseDTO ret = service.save(newPredefinedAppointment, email);
		
		if(ret == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if(ret.getStatus() != CreatingPredefinedAppointmentEnum.APPROVED) {
			return new ResponseEntity<>(ret, HttpStatus.CONFLICT);//409
		} else {
			return new ResponseEntity<>(ret, HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping(value = "delete/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> delete (@PathVariable("id") Long id) {
		service.delete(id);
		
		return new ResponseEntity<>("successfully" ,HttpStatus.OK);
	}
	

}
