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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.MedicalStaffFilterDTO;
import com.ftn.dr_help.dto.MedicalStaffInfoDTO;
import com.ftn.dr_help.service.MedicalStuffService;

@RestController
@RequestMapping(value = "api/medical+stuff")
public class MedicalStuffControler {
	
	@Autowired
	private MedicalStuffService service;
	
	@Autowired
	private CurrentUser currentUser;
	
	@GetMapping(value = "/clinic={clinic_id}/all")
	public ResponseEntity<List<MedicalStaffInfoDTO>> getAll(@PathVariable("clinic_id") Long clinicId) {
		List<MedicalStaffInfoDTO> finded = service.findAll(clinicId);
		
		if(finded == null || finded.isEmpty())
			return new ResponseEntity<List<MedicalStaffInfoDTO>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<MedicalStaffInfoDTO>>(finded, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/filter", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<MedicalStaffInfoDTO>> filter(@RequestBody MedicalStaffFilterDTO filter) {
		if(filter == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		String email = currentUser.getEmail();
		List<MedicalStaffInfoDTO> ret = service.filter(filter, email);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

}
