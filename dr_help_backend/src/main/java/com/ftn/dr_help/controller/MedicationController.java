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

import com.ftn.dr_help.dto.MedicationDTO;
import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.service.MedicationService;

@RestController
@RequestMapping (value = "api/medication")
public class MedicationController {

	@Autowired
	private MedicationService medicationService;
	
	
	@PostMapping(value = "/new", consumes = "application/json")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<MedicationDTO> newMedication(@RequestBody MedicationDTO medication) {
		System.out.println("nesto us put" + medication.getName());
		MedicationPOJO m = medicationService.findByName(medication.getName());
		if (m != null) {
			System.out.println(m.getMedicationName());
		}
		System.out.println(medication.getName());
		if(m != null) {
			System.out.println("KURVA");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		MedicationPOJO med = new MedicationPOJO();
		med.setMedicationName(medication.getName());
		med.setMedDescription(medication.getDescription());
	
		med = medicationService.save(med);
		
		if(med == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(new MedicationDTO(med) , HttpStatus.CREATED);
	}
	
	
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR') or hasAuthority('DOCTOR')")
	public ResponseEntity<List<MedicationDTO>> getAllMedication() {

		List<MedicationDTO> meds = medicationService.findAll();
		
		return new ResponseEntity<List<MedicationDTO>>(meds, HttpStatus.OK);
	}
	
	@DeleteMapping(value="delete={id}")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public void deleteMedication(@PathVariable("id") Long id) {
		medicationService.delete(id);
	}

}
