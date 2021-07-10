package com.ftn.dr_help.dto;

import java.util.Calendar;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;

public class PatientDTO {
	
	private DateConverter dateConverter = new DateConverter();
	
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
	private boolean isActivated;
	private String birthday;
	
	private boolean showHealthRecord;
	
	
	
	public PatientDTO(String email, String firstName, String lastName, String city, String address, String phoneNumber,
			Long insuranceNumber, String state, String password, boolean isActivated, Calendar birthday) {
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
		this.isActivated = isActivated;
		this.birthday = dateConverter.toString(birthday);
		
	}
	
	public PatientDTO(PatientPOJO p) {
		super();
		this.email = p.getEmail();
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.city = p.getCity();
		this.address = p.getAddress();
		this.phoneNumber = p.getPhoneNumber();
		this.insuranceNumber = p.getInsuranceNumber();
		this.state = p.getState();
		this.password = p.getPassword();
		this.isActivated = p.isActivated();
		this.birthday = dateConverter.toString(p.getBirthday());
	}
	
	public PatientDTO(String email, String declinedDescription) {
		super();
		this.email = email;
		this.declinedDescription = declinedDescription;
	}
	
	public PatientDTO(String email) {
		super();
		this.email = email;
	}
	
	
	public PatientDTO() {
		
	}
	
	public PatientDTO(String email, String firstName, String lastName, String city, String address, String phoneNumber,
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

	public PatientDTO(UserRequestPOJO userRequest) {
		this(userRequest.getEmail(), userRequest.getFirstName(), userRequest.getLastName(),
				userRequest.getCity(), userRequest.getAddress(), userRequest.getPhoneNumber(), userRequest.getInsuranceNumber(), 
				userRequest.getState(), userRequest.getPassword(), false, userRequest.getBirthday());
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = dateConverter.toString(birthday);
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

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isShowHealthRecord() {
		return showHealthRecord;
	}

	public void setShowHealthRecord(boolean showHealthRecord) {
		this.showHealthRecord = showHealthRecord;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
