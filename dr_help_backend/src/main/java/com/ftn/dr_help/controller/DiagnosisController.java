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

import com.ftn.dr_help.dto.DiagnosisDTO;
import com.ftn.dr_help.model.pojo.DiagnosisPOJO;
import com.ftn.dr_help.service.DiagnosisService;


@RestController
@RequestMapping (value = "api/diagnoses")
public class DiagnosisController {

	
	@Autowired
	private DiagnosisService diagnosisService;
	
	
	@PostMapping(value = "/new", consumes = "application/json")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<DiagnosisDTO> newMedication(@RequestBody DiagnosisDTO diagnosis) {
		
		DiagnosisPOJO test = diagnosisService.findByDiagnosis(diagnosis.getName());
		
		if(test != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		DiagnosisPOJO d = new DiagnosisPOJO();
		d.setDiagnosis(diagnosis.getName());
		d.setDescription(diagnosis.getDescription());
	
		d = diagnosisService.save(d);
		return new ResponseEntity<>(new DiagnosisDTO(d) , HttpStatus.CREATED);
	}
	
	
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR') or hasAuthority('DOCTOR')")
	public ResponseEntity<List<DiagnosisDTO>> getAllDiagnoses() {

		List<DiagnosisDTO> dDTO = diagnosisService.findAll();

		return new ResponseEntity<List<DiagnosisDTO>>(dDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value="delete={id}")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public void deleteDiagnosis(@PathVariable("id") Long id) {
		diagnosisService.delete(id);
	}
	
}