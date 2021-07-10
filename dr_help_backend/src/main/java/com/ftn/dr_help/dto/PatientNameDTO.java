package com.ftn.dr_help.dto;

public class PatientNameDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private Long insuranceNumber;
	
	public PatientNameDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PatientNameDTO(Long id, String firstName, String lastName, Long insuranceNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.insuranceNumber = insuranceNumber;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getInsuranceNumber() {
		return insuranceNumber;
	}
	public void setInsuranceNumber(Long insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	
	
}
