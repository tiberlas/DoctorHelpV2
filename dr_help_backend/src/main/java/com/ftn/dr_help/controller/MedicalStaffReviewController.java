package com.ftn.dr_help.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.MedicalStaffReviewDTO;
import com.ftn.dr_help.service.MedicalStaffReviewService;

@RestController
@RequestMapping(value = "api/medical_staff_raithing")
public class MedicalStaffReviewController {
	
	@Autowired
	private CurrentUser currentUser;
	
	@Autowired
	private MedicalStaffReviewService medicalStaffReviewService;
	
	@GetMapping(value="/all", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<MedicalStaffReviewDTO>> getMedicalStaffWithRaithing() {
		String email = currentUser.getEmail();
		
		List<MedicalStaffReviewDTO> list = medicalStaffReviewService.getAll(email);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
