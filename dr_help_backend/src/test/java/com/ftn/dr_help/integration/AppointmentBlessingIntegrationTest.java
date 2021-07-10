package com.ftn.dr_help.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
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
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.AppointmentForSchedulingDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AppointmentBlessingIntegrationTest {

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
						new LoginRequestDTO(UserConstants.CLINIC_ADMIN_MAIL, UserConstants.CLINIC_ADMIN_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer "+responseEntity.getBody().getJwtToken();
	}
	
	@Test
	public void testBless() {
		try {
			AppointmentForSchedulingDTO appointment = new AppointmentForSchedulingDTO(
					"digimon@gmail.com", 
					"j.milinkovic@gmail",
					2l,
					2l,
					"2020-02-04 15:30",
					22l);
			
			String json = TestUtil.json(appointment);
			this.mockMvc.perform(post("/api/appointments/bless")
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isAccepted());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testBlessNot() {
		try {
			AppointmentForSchedulingDTO appointment = new AppointmentForSchedulingDTO(
					"happymeal@gmail.com", 
					"pera@gmail",
					2l,
					2l,
					"2020-02-01 12:30",
					40l);
			
			String json = TestUtil.json(appointment);
			this.mockMvc.perform(post("/api/appointments/bless")
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isNotAcceptable());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
