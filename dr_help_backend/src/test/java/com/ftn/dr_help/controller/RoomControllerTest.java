package com.ftn.dr_help.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.ftn.dr_help.TestUtil;
import com.ftn.dr_help.constants.RoomsConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.dto.RoomDTO;
import com.ftn.dr_help.dto.RoomSearchDTO;
import com.ftn.dr_help.service.RoomService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RoomControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	private String accessToken;
	
	private MockMvc mockMvc;
	
	@MockBean
	private RoomService roomService;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());
	
	private List<RoomDTO> roomList;
	
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
		
		RoomDTO room = new RoomDTO(
				RoomsConstants.ID,
				RoomsConstants.NAME,
				RoomsConstants.NUMBER,
				RoomsConstants.PROCEDURE_NAME,
				RoomsConstants.PROCEDURE_ID,
				RoomsConstants.RESERVED,
				RoomsConstants.FIRST_FREE);
		
		roomList = new ArrayList<>();
		roomList.add(room);
	}
	
	@Test
	public void testAll() {
		Mockito.when(roomService.findAll(Mockito.any(String.class))).thenReturn(roomList);
		
		try {
			
			this.mockMvc.perform(get(RoomsConstants.ALL_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(RoomsConstants.ID.intValue())));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testOne() {
		Mockito.when(roomService.findOne(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(roomList.get(0));
		
		try {
			
			this.mockMvc.perform(get(RoomsConstants.ONE_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.id").value(RoomsConstants.ID.intValue()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testOneFail() {
		Mockito.when(roomService.findOne(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(roomList.get(0));
		
		try {
			
			this.mockMvc.perform(get(RoomsConstants.ONE_NOT_URL)
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isNotFound());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSearch() {
		RoomSearchDTO room = new RoomSearchDTO(
				RoomsConstants.NAME, 
				new Long(RoomsConstants.NUMBER), 
				RoomsConstants.PROCEDURE_ID, 
				RoomsConstants.FIRST_FREE);

		Mockito.when(roomService.search(Mockito.any(RoomSearchDTO.class), Mockito.any(String.class))).thenReturn(roomList);
		
		try {
			
			String json = TestUtil.json(room);
			this.mockMvc.perform(post(RoomsConstants.SEARCH_URL)			
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(RoomsConstants.ID.intValue())));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test() {
		
	}

}
