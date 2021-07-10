package com.ftn.dr_help.controller;

import java.text.ParseException;
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
import com.ftn.dr_help.dto.ClinicDTO;
import com.ftn.dr_help.dto.ClinicListingDTO;
import com.ftn.dr_help.dto.ClinicPreviewDTO;
import com.ftn.dr_help.dto.ClinicRatingDTO;
import com.ftn.dr_help.dto.DatePeriodDTO;
import com.ftn.dr_help.dto.HeldAppointmentsChartDataDTO;
import com.ftn.dr_help.dto.HeldAppointmentsRequestDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.ClinicReviewPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicReviewRepository;
import com.ftn.dr_help.service.ClinicService;
import com.ftn.dr_help.service.ProcedureTypeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/clinics")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private CurrentUser currentUser;
	
	@Autowired
	private ProcedureTypeService procedureTypeService;
	
	@Autowired
	private ClinicReviewRepository clinicReviewRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@PostMapping(value = "/newClinic", consumes = "application/json")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {
		
		ClinicPOJO c = clinicService.findByName(clinicDTO.getName());
		
		if( c != null) 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ClinicPOJO clinic = new ClinicPOJO();
		clinic.setName(clinicDTO.getName());
		clinic.setAddress(clinicDTO.getAddress());
		clinic.setCity(clinicDTO.getCity());
		clinic.setState(clinicDTO.getState());
		clinic.setDescription(clinicDTO.getDescription());

		clinic = clinicService.save(clinic);
		return new ResponseEntity<>(new ClinicDTO(clinic) , HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/admin-all")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<List<ClinicDTO>> getAllAdminClinics() {

		List<ClinicDTO> clinics = clinicService.findAdminAll();

		return new ResponseEntity<List<ClinicDTO>>(clinics, HttpStatus.OK);
	}

	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {

		List<ClinicPOJO> clinics = clinicService.findAll();

		List<ClinicDTO> clinicDTO = new ArrayList<>();
		for (ClinicPOJO c : clinics) {
			clinicDTO.add(new ClinicDTO(c));
		} 
		
		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/id={id}")
	public ResponseEntity<ClinicDTO> getOneCLinic(@PathVariable("id") Long id) {
		ClinicDTO finded = clinicService.findOneDTO(id);

		//System.out.println("Getting one clinic by the following id: " + id);
		
		if(finded == null) {
			return new ResponseEntity<ClinicDTO>(HttpStatus.NOT_FOUND);
		}
		//System.out.println("Findeds id: " + clinicReviewRepository.getAverageReview(finded.getId()).toString());
		Float retVal = clinicReviewRepository.getAverageReview(finded.getId());
		if (retVal != null) {
			finded.setRating(clinicReviewRepository.getAverageReview(finded.getId()).toString());
		} 
		else {
			//System.out.println("Returned NULL od trazenja proseka");
		}
		return new ResponseEntity<ClinicDTO>(finded,  HttpStatus.OK);
	} 

	@PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<ClinicDTO> changeClinicProfile(@RequestBody ClinicDTO newClinic) {
		String email = currentUser.getEmail();

		ClinicDTO ret = clinicService.save(newClinic, email);
		
		if(ret == null) {
			return new ResponseEntity<ClinicDTO>(HttpStatus.NOT_ACCEPTABLE); //406
		}
		
		return new ResponseEntity<ClinicDTO>(ret, HttpStatus.OK);
	}

	@GetMapping (value = "/listing/proc_type={filter}/date={date_string}/stat={state}/cit={city}/adr={address}/min_rat={minimal_rating}/max_rat={maximum_rating}/min_price={minimal_price}/max_price={maximal_price}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity <ClinicListingDTO> getClinicListing (@PathVariable("filter") String filter, @PathVariable("date_string") String dateString, @PathVariable("state") String state
			, @PathVariable("city") String city, @PathVariable("address") String address, @PathVariable("minimal_rating") String minimalRating, @PathVariable("maximum_rating") String maximumRating
			, @PathVariable("minimal_price") String minimalPrice, @PathVariable("maximal_price") String maximalPrice) throws ParseException {

		System.out.println("Filtriram klinike: ");
		System.out.println("   Tip procedure: " + filter);
		System.out.println("   Datum procedure: " + dateString);
		System.out.println("   Zemlja procedure: " + state);
		System.out.println("   Grad procedure: " + city);
		System.out.println("   Adresa procedure: " + address);
		System.out.println("   Min rating: " + minimalRating);
		System.out.println("   Max Rating: " + maximumRating);
		System.out.println("   Min price: " + minimalPrice);
		System.out.println("   Max price: " + maximalPrice);
		
		List<ClinicPOJO> clinicList = new ArrayList<ClinicPOJO>();
		
		if (filter.equals("unfiltered")) {
			List<ClinicPOJO> clinics = clinicService.findAll();
			for (ClinicPOJO c : clinics) {
				clinicList.add(c);
			} 
		} else {
			filter = filter.replace('_', ' ');
			List<ClinicPOJO> clinics = clinicService.filterByProcedureType (filter);
			for (ClinicPOJO c : clinics) {
				clinicList.add(c);
			} 
		}
		
		if (!dateString.equals("unfiltered")) {
			clinicList = clinicService.filterByDate(clinicList, filter, dateString);
		}
		
		List<ClinicPreviewDTO> clinicDTO = new ArrayList<>();
		for (ClinicPOJO c : clinicList) {
			ClinicPreviewDTO temp = new ClinicPreviewDTO (c);
			if (!filter.equals ("unfiltered")) {
				Double price = procedureTypeService.getPrice(c.getId(), filter);
				temp.setPrice((price == null) ? ("-") : (Double.toString(price)));
			}
			Float averageReview = clinicReviewRepository.getAverageReview(temp.getId());
			if (averageReview != null) {
				temp.setRating(averageReview.toString());
			}
			clinicDTO.add(temp);
		}
		
		List<String> procedureTypes = procedureTypeService.getProcedureTypes();
		
		ClinicListingDTO retVal = new ClinicListingDTO (clinicDTO, procedureTypes, filter, dateString);
		retVal.setStateString(state);
		retVal.setCityString(city);
		retVal.setAddressString(address);
		retVal = clinicService.doOtherFilters (retVal, state, city, address, minimalRating, maximumRating, minimalPrice, maximalPrice);
		
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@PostMapping (value="/review/{patient}/{clinic}/{rating}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<String> addReview (@PathVariable("patient") Long patientId, 
				@PathVariable("clinic") Long clinicId, @PathVariable("rating") Integer rating) {

		clinicService.addReview(patientId, clinicId, rating);
		
		return new ResponseEntity<> ("All is swell, gentlmen", HttpStatus.OK); 
	}
	
	@GetMapping (value="/review/{patient}/{clinic}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<ClinicRatingDTO> haveInteracted (@PathVariable("patient") Long patientId, 
				@PathVariable("clinic") Long clinicId) {
		
		ClinicRatingDTO retVal = new ClinicRatingDTO();

		List<AppointmentPOJO> appointments = appointmentRepository.getPatientsPastAppointmentsForClinic(patientId, clinicId);
		if (appointments.size() > 0) {
			retVal.setHaveInteracted(true);;
		}
		ClinicReviewPOJO crp = clinicReviewRepository.getClinicReview(patientId, clinicId);
		if (crp != null) {
			retVal.setMyRating(crp.getRating());
		}
		
		
		
		return new ResponseEntity<> (retVal, HttpStatus.OK); 
	}
	
	@PostMapping(value = "/income", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<Float> getIncome(@RequestBody DatePeriodDTO datePeriod) {
		String email = currentUser.getEmail();
		
		Float income = clinicService.getIncome(email, datePeriod);
		
		return new ResponseEntity<>(income, HttpStatus.OK);
	}
	
	@PostMapping(value = "/held_appointments", consumes = "application/json", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<HeldAppointmentsChartDataDTO>> getDataForChart(@RequestBody HeldAppointmentsRequestDTO request) {
		String email = currentUser.getEmail();
		
		List<HeldAppointmentsChartDataDTO> finded = clinicService.getDataForChart(email, request);
		
		if(finded == null || finded.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(finded, HttpStatus.OK);
		}
	}
	@DeleteMapping(value="delete={id}")
	@PreAuthorize("hasAuthority('CENTRE_ADMINISTRATOR')")
	public void deleteClinic(@PathVariable("id") Long id) {
		clinicService.delete(id);
	}
	

}
