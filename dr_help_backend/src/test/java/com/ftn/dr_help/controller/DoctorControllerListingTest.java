package com.ftn.dr_help.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

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

import com.ftn.dr_help.constants.ClinicConstants;
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.dto.DoctorListingDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.service.DoctorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DoctorControllerListingTest {


	private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());


	private MockMvc mockMvc;

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
	
	private String accessToken;
	
	
	@MockBean
	private DoctorService doctorService;
	
	
	@MockBean 
	private ClinicRepository clinicRepository;
	
	
	@Before
	public void login() {
		ResponseEntity<LoginResponseDTO> responseEntity = 
				restTemplate.postForEntity("/api/login", 
						new LoginRequestDTO(LoginConstants.PATIENT_USERNAME, LoginConstants.PATIENT_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getJwtToken();
	
	}
	
	
	@Test
	public void getDoctorListForAppointmentRequest() throws Exception{
		
		ClinicPOJO fakeClinic = new ClinicPOJO();
		fakeClinic.setName("My fake clinic");
		fakeClinic.setAddress("My fake address");
		
		List<DoctorListingDTO> doctors = new ArrayList<>();
		List<String> terms = new ArrayList<String>();
		doctors.add(new DoctorListingDTO("Pera", "Peric", "2", 1L, terms));
		
		Mockito.when(this.clinicRepository.getOne(Mockito.any(Long.class))).thenReturn(fakeClinic);
		Mockito.when(this.doctorService.filterByClinicDateProcedureType(Mockito.any(Long.class), Mockito.any(String.class), Mockito.any(String.class)))
		.thenReturn(doctors);
		
		mockMvc.perform(get("/api/doctors/listing/clinic=1&appointment="+ClinicConstants.MOCK_PROCEDURE_TYPE_NAME+
				"&date="+ClinicConstants.MOCK_SEARCH_DATE)
				.contentType(contentType)
				.header("Authorization", accessToken))
				.andExpect(jsonPath("$.doctorListing").isArray())
				.andExpect(jsonPath("$.doctorListing", hasSize(1)))
				.andExpect(jsonPath("$.doctorListing[*].firstName").value("Pera"))
				.andExpect(status().isOk());
	}
	
}
