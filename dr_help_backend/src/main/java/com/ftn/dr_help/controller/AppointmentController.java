package com.ftn.dr_help.controller;


import java.text.ParseException;
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
import com.ftn.dr_help.dto.AddAppointmentDTO;
import com.ftn.dr_help.dto.AppointmentDeleteDTO;
import com.ftn.dr_help.dto.AppointmentForSchedulingDTO;
import com.ftn.dr_help.dto.AppointmentInternalBlessedDTO;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.DoctorAppointmentDTO;
import com.ftn.dr_help.dto.DoctorRequestAppointmentDTO;
import com.ftn.dr_help.dto.ExaminationReportDTO;
import com.ftn.dr_help.dto.RequestingAppointmentDTO;
import com.ftn.dr_help.dto.nurse.NurseAppointmentDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.service.AppointmentBlessingService;
import com.ftn.dr_help.service.AppointmentService;
import com.ftn.dr_help.service.DoctorService;
import com.ftn.dr_help.service.PatientService;


@RestController
@RequestMapping (value = "api/appointments/")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private Mail mailSender;
	
	@Autowired
	private CurrentUser currentUser;
	
	@Autowired
	private AppointmentBlessingService blesingService;
	
	@GetMapping(value = "/all_appointments/doctor={doctor_id}")
	public ResponseEntity<List<DoctorAppointmentDTO>> getAllDoctorAppointments(@PathVariable("doctor_id") Long doctor_id) {
		
		List<DoctorAppointmentDTO> appointments = appointmentService.findDoctorAppointments(doctor_id);
		
		return new ResponseEntity<List<DoctorAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@GetMapping(value="/all_appointments/nurse={nurse_id}")
	public ResponseEntity<List<NurseAppointmentDTO>> getAllNurseAppointments(@PathVariable("nurse_id") Long nurse_id) {
		
		List<NurseAppointmentDTO> appointments = appointmentService.findNurseAppointments(nurse_id);
		
		return new ResponseEntity<List<NurseAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	
	@PutMapping(value="/done={id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<ExaminationReportDTO> finishAppointment(@PathVariable("id") Long appointmentId, @RequestBody ExaminationReportDTO report) {
		
		AppointmentPOJO app = appointmentService.finishAppointment(appointmentId, report);
		System.out.println("Appointment with ID: " + app.getId() + "switched to status DONE.");
		return new ResponseEntity<ExaminationReportDTO>(report, HttpStatus.OK);
	}
	
	@GetMapping(value="/approved_appointments/doctor={doctor_id}/patient={insuranceNumber}")
	public ResponseEntity<List<DoctorAppointmentDTO>> 
		getAllApprovedDoctorAppointmentsForPatientWithId(@PathVariable("doctor_id") Long doctor_id, 
			@PathVariable("insuranceNumber") Long insuranceNumber) {
		PatientPOJO patient = patientService.findByInsuranceNumber(insuranceNumber);
		List<DoctorAppointmentDTO> appointments = appointmentService.findApprovedDoctorAppointmentsWithPatientId(patient.getId(), doctor_id);
		
		return new ResponseEntity<List<DoctorAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@PostMapping (value = "add", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<Boolean> add (@RequestBody AddAppointmentDTO dto) throws NumberFormatException, ParseException {

		String dateString = dto.getDate() + " " + dto.getTime() + ":00";

		Boolean retVal = appointmentService.addAppointment(Long.parseLong(dto.getDoctorId()), dateString, Long.parseLong(dto.getPatientId()));
		return new ResponseEntity<Boolean> (retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/done_appointments/doctor/patient={insuranceNumber}")
	public ResponseEntity<List<DoctorAppointmentDTO>> 
		getAllDoctorDoneAppointmentsForPatientWithId(@PathVariable("insuranceNumber") Long insuranceNumber) {
		PatientPOJO patient = patientService.findByInsuranceNumber(insuranceNumber);
		List<DoctorAppointmentDTO> appointments = appointmentService.findDoctorDoneAppointmentsForPatientWithId(patient.getId());
		
		return new ResponseEntity<List<DoctorAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@GetMapping(value="/done_appointments/nurse/patient={insuranceNumber}")
	public ResponseEntity<List<NurseAppointmentDTO>> 
		getAllNurseDoneAppointmentsForPatientWithId(@PathVariable("insuranceNumber") Long insuranceNumber) {
		PatientPOJO patient = patientService.findByInsuranceNumber(insuranceNumber);
		List<NurseAppointmentDTO> appointments = appointmentService.findNurseDoneAppointmentsForPatientWithId(patient.getId());
		
		return new ResponseEntity<List<NurseAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@PostMapping(value = "/request/doctor", consumes = "application/json")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> createDoctorRequestedAppointment(@RequestBody DoctorRequestAppointmentDTO requestedAppointment) {
		boolean success = appointmentService.doctorRequestAppointment(requestedAppointment);
		
		if(success) {
			String drMail = currentUser.getEmail();
			String dr = doctorService.getDoctorsFullName(drMail);
			List<String> adminMails = doctorService.getAdminsMail(drMail); 
			String type = appointmentService.getAppointmentType(requestedAppointment.getOldAppointmentID());
			
			for(String adminMail : adminMails) {
				System.out.println("MAIL TO " + adminMail);
				mailSender.sendAppointmentRequestEmail(adminMail, dr, type, requestedAppointment.getDateAndTime());				
			}

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/requested={id}/can+delete")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> getCanDelete(@PathVariable("id") Long id) {
		String email = currentUser.getEmail();
		
		boolean canDelete = appointmentService.canDelete(email, id);
		
		if(canDelete) {
			return new ResponseEntity<>("CAN BE DELETED", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping (value="/delete", consumes="application/json", produces="application/json")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> delete (@RequestBody AppointmentDeleteDTO appointment) {
		System.out.println("Attempted a delete on appointment number " + appointment.getAppointmentId());
		appointmentService.delete(appointment.getAppointmentId());
		return null;
	}
	@DeleteMapping(value = "/requested={id}/delete")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<String> deleteRequestedAppointment(@PathVariable("id") Long id) {
		String email = currentUser.getEmail();
		
		boolean deleted = appointmentService.deleteRequested(email, id);

		if(deleted) {
			return new ResponseEntity<>("DELETED", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/requests/all")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<RequestingAppointmentDTO>> getAllRequestingAppointments() {
		String email = currentUser.getEmail();
		
		List<RequestingAppointmentDTO> retVal = appointmentService.getAllRequests(email);
		
		if(retVal == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/requests/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<RequestingAppointmentDTO> getOneRequestingAppointments(@PathVariable("id") Long id) {
		
		RequestingAppointmentDTO retVal = appointmentService.getOneRequests(id);
		
		if(retVal == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		}
	}

	@GetMapping(value="/get-examination-report/appointment={id}/doctor={doctor_id}")
	public ResponseEntity<ExaminationReportDTO> getExaminationReportForDoneAppointment(@PathVariable("id") Long appointmentId,
			@PathVariable("doctor_id") Long doctor_id) {
		
		ExaminationReportDTO dto = appointmentService.findExaminationReportForDoneAppointment(appointmentId, doctor_id);
		return new ResponseEntity<ExaminationReportDTO>(dto, HttpStatus.OK);
	}
	
	@PutMapping(value="/update/appointment={id}")
	public ResponseEntity<ExaminationReportDTO> updateExaminationReport(@PathVariable("id") Long appointment_id, 
			@RequestBody ExaminationReportDTO ex ) {
		
		boolean ret = appointmentService.updateExaminationReportDTO(appointment_id, ex);
		if(ret) {
			return new ResponseEntity<ExaminationReportDTO>(ex, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value="/leave-request-appointments/nurse={id}")
	public ResponseEntity<List<NurseAppointmentDTO>> 
		getAvailableOrApprovedNurseAppointments(@PathVariable("id") Long id) {
		
		List<NurseAppointmentDTO> appointments = appointmentService.findAvailableOrApprovedNurseAppointments(id);
		
		return new ResponseEntity<List<NurseAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@GetMapping(value="/leave-request-appointments/doctor={id}")
	public ResponseEntity<List<DoctorAppointmentDTO>> 
		getAvailableOrApprovedDoctorAppointments(@PathVariable("id") Long id) {
		
		List<DoctorAppointmentDTO> appointments = appointmentService.findAvailableOrApprovedDoctorAppointments(id);
		
		return new ResponseEntity<List<DoctorAppointmentDTO>>(appointments, HttpStatus.OK);
	}
	
	@GetMapping (value="/predefined/doctor={dr_id}/procedure_type={proc_type_id}/clinic={clinic_id}/date={app_date}")
	@PreAuthorize("hasAuthority('PATIENT')")	
	public ResponseEntity<AppointmentListDTO> getPredefined (@PathVariable("dr_id") String doctorId, @PathVariable("proc_type_id") String procedureTypeId, 
				@PathVariable("clinic_id") String clinicId, @PathVariable("app_date") String date) {
		AppointmentListDTO retVal = appointmentService.getPredefinedAppointments(doctorId, procedureTypeId, clinicId, date);
		
		if (retVal == null) {
			 retVal = new AppointmentListDTO();
			
		}
		
		return new ResponseEntity<> (retVal, HttpStatus.OK);
	}
	
	@PostMapping (value = "/predefined/reserve")
	@PreAuthorize("hasAuthority('PATIENT')")	
	public ResponseEntity<Boolean> reservePredefined (@RequestBody AppointmentDeleteDTO input) {
//		System.out.println("Trying to book appointment: " + input.getAppointmentId() + "; for patient with id: " + input.getPatientId());
		Boolean retVal = appointmentService.reserveAppointment(input.getAppointmentId(), input.getPatientId());
		
		
		return new ResponseEntity<Boolean> (retVal, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/bless", produces="application/json", consumes="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> checkAppointemnt(@RequestBody AppointmentForSchedulingDTO appointment) {
		
		try {
			String adminMeil = currentUser.getEmail();
			AppointmentInternalBlessedDTO response = blesingService.blessing(appointment, adminMeil);
			
			switch(response.getBlessingLvl()) {
				case BLESSED:
					return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
				case BAD_DATE:
					return new ResponseEntity<>("DATE#"+response.getMessage(), HttpStatus.NOT_ACCEPTABLE); //406
				default:
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				case BAD_DOCTOR:
					return new ResponseEntity<>("DOCTOR#"+response.getMessage() , HttpStatus.NOT_ACCEPTABLE); //406
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping (value="/confirm", produces="application/json", consumes="application/json")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> confirmAppointment (@RequestBody AppointmentDeleteDTO dto) {
		
		appointmentService.confirmAppointment(dto.getAppointmentId());
		
		return null;
	}
	
	
	
}
