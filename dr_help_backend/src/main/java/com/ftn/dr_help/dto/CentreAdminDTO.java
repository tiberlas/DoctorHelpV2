package com.ftn.dr_help.dto;

import java.util.Date;

import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;

public class CentreAdminDTO {

	
	private String email;
	private String firstName;
	private String lastName;
	
	private Date birthday;
	
	public CentreAdminDTO() {
		
	}
	
	
	public CentreAdminDTO(String email, String firstName, String lastName, Date birthday) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}
	
	public CentreAdminDTO(String email, String firstName, String lastName) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public CentreAdminDTO(CentreAdministratorPOJO admin) {
		this(admin.getEmail(), admin.getFirstName(), admin.getLastName());
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
