package com.ftn.dr_help.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.ftn.dr_help.constants.AppointmentConstants;
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.constants.PredefinedAppointmentConstants;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentGetPredefinedIntegrationTest {

	private String accessToken;

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
    
    @Before
	public void login() throws Exception {
		ResponseEntity<LoginResponseDTO> responseEntity = 
				restTemplate.postForEntity(UserConstants.LOGIN_URL, 
						new LoginRequestDTO(LoginConstants.PATIENT_USERNAME, LoginConstants.PATIENT_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer "+ responseEntity.getBody().getJwtToken();
	}
    
    /* 
     * gets all predefined appointments, unfiltered, that are present in the sql script.
     * 
     * returns 5 predefined appointments.
     * */
    @Test 
	public void getUnfilteredPredefinedAppointments() throws Exception {
		
		mockMvc.perform(get("/api/appointments/predefined/doctor=" + PredefinedAppointmentConstants.UNFILTERED
				+"/procedure_type="+PredefinedAppointmentConstants.UNFILTERED+"/clinic="
				+ PredefinedAppointmentConstants.UNFILTERED + "/date="+PredefinedAppointmentConstants.UNFILTERED)
				.contentType(contentType)
				.header("Authorization", accessToken))
				.andExpect(jsonPath("$.possibleClinics").isArray())
				.andExpect(jsonPath("$.possibleDoctors").isArray())
				.andExpect(jsonPath("$.possibleStatuses").isArray())
				.andExpect(jsonPath("$.possibleTypes").isArray())
				.andExpect(jsonPath("$.appointmentList").isArray())
				.andExpect(jsonPath("$.appointmentList[*]", hasSize(5)))
				.andExpect(status().isOk());
	}
    
     /* 
     * filters predefined by a criteria. 
     * 
     * filtered by date: 03-03-2020 and clinic: Klinika zdravog uma
     * 
     * returns 2 appointments.
     * */
    @Test 
    public void getFilteredPredefinedAppointments() throws Exception {
    	
    	
    	mockMvc.perform(get("/api/appointments/predefined/doctor=" + PredefinedAppointmentConstants.UNFILTERED
				+"/procedure_type="+PredefinedAppointmentConstants.UNFILTERED+"/clinic="
				+ PredefinedAppointmentConstants.FILTER_CLINIC_NAME + "/date="+PredefinedAppointmentConstants.FILTER_DATE)
				.contentType(contentType)
				.header("Authorization", accessToken))
				.andExpect(jsonPath("$.possibleClinics").isArray())
				.andExpect(jsonPath("$.possibleDoctors").isArray())
				.andExpect(jsonPath("$.possibleStatuses").isArray())
				.andExpect(jsonPath("$.possibleTypes").isArray())
				.andExpect(jsonPath("$.appointmentList").isArray())
				.andExpect(jsonPath("$.appointmentList[*]", hasSize(2)))
				.andExpect(jsonPath("$.possibleClinics", hasItem("Klinika zdravog uma")))
				.andExpect(jsonPath("$.possibleDates", hasItem("03.03.2020.")))
				.andExpect(status().isOk());
	}
}
