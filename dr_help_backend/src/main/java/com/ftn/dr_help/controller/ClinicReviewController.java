package com.ftn.dr_help.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.service.ClinicReviewService;

@RestController
@RequestMapping(value = "api/clinic_raithing")
public class ClinicReviewController {

	@Autowired
	private ClinicReviewService clinicReviewService;
	
	@Autowired
	private CurrentUser currentUser;
	
	@GetMapping(value = "/average")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<Float> getAverageReview() {
		String email = currentUser.getEmail();
		Float average = clinicReviewService.getAverage(email);
		
		return new ResponseEntity<>(average, HttpStatus.OK);
	}
	
}
