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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.ProcedureTypeDTO;
import com.ftn.dr_help.dto.ProcedureTypeFilterDTO;
import com.ftn.dr_help.dto.ProcedureTypeInfoDTO;
import com.ftn.dr_help.service.ProcedureTypeService;

@RestController
@RequestMapping(value = "/api/procedure+types")
public class ProcedureTypeController {

    @Autowired
	private ProcedureTypeService procedureTypeService;
	
	@Autowired
    private CurrentUser currentUser;
    
    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
    public ResponseEntity<List<ProcedureTypeDTO>> getAll() {
    	String email = currentUser.getEmail();
        List<ProcedureTypeDTO> ret = procedureTypeService.getAll(email);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    //every one can access this
    @GetMapping(value = "/id={id}")
    public ResponseEntity<ProcedureTypeDTO> getOne(@PathVariable("id") Long id) {
    	ProcedureTypeDTO ret = null;

        ret = procedureTypeService.getOne(id);

        if(ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping(value = "/new+procedure+type", consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
    public ResponseEntity<ProcedureTypeDTO> save(@RequestBody ProcedureTypeDTO newProcedureType) {
        String email = currentUser.getEmail();
		
		ProcedureTypeDTO saved = procedureTypeService.save(newProcedureType, email);
		
		if(saved == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/delete/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> deleteProcedure(@PathVariable("id") Long id) {
		String email = currentUser.getEmail();
		boolean ret = procedureTypeService.delete(id, email);
		
		if(ret) {
			return new ResponseEntity<String>(HttpStatus.OK);			
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/change", consumes = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<ProcedureTypeDTO> changeRoom(@RequestBody ProcedureTypeDTO procedure) {
		String email = currentUser.getEmail();
		ProcedureTypeDTO ret = procedureTypeService.change(procedure, email);
		
		if(ret == null) {
			return new ResponseEntity<ProcedureTypeDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<ProcedureTypeDTO>(ret, HttpStatus.CREATED);
	}

	@PostMapping(value = "/filter", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<ProcedureTypeDTO>> filter(@RequestBody ProcedureTypeFilterDTO filter) {
		if(filter == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		String email = currentUser.getEmail();
		List<ProcedureTypeDTO> ret = procedureTypeService.filter(filter, email);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@GetMapping(value = "/operation/all", produces="application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<ProcedureTypeInfoDTO>> getAllOperations() {
		String email = currentUser.getEmail();
		
		List<ProcedureTypeInfoDTO> operations = procedureTypeService.allOperation(email);
		
		if(operations == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(operations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/appointments/all", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<ProcedureTypeDTO>> getAllNotOperations() {
    	String email = currentUser.getEmail();
        List<ProcedureTypeDTO> ret = procedureTypeService.getAllNotOpeations(email);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}