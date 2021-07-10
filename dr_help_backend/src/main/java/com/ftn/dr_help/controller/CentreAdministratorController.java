package com.ftn.dr_help.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.CentreAdminDTO;
import com.ftn.dr_help.dto.CentreAdminProfileDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.PatientRequestDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;
import com.ftn.dr_help.service.CentreAdministratorService;
import com.ftn.dr_help.service.PatientService;

@RestController
@RequestMapping(value = "api/centreAdmins")
@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')") //authority level on the hole controller
public class CentreAdministratorController {
	
	@Autowired
	private CentreAdministratorService centreAdministratorService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AppPasswordEncoder encoder;

	@Autowired 
	private Mail mail;
	
	@Autowired
	private CurrentUser currentUser;

	
	@PostMapping(value = "/newAdmin", consumes = "application/json")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<CentreAdminDTO> saveAdmin(@RequestBody CentreAdminDTO centreAdminDTO) {
		
		CentreAdministratorPOJO c = centreAdministratorService.findOneByEmail(centreAdminDTO.getEmail());
		
		if( c != null) 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		CentreAdministratorPOJO admin = centreAdministratorService.createNewAdmin(centreAdminDTO);
		
		return new ResponseEntity<>(new CentreAdminDTO(admin), HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<CentreAdminDTO>> getAllAdmins() {

		List<CentreAdministratorPOJO> admins = centreAdministratorService.findAll();

		List<CentreAdminDTO> centreDTO = new ArrayList<>();
		for (CentreAdministratorPOJO a : admins) {
			centreDTO.add(new CentreAdminDTO(a));
		}
		
		return new ResponseEntity<>(centreDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/requests")
	public ResponseEntity<List<PatientRequestDTO>> getPatientRequests() {
		List<UserRequestPOJO> requests = patientService.findAllRequests();
		
		List<PatientRequestDTO> requestDTO = new ArrayList<>();
		for (UserRequestPOJO request : requests) {
			requestDTO.add(new PatientRequestDTO(request));
		}
		
		return new ResponseEntity<>(requestDTO, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/profile")
	public ResponseEntity<CentreAdminProfileDTO> getCentreAdminProfile() {
		String email = currentUser.getEmail();
		
		CentreAdministratorPOJO ret = centreAdministratorService.findOneByEmail(email);
		CentreAdminProfileDTO dto = new CentreAdminProfileDTO(ret);
		
		if(ret == null) {
			return new ResponseEntity<CentreAdminProfileDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<CentreAdminProfileDTO>(dto, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CentreAdminProfileDTO> putAdminProfile(@RequestBody UserDetailDTO admin) {
		String email = currentUser.getEmail();
		
		CentreAdminProfileDTO ret = centreAdministratorService.save(admin, email);
		
		if(ret == null) {
			return new ResponseEntity<CentreAdminProfileDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<CentreAdminProfileDTO>(ret, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/change/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putAdminPassword(@RequestBody ChangePasswordDTO passwords) {
		String email = currentUser.getEmail();

		boolean ret = centreAdministratorService.changePassword(passwords, email);
		
		if(ret) {
			return new ResponseEntity<String>("changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not changed", HttpStatus.BAD_REQUEST);
		}
		
	}  
	
	
	@PostMapping(value = "/declineRequest", consumes = "application/json")
	public ResponseEntity<UserRequestPOJO> declineUserRequest(@RequestBody PatientRequestDTO patientDTO){
		
		UserRequestPOJO request = null;
				
		try {
			request = centreAdministratorService.declineRequest(patientDTO);
		} catch(Exception e) {
			System.out.println("Optimistic lock exception");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(request, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/acceptRequest", consumes = "application/json")
	public ResponseEntity<UserRequestPOJO> acceptUserRequest(@RequestBody PatientRequestDTO patientDTO) {
		
		UserRequestPOJO requested = null;
		try {
			 requested = centreAdministratorService.acceptRequest(patientDTO);
		} catch(Exception e) {
			System.out.println("Optimistic lock exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
			return new ResponseEntity<>(requested, HttpStatus.OK);
		
	}
	
	
}
