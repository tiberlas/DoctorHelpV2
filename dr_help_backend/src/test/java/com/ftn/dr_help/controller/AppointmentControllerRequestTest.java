package com.ftn.dr_help.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.dto.AddAppointmentDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.service.AppointmentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentControllerRequestTest {

	
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
	public void testRequestingAnAppointment() throws Exception {
		
		AddAppointmentDTO dto = new AddAppointmentDTO();
		dto.setDoctorId("1");
		dto.setPatientId("1");
		dto.setDate("2020-02-12");
		dto.setTime("0:15");
		
		String dateTime = dto.getDate() + " " + dto.getTime() + ":00";
		String json = TestUtil.json(dto);
		
		Mockito.when(this.appointmentService.addAppointment(
				Long.parseLong(dto.getDoctorId()), 
				dateTime, 
				Long.parseLong(dto.getPatientId()))).thenReturn(true);
		
		mockMvc.perform(post("/api/appointments/add")
				.contentType(contentType)
				.header("Authorization", accessToken)
				.content(json))
				.andExpect(content().string("true"))
				.andExpect(status().isOk());
	}
}
