package com.ftn.dr_help.controller;

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
import static org.hamcrest.Matchers.hasItem;
import com.ftn.dr_help.constants.ProcedureConstants;
import com.ftn.dr_help.constants.RoomsConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.dto.ProcedureTypeDTO;
import com.ftn.dr_help.service.ProcedureTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ProcedureTypeControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	private String accessToken;
	
	private MockMvc mockMvc;
	
	@MockBean
	private ProcedureTypeService typeService;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());
	
	private List<ProcedureTypeDTO> procedureList;
	
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
		
		ProcedureTypeDTO procedure = new ProcedureTypeDTO(
									ProcedureConstants.ID, 
									ProcedureConstants.NAME, 
									ProcedureConstants.PRICE, 
									ProcedureConstants.DURATION, 
									ProcedureConstants.OPERATION, 
									ProcedureConstants.USE);
		
		procedureList = new ArrayList<>();
		procedureList.add(procedure);
	}
	
	@Test
	public void testAll() {
		Mockito.when(typeService.getAll(Mockito.any(String.class))).thenReturn(procedureList);
		
		try {
			
			this.mockMvc.perform(get(ProcedureConstants.URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ProcedureConstants.ID.intValue())));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
