package com.ftn.dr_help.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.MedicalStaffProfileDTO;
import com.ftn.dr_help.dto.MedicalStaffSaveingDTO;
import com.ftn.dr_help.dto.PatientDTO;
import com.ftn.dr_help.dto.PatientFilterDTO;
import com.ftn.dr_help.dto.SignOffDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.dto.business_hours.BusinessDayHoursDTO;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.PerscriptionPOJO;
import com.ftn.dr_help.service.NurseService;
import com.ftn.dr_help.service.PatientService;
import com.ftn.dr_help.service.PerscriptionService;

@RestController
@RequestMapping(value = "api/nurses")
public class NurseController {
	
	@Autowired
	private NurseService service;
	
	@Autowired
	private CurrentUser currentUser;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PerscriptionService perscriptionService;
	
	@Autowired
	private Mail mailSender;
	
	
	@GetMapping(value = "/profile")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<MedicalStaffProfileDTO> findProfile() {
		String email = currentUser.getEmail();
		
		MedicalStaffProfileDTO ret = service.findByEmail(email);
		
		if(ret == null) {
			return new ResponseEntity<MedicalStaffProfileDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalStaffProfileDTO>(ret, HttpStatus.OK);
	}
	
	@PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<MedicalStaffProfileDTO> putAdminProfile(@RequestBody UserDetailDTO nurse) {
		String email = currentUser.getEmail();
		
		MedicalStaffProfileDTO ret = service.save(nurse, email);
		
		if(ret == null) {
			return new ResponseEntity<MedicalStaffProfileDTO>(HttpStatus.NOT_ACCEPTABLE); //406
		}
		
		return new ResponseEntity<MedicalStaffProfileDTO>(ret, HttpStatus.OK);
	}
	
	@PutMapping(value = "/change/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<String> putAdminPassword(@RequestBody ChangePasswordDTO passwords) {
		String email = currentUser.getEmail();
		
		boolean ret = service.changePassword(passwords, email);
		
		if(ret) {
			return new ResponseEntity<String>("changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not changed", HttpStatus.NOT_ACCEPTABLE);
		}
		
	} 
	
	
	@GetMapping(value = "/patientList")
	public ResponseEntity<List<PatientDTO>> getAllPatients() {

		List<PatientPOJO> patients = patientService.findAll();

		List<PatientDTO> patientDTO = new ArrayList<>();
		for (PatientPOJO p : patients) {
			patientDTO.add(new PatientDTO(p));
		}
		
		return new ResponseEntity<>(patientDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/filterPatients", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<PatientDTO>> getFilteredPatients(@RequestBody PatientFilterDTO filterResults) {
		
		if(filterResults.getFilterResults().trim().equals("")) { //if search result is empty, return all
			List<PatientPOJO> patients = patientService.findAll();
			
			List<PatientDTO> patientDTO = new ArrayList<>();
			for (PatientPOJO p : patients) {
				patientDTO.add(new PatientDTO(p));
			}
			return new ResponseEntity<>(patientDTO, HttpStatus.OK);
		} 
		
		List<PatientPOJO> patients = patientService.singleFilterPatients(filterResults.getFilterResults());

		List<PatientDTO> patientDTO = new ArrayList<>();
		for (PatientPOJO p : patients) {
			patientDTO.add(new PatientDTO(p));
		}
		
		return new ResponseEntity<>(patientDTO, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/pendingPerscriptions/nurse={id}")
	public ResponseEntity<List<SignOffDTO>> listPendingPerscriptions(@PathVariable("id") Long nurse_id) {
		List<SignOffDTO> dtoList = perscriptionService.findAllPendingPerscriptions(nurse_id);
		
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/perscription/appointment={id}")
	public ResponseEntity<SignOffDTO> getPerscriptionForAppointment(@PathVariable("id") Long id) {
		
		SignOffDTO dto = perscriptionService.findPerscriptionFromAppointment(id);
		return new ResponseEntity<SignOffDTO>(dto, HttpStatus.OK);
	}
	
	@PutMapping(value="/signOff/appointment={id}")
	public ResponseEntity<SignOffDTO> signOffPerscriptions(@PathVariable("id") Long id) {
		perscriptionService.signAppointmentPerscription(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/signOff/{nurseId}/{perscriptionId}")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<PerscriptionPOJO> 
	putAdminProfile (@PathVariable("nurseId") Long nurseId, @PathVariable("perscriptionId") Long perscriptionId) {
		System.out.println("NURSE ID: "+nurseId + " PERSCR" + perscriptionId);
		
		PerscriptionPOJO updated = perscriptionService.signPerscription(nurseId, perscriptionId);
		
		System.out.println("bad boy" + updated.getId() + " " + updated.getSigningNurse().getFirstName());
		
		return new ResponseEntity<PerscriptionPOJO>(updated, HttpStatus.OK);
	}
	

	@PostMapping(value = "/new+nurse", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> createNurse(@RequestBody MedicalStaffSaveingDTO newNurse) {
		String email = currentUser.getEmail();
		
		boolean ret = service.save(newNurse, email);
		
		if(ret) {
			mailSender.sendAccountInfoEmail(newNurse.getEmail(), "DoctorHelp", newNurse.getFirstName(), newNurse.getLastName(), RoleEnum.NURSE);
			return new ResponseEntity<String>("created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("not", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@DeleteMapping(value = "/delete/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> deleteNurse(@PathVariable("id") Long id) {
		
		boolean ret = service.delete(id);
		
		if(ret) {
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@GetMapping(value = "/all", produces = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<MedicalStaffProfileDTO>> getAll() {
		String email = currentUser.getEmail();
		
		List<MedicalStaffProfileDTO> retVal = service.getAll(email);
		
		if(retVal != null) {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/nurse={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<MedicalStaffProfileDTO> findOne(@PathVariable("id") Long id) {
		
		MedicalStaffProfileDTO ret = service.findById(id);
		
		if(ret == null) {
			return new ResponseEntity<MedicalStaffProfileDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalStaffProfileDTO>(ret, HttpStatus.OK);
	}
	
	@GetMapping(value="/nurse={id}/business-hours")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<List<BusinessDayHoursDTO>> getNurseBusinessHours(@PathVariable("id") Long doctor_id) {
		
		List<BusinessDayHoursDTO> list = service.getNurseBusinessHours(doctor_id);
		
		if(list == null) {
			System.out.println("Error while calculating doctor business hours");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else{
			return new ResponseEntity<List<BusinessDayHoursDTO>>(list, HttpStatus.OK);
		}
	}
	

}
