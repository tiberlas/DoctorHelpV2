package com.ftn.dr_help.controller;

import java.util.Calendar;
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
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.ClinicAdminDTO;
import com.ftn.dr_help.dto.ClinicAdminNameDTO;
import com.ftn.dr_help.dto.ClinicAdminProfileDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.service.ClinicAdministratorService;
import com.ftn.dr_help.service.ClinicService;

@RestController
@RequestMapping(value = "api/clinicAdmins")
public class ClinicAdministratorController {

		@Autowired
		private ClinicAdministratorService clinicAdministratorService;
		
		@Autowired
		private ClinicService clinicService;
		
		@Autowired
		private CurrentUser currentUser;

		@Autowired
		private AppPasswordEncoder encoder;
		
		@Autowired
		private Mail mail;
	
		
		@PostMapping(value = "/newAdmin", consumes = "application/json")
		@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
		public ResponseEntity<ClinicAdminDTO> saveAdmin(@RequestBody ClinicAdminDTO clinicAdminDTO) {
			
			ClinicAdministratorPOJO a = clinicAdministratorService.findOneByEmail(clinicAdminDTO.getEmail());
			
			if(a != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			ClinicAdministratorPOJO admin = new ClinicAdministratorPOJO();
			
			ClinicPOJO c = clinicService.findOne(clinicAdminDTO.getId());
			
			if(c != null) admin.setClinic(c);
			admin.setFirstName(clinicAdminDTO.getFirstName());
			admin.setLastName(clinicAdminDTO.getLastName());
			admin.setEmail(clinicAdminDTO.getEmail());
			admin.setAddress("...");
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(clinicAdminDTO.getBirthday());
			admin.setBirthday(birthday);
			admin.setCity("...");
			admin.setPhoneNumber("...");
			admin.setState("...");
			
			String password = "verycoolpassword";
			
			String encoded = encoder.getEncoder().encode(password);
			//p.setPassword(encoded);
			admin.setPassword(encoded);
			mail.sendAccountInfoEmail(admin.getEmail(), password, admin.getFirstName(), admin.getLastName(), RoleEnum.CLINICAL_ADMINISTRATOR);
			System.out.println("Successfully sent account info email.");
			
			admin.setMustChangePassword(true);
			
			admin = clinicAdministratorService.save(admin);
			return new ResponseEntity<>(new ClinicAdminDTO(admin), HttpStatus.CREATED);
		}
		

		@GetMapping(value = "/all")
		@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
		public ResponseEntity<List<ClinicAdminDTO>> getAllClinicAdministrators() {

			List<ClinicAdminDTO> admins = clinicAdministratorService.findAll();

			return new ResponseEntity<>(admins, HttpStatus.OK);
		}

		@GetMapping(value = "/name")
		@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
		public ResponseEntity<ClinicAdminNameDTO> getClinicAdministratorsName() {
			String email = currentUser.getEmail();
			
			ClinicAdminNameDTO ret = clinicAdministratorService.findOnesName(email);
			
			if(ret == null) {
				return new ResponseEntity<ClinicAdminNameDTO>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<ClinicAdminNameDTO>(ret, HttpStatus.OK);
		}
		
		@GetMapping(value = "/profile")
		@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
		public ResponseEntity<ClinicAdminProfileDTO> getClinicAdminProfile() {
			String email = currentUser.getEmail();
			
			ClinicAdminProfileDTO ret = clinicAdministratorService.findOneProfile(email);
			
			if(ret == null) {
				return new ResponseEntity<ClinicAdminProfileDTO>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<ClinicAdminProfileDTO>(ret, HttpStatus.OK);
		}
				

		@PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
		@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
		public ResponseEntity<ClinicAdminProfileDTO> putAdminProfile(@RequestBody UserDetailDTO admin) {
			String email = currentUser.getEmail();
			
			ClinicAdminProfileDTO ret = clinicAdministratorService.save(admin, email);
			
			if(ret == null) {
				return new ResponseEntity<ClinicAdminProfileDTO>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			return new ResponseEntity<ClinicAdminProfileDTO>(ret, HttpStatus.OK);
		}
		
		@PutMapping(value = "/change/password", consumes = MediaType.APPLICATION_JSON_VALUE)
		@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
		public ResponseEntity<String> putAdminPassword(@RequestBody ChangePasswordDTO passwords) {
			String email = currentUser.getEmail();

			boolean ret = clinicAdministratorService.changePassword(passwords, email);
			
			if(ret) {
				return new ResponseEntity<String>("changed", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("not changed", HttpStatus.NOT_ACCEPTABLE);
			}
			
		}  
	
}
