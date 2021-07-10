package com.ftn.dr_help.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;

import org.hamcrest.collection.HasItemInArray;
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
import com.ftn.dr_help.constants.RoomsConstants;
import com.ftn.dr_help.constants.UserConstants;
import com.ftn.dr_help.dto.LoginRequestDTO;
import com.ftn.dr_help.dto.LoginResponseDTO;
import com.ftn.dr_help.dto.RoomSearchDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RoomIntegrationTest {

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
	public void testAll() {
		try {
			
			this.mockMvc.perform(get("/api/rooms/all")
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$", hasSize(5)));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testOne() {
		try {
			
			this.mockMvc.perform(get("/api/rooms/one/room=2")
					.contentType(contentType)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$.id").value(2));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSearchNone() {
		try {
			RoomSearchDTO room = new RoomSearchDTO(
					"", 
					41l, 
					2l, 
					"");
			
			String json = TestUtil.json(room);
			this.mockMvc.perform(post("/api/rooms/search")
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSearchOpste() {
		try {
			RoomSearchDTO room = new RoomSearchDTO(
					"op", 
					null, 
					2l, 
					null);
			
			String json = TestUtil.json(room);
			this.mockMvc.perform(post("/api/rooms/search")
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$", hasSize(2)));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSearchOpsta() {
		try {
			RoomSearchDTO room = new RoomSearchDTO(
					"opšta a", 
					null, 
					2l, 
					null);
			
			String json = TestUtil.json(room);
			this.mockMvc.perform(post("/api/rooms/search")
					.contentType(contentType)
					.content(json)
					.header("Authorization", accessToken))
			.andDo(print())
			.andExpect(status()
					.isOk())
			.andExpect(content().contentType(contentType))
	        .andExpect(jsonPath("$", hasSize(1)))
	        .andExpect(jsonPath("$.[*].id").value(hasItem(2)));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	



}