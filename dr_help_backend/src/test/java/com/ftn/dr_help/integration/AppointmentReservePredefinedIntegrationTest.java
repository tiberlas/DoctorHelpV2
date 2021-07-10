package com.ftn.dr_help.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.ftn.dr_help.TestUtil;
import com.ftn.dr_help.constants.AppointmentConstants;
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.constants.PredefinedAppointmentConstants;
import com.ftn.dr_help.dto.AppointmentDeleteDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentReservePredefinedIntegrationTest {

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
    
    
    @Test
    public void appointmentReserveShouldReturnTrue() throws Exception {
    	AppointmentDeleteDTO dto = new AppointmentDeleteDTO();
		dto.setPatientId(PredefinedAppointmentConstants.RESERVE_PATIENT_ID);
		dto.setAppointmentId(PredefinedAppointmentConstants.AVAILABLE_APPOINTMENT_ID);
    	
    	String json = TestUtil.json(dto);
    	
    	mockMvc.perform(
				post("/api/appointments/predefined/reserve")
				.contentType(contentType)
				.header("Authorization", accessToken)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
    }
    
    @Test
    public void appointmentReserveShouldReturnFalse() throws Exception {
    	AppointmentDeleteDTO dto = new AppointmentDeleteDTO();
		dto.setPatientId(PredefinedAppointmentConstants.RESERVE_PATIENT_ID);
		dto.setAppointmentId(PredefinedAppointmentConstants.UNAVAILABLE_APPOINTMENT_ID);
    	
    	String json = TestUtil.json(dto);
    	
    	mockMvc.perform(
				post("/api/appointments/predefined/reserve")
				.contentType(contentType)
				.header("Authorization", accessToken)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
    }
    
    
}
