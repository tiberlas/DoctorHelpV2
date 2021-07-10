package com.ftn.dr_help.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.ftn.dr_help.constants.AppointmentConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.dto.RequestingAppointmentDTO;
import com.ftn.dr_help.service.AppointmentService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	private String accessToken;
	
	private MockMvc mockMvc;
	
	@MockBean
	private AppointmentService appointmentService;
	
	private List<RequestingAppointmentDTO> requests = new ArrayList<>();
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());
	
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
						new LoginRequestDTO(UserConstants.CLINIC_ADMIN_MAIL, UserConstants.CLINIC_ADMIN_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer "+responseEntity.getBody().getJwtToken();
		
		
		RequestingAppointmentDTO newAppointemnt = new RequestingAppointmentDTO(
				AppointmentConstants.APPOINTMENT_ID, 
				AppointmentConstants.DATE, 
				AppointmentConstants.TYPE, 
				AppointmentConstants.DOCTOR_NAME, 
				AppointmentConstants.NURSE_NAME, 
				AppointmentConstants.PATIENT_NAME, 
				AppointmentConstants.TYPE_ID, 
				AppointmentConstants.DURATION);
		requests.add(newAppointemnt);
	}
	
	@Test
	public void smokeTestNotFound() {
		try {

			mockMvc.perform(get(AppointmentConstants.REQUEST_ONE_NOT_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
				    .andExpect(status().isNotFound());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void smokeTestOK() {
		try {

			Mockito.when(
					appointmentService.getAllRequests(
							Mockito.any(String.class))
					).thenReturn(requests);
			
			mockMvc.perform(get(AppointmentConstants.REQUEST_ALL_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
				    .andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAll() throws Exception {
		Mockito.when(appointmentService.getAllRequests(Mockito.any(String.class))).thenReturn(requests);
		
		try {
			
			this.mockMvc.perform(get(AppointmentConstants.REQUEST_ALL_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(AppointmentConstants.APPOINTMENT_ID.intValue())));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testOne() throws Exception {
		Mockito.when(appointmentService.getOneRequests(Mockito.any(Long.class))).thenReturn(requests.get(0));
		
		try {
			
			this.mockMvc.perform(get(AppointmentConstants.REQUEST_ONE_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.id").value(AppointmentConstants.APPOINTMENT_ID.intValue()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
