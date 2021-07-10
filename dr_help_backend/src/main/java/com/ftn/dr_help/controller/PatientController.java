package com.ftn.dr_help.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.HealthRecordDTO;
import com.ftn.dr_help.dto.PatientDTO;
import com.ftn.dr_help.dto.PatientFilterDTO;
import com.ftn.dr_help.dto.PatientHistoryDTO;
import com.ftn.dr_help.dto.PatientNameDTO;
import com.ftn.dr_help.dto.PatientProfileDTO;
import com.ftn.dr_help.dto.PatientRequestDTO;
import com.ftn.dr_help.dto.PerscriptionDisplayDTO;
import com.ftn.dr_help.model.pojo.HealthRecordPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.service.HealthRecordService;
import com.ftn.dr_help.service.PatientService;

@RestController
@RequestMapping (value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private CurrentUser currentUser;
	
	
	@Autowired 
	private HealthRecordService healthRecordService;
	
	@GetMapping(value = "/all/names")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<PatientNameDTO>> getAllPatientNames() {

		List<PatientNameDTO> ret = patientService.findAllNames();
		
		if(ret == null) {
			return new ResponseEntity<List<PatientNameDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PatientNameDTO>>(ret, HttpStatus.OK);
	}
	
	@GetMapping(value = "/profile/{insuranceId}")
	@PreAuthorize("hasAuthority('NURSE') or hasAuthority('DOCTOR')")
	public ResponseEntity<PatientDTO> getPatientProfile(@PathVariable("insuranceId") Long insuranceId ) {

		PatientPOJO ret = patientService.findByInsuranceNumber(insuranceId);
		
		
		if(ret == null) {
			return new ResponseEntity<PatientDTO>(HttpStatus.NOT_FOUND);
		}
		
		String medicalStaffEmail = currentUser.getEmail(); //iz tokena izvadi email i proveri u obe tabele da li postoji istorija pregleda
		boolean showHealthRecord = patientService.showHealthRecord(medicalStaffEmail, insuranceId);
		
		PatientDTO dto = new PatientDTO(ret);
		dto.setShowHealthRecord(showHealthRecord); //prosiren dto da bi prikazao health record na front endu
		
		return new ResponseEntity<PatientDTO>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('NURSE') or hasAuthority('DOCTOR')")
	public ResponseEntity<List<PatientDTO>> getAllPatients() {

		List<PatientPOJO> patients = patientService.findAll();

		List<PatientDTO> patientDTO = new ArrayList<>();
		for (PatientPOJO p : patients) {
			patientDTO.add(new PatientDTO(p));
		}
		
		return new ResponseEntity<>(patientDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/allRecords")
	public ResponseEntity<List<HealthRecordPOJO>> getAllRecords() {

		List<HealthRecordPOJO> patients = healthRecordService.findAll();

		List<HealthRecordPOJO> patientDTO = new ArrayList<>();
		for (HealthRecordPOJO p : patients) {
			patientDTO.add(p);
		}
		
		return new ResponseEntity<>(patientDTO, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/confirmAccount", consumes = "application/json")
	public ResponseEntity<PatientPOJO> confirmPatientAccount(@RequestBody PatientRequestDTO patient) {
		
		PatientPOJO p = patientService.confirmAccount(patient.getEmail());
		
		if(p == null) {
			
			return new ResponseEntity<PatientPOJO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PatientPOJO>(p, HttpStatus.OK);
	}
	
	@GetMapping (value="/profile")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<PatientProfileDTO> getPatientProfile1 () {
		String email = currentUser.getEmail();
		PatientPOJO retVal = patientService.findPatientByEmail(email);
		
		if (retVal == null) {
			return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
		}
		PatientProfileDTO ret = new PatientProfileDTO(retVal);
		
		return new ResponseEntity<PatientProfileDTO> (ret, HttpStatus.OK);
	}
	
	@PutMapping (value="/change")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<PatientProfileDTO> updateProfile (@RequestBody PatientProfileDTO profileUpdate) {
		String email = currentUser.getEmail();
		PatientProfileDTO retVal = patientService.save(profileUpdate, email);
		if (retVal == null) {
			return new ResponseEntity<> (HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<PatientProfileDTO> (retVal, HttpStatus.OK);
	}
	
	@GetMapping (value="/health_record")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<HealthRecordDTO> getHealthRecord () {
		HealthRecordDTO retVal = patientService.getHealthRecord (currentUser.getEmail());
		if (retVal == null) {
			return new ResponseEntity<HealthRecordDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HealthRecordDTO> (retVal, HttpStatus.OK);
	}
	
	@GetMapping (value="/history/doctor={dr_id}/procedure_type={proc_type_id}/clinic={clinic_id}/date={app_date}")
	@PreAuthorize("hasAuthority('PATIENT')")	
	public ResponseEntity<AppointmentListDTO> getHistory (@PathVariable("dr_id") String doctorId, @PathVariable("proc_type_id") String procedureTypeId, 
			@PathVariable("clinic_id") String clinicId, @PathVariable("app_date") String date) {
		
		AppointmentListDTO retVal = patientService.getHistory(currentUser.getEmail(), date, doctorId, clinicId, procedureTypeId);
		if (retVal == null ) {
			return new ResponseEntity<> (retVal, HttpStatus.OK);
		}

		if (retVal.getAppointmentList() == null) {
			retVal.setAppointmentList(new ArrayList<PatientHistoryDTO> ());
		}
		
		return new ResponseEntity<> (retVal, HttpStatus.OK);
	}
	
	@GetMapping (value="/pending/doctor={dr_id}/procedure_type={proc_type_id}/clinic={clinic_id}/date={app_date}/status={appointment_status}")
	@PreAuthorize("hasAuthority('PATIENT')")	
	public ResponseEntity<AppointmentListDTO> getPendingAppointments (@PathVariable("dr_id") String doctorId, @PathVariable("proc_type_id") String procedureTypeId, 
			@PathVariable("clinic_id") String clinicId, @PathVariable("app_date") String date, @PathVariable("appointment_status") String appointmentStatus) {
//		
//		System.out.println("");
//		System.out.println("");
//		System.out.println("    Appointment status is: " + appointmentStatus);
//		System.out.println("    Appointment date is:   " + date);
//		System.out.println("");
//		System.out.println("");
//		
		AppointmentListDTO retVal = patientService.getPending(currentUser.getEmail(), date, doctorId, clinicId, procedureTypeId, appointmentStatus);
		if (retVal.getAppointmentList() == null) {
			retVal.setAppointmentList(new ArrayList<PatientHistoryDTO> ());
		}
		return new ResponseEntity<> (retVal, HttpStatus.OK);
	}
	
	@GetMapping (value = "/examinationReportId={examinationReportId}/perscription")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<PerscriptionDisplayDTO> getPerscription (@PathVariable("examinationReportId") Long examinationReportId) {
		PerscriptionDisplayDTO retVal = patientService.getPerscription(examinationReportId);
		
		if (retVal == null) {
			System.out.println("Nisam pronasao perscription " + examinationReportId);
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<> (retVal, HttpStatus.OK);
	}
	
	@PutMapping(value = "/change/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> putPatientPassword(@RequestBody ChangePasswordDTO passwords) {
		String email = currentUser.getEmail();

		boolean ret = patientService.changePassword(passwords, email);
		
		if(ret) {
			return new ResponseEntity<String>("changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not changed", HttpStatus.NOT_ACCEPTABLE);
		}
		
	} 
	
	@PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('NURSE') or hasAuthority('DOCTOR')")
	public ResponseEntity<List<PatientDTO>> filter(@RequestBody PatientFilterDTO filter){
		List<PatientDTO> patients = patientService.findAllfilter(filter);

		if(patients == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}
	
}
