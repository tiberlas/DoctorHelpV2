package com.ftn.dr_help.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ftn.dr_help.TestUtil;
import com.ftn.dr_help.constants.AppointmentConstants;
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.dto.AppointmentDeleteDTO;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.dto.PatientHistoryDTO;
import com.ftn.dr_help.service.AppointmentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentControllerPredefinedTest {

	private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());


	private MockMvc mockMvc;

	@MockBean
	private AppointmentService appointmentService;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	 @Autowired
	    private WebApplicationContext webApplicationContext;
	
	  @PostConstruct
	    public void setUp() {
	        this.mockMvc = MockMvcBuilders
	                .webAppContextSetup(webApplicationContext)
	                .apply(springSecurity())
	                .build();
	    }
	
	// JWT token
	private String accessToken;
	
	
	@Before
	public void login() {
		ResponseEntity<LoginResponseDTO> responseEntity = 
				restTemplate.postForEntity("/api/login", 
						new LoginRequestDTO(LoginConstants.PATIENT_USERNAME, LoginConstants.PATIENT_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getJwtToken();
	
	}
	
	
	@Test
	public void reserveAppointmentShouldReturnBooleanFromService() throws Exception {
		
		AppointmentDeleteDTO dto = new AppointmentDeleteDTO();
		dto.setPatientId(1L);
		dto.setAppointmentId(1L);
    	
    	String json = TestUtil.json(dto);
		
		when(this.appointmentService.reserveAppointment(dto.getPatientId(), dto.getAppointmentId())).thenReturn(true);
		mockMvc.perform(
				post("/api/appointments/predefined/reserve")
				.contentType(contentType)
				.header("Authorization", accessToken)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}
	
	
	
	@Test
	public void getSomeFilteredPredefinedAppointments() throws Exception {
		
		AppointmentListDTO appList = setUpAppointmentList();
		
		Mockito.when(this.appointmentService.getPredefinedAppointments(Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class))).thenReturn(appList);
		
		
		mockMvc.perform(get("/api/appointments/predefined/doctor=" + AppointmentConstants.DOCTOR_ID
				+"/procedure_type="+AppointmentConstants.PROCEDURE_TYPE_ID+"/clinic="
				+ AppointmentConstants.CLINIC_ID + "/date="+AppointmentConstants.FILTER_DATE)
				.contentType(contentType)
				.header("Authorization", accessToken))
				.andExpect(jsonPath("$.possibleClinics").isArray())
				.andExpect(jsonPath("$.possibleDoctors").isArray())
				.andExpect(jsonPath("$.possibleStatuses").isArray())
				.andExpect(jsonPath("$.possibleTypes").isArray())
				.andExpect(jsonPath("$.possibleClinics", hasItem(AppointmentConstants.CLINIC_NAME)))
				.andExpect(jsonPath("$.possibleDoctors", hasItem(AppointmentConstants.DOCTOR_NAME_PERA)))
				.andExpect(jsonPath("$.possibleTypes", hasItem(AppointmentConstants.PROCEDURE_NAME)))
				.andExpect(jsonPath("$.possibleStatuses", hasItem(AppointmentConstants.STATUS_NAME)))
				.andExpect(status().isOk());
		
	}
	
	
	
	private AppointmentListDTO setUpAppointmentList() {
		AppointmentListDTO appList = new AppointmentListDTO();
		
		List<String> possibleClinics = new ArrayList<String>();
		List<String> possibleDates = new ArrayList<String>();
		List<String> possibleDoctors = new ArrayList<String>();
		List<String> possibleStatuses = new ArrayList<String>();
		List<String> possibleTypes = new ArrayList<String>();
		
		List<PatientHistoryDTO> historyList = new ArrayList<PatientHistoryDTO>();
		
		possibleClinics.add("Psihicki se raspadam");
		possibleClinics.add("Hospital 2");
		
		possibleDates.add("27.02.2020 11:05");
		possibleDates.add("23.02.2020, 07:23");
		
		possibleDoctors.add("Pera Peric");
		possibleDoctors.add("Mikica Kurtovic");
		
		possibleStatuses.add("nzm");
		possibleStatuses.add("nzm2");
		
		possibleTypes.add("Psiho analiza mozga");
		possibleTypes.add("yeah...");
		
		historyList.add(new PatientHistoryDTO(possibleStatuses.get(0),1L, 
											possibleDates.get(0),
											possibleTypes.get(0),
											possibleDoctors.get(0),
											"Ana",
											possibleClinics.get(0),
											1L,
											1L,
											1L,
											1L,
											false));
		
		historyList.add(new PatientHistoryDTO(possibleStatuses.get(1),2L, 
				possibleDates.get(1),
				possibleTypes.get(1),
				possibleDoctors.get(1),
				"Mila",
				possibleClinics.get(1),
				2L,
				2L,
				2L,
				2L,
				false));
		
		
		appList.setAppointmentList(historyList);
		
		appList.setPossibleClinics(possibleClinics);
		appList.setPossibleDates(possibleDates);
		appList.setPossibleDoctors(possibleDoctors);
		appList.setPossibleStatuses(possibleStatuses);
		appList.setPossibleTypes(possibleTypes);
		
		return appList;
		
	}
	
}
