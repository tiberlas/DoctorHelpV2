package com.ftn.dr_help.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.ftn.dr_help.constants.ClinicConstants;
import com.ftn.dr_help.constants.LoginConstants;
import com.ftn.dr_help.dto.ClinicListingDTO;
import com.ftn.dr_help.dto.ClinicPreviewDTO;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.service.ClinicService;
import com.ftn.dr_help.service.ProcedureTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ClinicControllerListingTest {

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
	
	// JWT token
	private String accessToken;
	
	@MockBean
	private ClinicService clinicService;
	
	@MockBean
	private ProcedureTypeService procedureTypeService;
	
	
	@Before
	public void login() {
		ResponseEntity<LoginResponseDTO> responseEntity = 
				restTemplate.postForEntity("/api/login", 
						new LoginRequestDTO(LoginConstants.PATIENT_USERNAME, LoginConstants.PATIENT_PASSWORD), 
						LoginResponseDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getJwtToken();
	}
	
	@Test
	public void getClinicListFilteredByProcedureType() throws Exception {
		
		
		List<String> mockProcedureTypes = new ArrayList<String>();
		mockProcedureTypes.add("Nikolina psiho-analiza");
		mockProcedureTypes.add("Nikolina operacija");
		
		List<ClinicPreviewDTO> mockClinicDTO = new ArrayList<>();
		mockClinicDTO.add(new ClinicPreviewDTO(1L, "Nikolina bolnica", 
				"Nikolina adresa u lil twat",
				"Nikolin grad",
				"Nikolina drzava",
				"Nikolina ocena",
				"Nikolina cena"));
		
		ClinicListingDTO clinicList = new ClinicListingDTO(mockClinicDTO, 
				mockProcedureTypes, ClinicConstants.UNFILTERED, ClinicConstants.UNFILTERED);
		clinicList.setAddressString("Nikolina adresa");
		clinicList.setCityString("Nikolin grad");
		clinicList.setStateString("Nikolino stanje");
		clinicList.setProcedureTypeString(ClinicConstants.PROCEDURE_TYPE_FILTER);
		
		Mockito.when(this.procedureTypeService.getProcedureTypes()).thenReturn(mockProcedureTypes);
		Mockito.when(this.clinicService.doOtherFilters(Mockito.any(ClinicListingDTO.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class), 
				Mockito.any(String.class)))
				.thenReturn(clinicList);
		
		mockMvc.perform(
				get("/api/clinics/listing/proc_type="+ClinicConstants.PROCEDURE_TYPE_FILTER+"/date="+ClinicConstants.UNFILTERED +
						"/stat="+ClinicConstants.UNFILTERED+"/cit="+ClinicConstants.UNFILTERED+"/adr="+ClinicConstants.UNFILTERED+"/min_rat="+ClinicConstants.UNFILTERED
						+ "/max_rat="+ClinicConstants.UNFILTERED+"/min_price="+ClinicConstants.UNFILTERED + "/max_price="+ClinicConstants.UNFILTERED)
						.contentType(contentType)
						.header("Authorization", accessToken))
						.andExpect(jsonPath("$.clinicList").isArray())
						.andExpect(jsonPath("$.procedureTypeString").value(ClinicConstants.PROCEDURE_TYPE_FILTER))
						.andExpect(status().isOk());
	}
}
