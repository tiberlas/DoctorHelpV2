package com.ftn.dr_help.controller;

import java.text.ParseException;
import java.util.Calendar;
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
import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.AppointmentReqiestDoctorsDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.DateAndTimeDTO;
import com.ftn.dr_help.dto.DoctorListingDTO;
import com.ftn.dr_help.dto.DoctorProfileDTO;
import com.ftn.dr_help.dto.DoctorProfilePreviewDTO;
import com.ftn.dr_help.dto.MedicalStaffNameDTO;
import com.ftn.dr_help.dto.MedicalStaffProfileDTO;
import com.ftn.dr_help.dto.MedicalStaffSaveingDTO;
import com.ftn.dr_help.dto.PatientHealthRecordDTO;
import com.ftn.dr_help.dto.RequestedOperationScheduleDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.dto.business_hours.BusinessDayHoursDTO;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.service.DoctorService;

@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService service;
	
	@Autowired
	private CurrentUser currentUser;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Autowired
	private Mail mailSender;
	
	@Autowired
	private DateConverter dateConvertor;
	
	@GetMapping(value = "/clinic={clinic_id}/all")
	public ResponseEntity<List<DoctorProfileDTO>> getAllRooms(@PathVariable("clinic_id") Long clinic_id) {
		List<DoctorProfileDTO> finded = service.findAll(clinic_id);
		
		if(finded == null || finded.isEmpty())
			return new ResponseEntity<List<DoctorProfileDTO>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<DoctorProfileDTO>>(finded,  HttpStatus.OK);
	}
	
	@GetMapping(value = "/clinic={clinic_id}/one/doctor={doctor_id}")
	public ResponseEntity<DoctorProfileDTO> getOneRooms(@PathVariable("clinic_id") Long clinic_id, @PathVariable("doctor_id") Long doctor_id) {
		DoctorProfileDTO finded = service.findOne(clinic_id, doctor_id);
		
		if(finded == null)
			return new ResponseEntity<DoctorProfileDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<DoctorProfileDTO>(finded,  HttpStatus.OK);
	}
	
	@GetMapping(value = "/profile")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<MedicalStaffProfileDTO> findProfile() {
		String email = currentUser.getEmail();
		
		MedicalStaffProfileDTO ret = service.findByEmail(email);
		
		if(ret == null) {
			return new ResponseEntity<MedicalStaffProfileDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalStaffProfileDTO>(ret, HttpStatus.OK);
	}
	
	@PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<MedicalStaffProfileDTO> putAdminProfile(@RequestBody UserDetailDTO doctor) {
		String email = currentUser.getEmail();
		
		MedicalStaffProfileDTO ret = service.save(doctor, email);
		
		if(ret == null) {
			return new ResponseEntity<MedicalStaffProfileDTO>(HttpStatus.NOT_ACCEPTABLE); //406
		}
		
		return new ResponseEntity<MedicalStaffProfileDTO>(ret, HttpStatus.OK);
	}
	
	@PutMapping(value = "/change/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> putAdminPassword(@RequestBody ChangePasswordDTO passwords) {
		String email = currentUser.getEmail();
		
		boolean ret = service.changePassword(passwords, email);
		
		if(ret) {
			return new ResponseEntity<String>("changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not changed", HttpStatus.NOT_ACCEPTABLE);
		}
		
	} 
	
	@GetMapping (value = "/listing/clinic={clinic_id}&appointment={appointment_type}&date={appointment_date}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<AppointmentReqiestDoctorsDTO> getDoctorListing (@PathVariable("clinic_id") Long clinicId, 
				@PathVariable("appointment_type") String appointmentType, @PathVariable("appointment_date") String appointmentDate) throws ParseException {

		
		AppointmentReqiestDoctorsDTO retVal = new AppointmentReqiestDoctorsDTO();
		
		ClinicPOJO clinic = clinicRepository.getOne(clinicId);
		retVal.setClinicName(clinic.getName());
		retVal.setAddress(clinic.getAddress());

		if (appointmentType.equals("unfiltered") || appointmentDate.equals("unfiltered")) {
			List<DoctorListingDTO> doctors = service.filterByClinic(clinicId);
			retVal.setDoctorListing(doctors);
			return new ResponseEntity<> (retVal, HttpStatus.OK);
		} else {
			List<DoctorListingDTO> doctors = service.filterByClinicDateProcedureType(clinicId, appointmentType.replace('_', ' '), appointmentDate);
			retVal.setDoctorListing(doctors);
			return new ResponseEntity<> (retVal, HttpStatus.OK);
		}
	}
	
	@GetMapping (value = "/preview/{id}/{patient}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<DoctorProfilePreviewDTO> getProfilePreview (@PathVariable("id") Long doctorId, @PathVariable("patient") Long patientId) {
		DoctorProfilePreviewDTO retVal = service.getProfilePreview(doctorId, patientId);
		if (retVal == null) {
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		} 
		
		return new ResponseEntity<> (retVal, HttpStatus.OK);
	}
	
	@GetMapping(value = "/appointment={id}/health_record") 
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<PatientHealthRecordDTO> findPatientHealthRecord(@PathVariable("id") Long id) {
		
		PatientHealthRecordDTO record = service.findPatientHealthRecord(id);
		System.out.println("record basic info" + record.getLastName());
		
		return new ResponseEntity<PatientHealthRecordDTO> (record, HttpStatus.OK);
	}

	@PostMapping(value = "/new+doctor", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> createDoctor(@RequestBody MedicalStaffSaveingDTO newDoctor) {
		String email = currentUser.getEmail();
		
		boolean ret = service.save(newDoctor, email);
		
		if(ret) {
			mailSender.sendAccountInfoEmail(newDoctor.getEmail(), "DoctorHelp", newDoctor.getFirstName(), newDoctor.getLastName(), RoleEnum.DOCTOR);
			return new ResponseEntity<String>("created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("not", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@DeleteMapping(value = "/delete/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> deleteDoctor(@PathVariable("id") Long id) {
		
		boolean ret = service.delete(id);
		
		if(ret) {
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not", HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@PostMapping (value="/review/{patient}/{doctor}/{rating}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> addReview (@PathVariable("patient") Long patientId, 
				@PathVariable("doctor") Long doctorId, @PathVariable("rating") Integer rating) {
		
		service.addReview(doctorId, patientId, rating);
		
		return new ResponseEntity<String> ("All is well", HttpStatus.OK);
	}
	@GetMapping(value = "/schedules/first_free", produces = "application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> getFirstFreeSchedule() {
		String email = currentUser.getEmail();
		
		String date = service.findFirstFreeSchedue(email);
		
		if(date == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(date, HttpStatus.OK);
	}
	
	@PostMapping(value = "/schedules/check", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> checkSchedule(@RequestBody DateAndTimeDTO dateAndTime) {
		try {
			String email = currentUser.getEmail();
			Calendar requestedSchedule = dateConvertor.stringToDate(dateAndTime.getDateAndTimeString());
			
			Calendar schedule = service.checkSchedue(email, requestedSchedule);
			
			if(requestedSchedule.compareTo(schedule) == 0) {
				return new ResponseEntity<>("OK", HttpStatus.OK);
			} else {
				String date = dateConvertor.dateForFrontEndString(schedule);
				return new ResponseEntity<>(date, HttpStatus.CREATED);//201
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/all/specialization={id}", produces="application/json")
	@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<MedicalStaffNameDTO>> getSpecializedDoctors(@PathVariable("id") Long id) {
		List<MedicalStaffNameDTO> doctors = service.getSpecializedDoctors(id);
		
		if(doctors == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(doctors, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/all/without-nurse/specialization=", produces="application/json")
	@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<MedicalStaffNameDTO>> getSpecializedDoctorsWithoutNurse(@PathVariable("id") Long id) {
		List<MedicalStaffNameDTO> doctors = service.getSpecializedDoctorsWithoutNurse(id);
		
		if(doctors == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(doctors, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/schedules/operation/requested", produces = "application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<RequestedOperationScheduleDTO>> getAllOperationRequest() {
		//vraca listu operacija koje je lekar zakazao
		String email = currentUser.getEmail();
		
		List<RequestedOperationScheduleDTO> retVal = service.getOperationRequests(email);
		
		if(retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/schedules/operation/requested/count")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> getOperationRequestCount() {
		//vraca da li je lekar zakazao operacije; ako jeste na front-u vidi spisak operacija koje je on zakazao
		String email = currentUser.getEmail();
		
		boolean retVal = service.getOperationRequestsCount(email);
		if(!retVal) {
			return new ResponseEntity<>("NO OPERATIONS", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("OPERATIONS", HttpStatus.OK);
		}
	}
		
	@GetMapping(value="/doctor={id}/business-hours")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<List<BusinessDayHoursDTO>> getDoctorBusinessHours(@PathVariable("id") Long doctor_id) {
		
		List<BusinessDayHoursDTO> list = service.getDoctorBusinessHours(doctor_id);
		
		if(list == null) {
			System.out.println("Error while calculating doctor business hours");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else{
			return new ResponseEntity<List<BusinessDayHoursDTO>>(list, HttpStatus.OK);
		}
	}
	
}
