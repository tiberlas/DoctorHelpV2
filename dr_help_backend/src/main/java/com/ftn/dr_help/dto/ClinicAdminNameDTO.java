package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;

public class ClinicAdminNameDTO {
	
	private String firstName;
	private String lastName;
	
	public ClinicAdminNameDTO(ClinicAdministratorPOJO adminPojo) {
		this.firstName = adminPojo.getFirstName();
		this.lastName = adminPojo.getLastName();
	}
	
	public ClinicAdminNameDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firsName) {
		this.firstName = firsName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
