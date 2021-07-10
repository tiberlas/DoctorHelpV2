package com.ftn.dr_help.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DoctorControllerListingIntegrationTest {
	
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
	
	
	@Before
	public void login() {
		ResponseEntity<LoginResponseDTO> responseEntity = 
				restTemplate.postForEntity("/api/login", 
						new LoginRequestDTO(LoginConstants.PATIENT_USERNAME, LoginConstants.PATIENT_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getJwtToken();
	}
	
	/* 
	 * query params: clinic=1 & appointment=opsta_praksa  & date=2020-02-12
	 * 
	 * returns: Doctors Jovan Milinkovic and Pera Peric, Criteria rating = 1
	 * */
	
	@Test
	public void getRealDoctorListForAppointment() throws Exception {
		mockMvc.perform(get("/api/doctors/listing/clinic="+ClinicConstants.FILTER_CLINIC_ID
				+"&appointment="+ClinicConstants.INTEGRATION_PROCEDURE_TYPE_NAME+"&date="+ClinicConstants.INTEGRATION_SEARCH_DATE)
				.contentType(contentType)
				.header("Authorization", accessToken))
				.andExpect(jsonPath("$.doctorListing").isArray())
				.andExpect(jsonPath("$.doctorListing", hasSize(2)))
				.andExpect(jsonPath("$.doctorListing[*].firstName", hasItem(ClinicConstants.INTEGRATION_DOCTOR_NAME)))
				.andExpect(jsonPath("$.doctorListing[*].rating", hasItem(ClinicConstants.INTEGRATION_DOCTOR_RATING)))
				.andExpect(status().isOk());
	}
}
