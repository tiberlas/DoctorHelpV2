package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.UserRequestPOJO;

public class PatientRequestDTO {

	private String email;
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String address;
	private String phoneNumber;
	private Long insuranceNumber;
	private String declinedDescription;
	private String password;
	
	
	public PatientRequestDTO(String email, String firstName, String lastName, String city, String address, String phoneNumber,
			Long insuranceNumber, String state, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.insuranceNumber = insuranceNumber;
		this.state = state;
		this.password = password;
	}
	
	public PatientRequestDTO(String email, String declinedDescription) {
		super();
		this.email = email;
		this.declinedDescription = declinedDescription;
	}
	
	public PatientRequestDTO(String email) {
		super();
		this.email = email;
	}
	
	
	public PatientRequestDTO() {
		
	}
	
	public PatientRequestDTO(String email, String firstName, String lastName, String city, String address, String phoneNumber,
			Long insuranceNumber, String declinedDescription, String state, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.insuranceNumber = insuranceNumber;
		this.declinedDescription = declinedDescription;
		this.state = state;
	}

	public PatientRequestDTO(UserRequestPOJO userRequest) {
		this(userRequest.getEmail(), userRequest.getFirstName(), userRequest.getLastName(),
				userRequest.getCity(), userRequest.getAddress(), userRequest.getPhoneNumber(), userRequest.getInsuranceNumber(), 
				userRequest.getState(), userRequest.getPassword());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(Long insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getDeclinedDescription() {
		return declinedDescription;
	}

	public void setDeclinedDescription(String declinedDescription) {
		this.declinedDescription = declinedDescription;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
