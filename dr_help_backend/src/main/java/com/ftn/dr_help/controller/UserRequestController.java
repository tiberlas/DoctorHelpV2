package com.ftn.dr_help.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.dto.UserRequestDTO;
import com.ftn.dr_help.dto.UserResponseDTO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;
import com.ftn.dr_help.service.UserRequestService;

@RestController
@RequestMapping (value = "/api")
public class UserRequestController {

	@Autowired
	private UserRequestService userRequestService;
	
	@PostMapping (value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserResponseDTO> register (@RequestBody UserRequestDTO userRequest) {
		System.out.println("asda");
		UserResponseDTO retVal = new UserResponseDTO ();

		if (userRequestService.patientExists(userRequest.getEmail()) ) {
			retVal.setResponse("An account under this email address already exists. ");
			System.out.println("1");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.insuranceNumberExists(userRequest.getInsuranceNumber())) {
			retVal.setResponse("An account with this insurance number already exists.1 ");
			System.out.println("2");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.NOT_ACCEPTABLE);
		}
		else if (userRequestService.doctorExists(userRequest.getEmail())) {
			retVal.setResponse("An account with this insurance number already exists. ");
			System.out.println("3");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.nurseExists(userRequest.getEmail())) {
			retVal.setResponse("An account under this email address already exists. ");
			System.out.println("4");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.clinicalAdminExists(userRequest.getEmail())) {
			retVal.setResponse("An account under this email address already exists. ");
			System.out.println("5");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.centreAdministratorExists(userRequest.getEmail())) {
			retVal.setResponse("An account under this email address already exists. ");
			System.out.println("6");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.requestEmailExists(userRequest.getEmail())) {
			retVal.setResponse("A request with this email address already exists. ");
			System.out.println("7");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.CONFLICT);
		}
		else if (userRequestService.requestInsuranceNumberExists(userRequest.getInsuranceNumber())) {
			retVal.setResponse("A request with this insurance number already exists.2 ");
			System.out.println("8");
			return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.NOT_ACCEPTABLE);
		}
		
		Calendar birthday = Calendar.getInstance();
		birthday.setTime(userRequest.getBirthday());
		UserRequestPOJO validRequest = new UserRequestPOJO ();
		validRequest.setEmail(userRequest.getEmail());
		validRequest.setPassword(userRequest.getPassword());
		validRequest.setFirstName(userRequest.getFirstName());
		validRequest.setLastName(userRequest.getLastName());
		validRequest.setAddress(userRequest.getAddress());
		validRequest.setCity(userRequest.getCity());
		validRequest.setState(userRequest.getState());
		validRequest.setPhoneNumber(userRequest.getPhoneNumber());
		validRequest.setBirthday(birthday);
		validRequest.setInsuranceNumber(Long.parseLong(userRequest.getInsuranceNumber()));
		
		userRequestService.addNewRequest(validRequest);
		
		retVal.setResponse("All is in order");
		return new ResponseEntity<UserResponseDTO> (retVal, HttpStatus.OK);
	}
	
}
