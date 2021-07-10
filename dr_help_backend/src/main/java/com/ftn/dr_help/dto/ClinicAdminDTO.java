package com.ftn.dr_help.dto;

import java.util.Date;

import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;

public class ClinicAdminDTO {
	
	private String email;
	private String firstName;
	private String lastName;
	private Long id;
	
	private Date birthday;
	
	private String clinicName;
	
	public ClinicAdminDTO() {
		
	}
	
	public ClinicAdminDTO(String email, String firstName, String lastName, Date birthday) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}
	
	
	public ClinicAdminDTO(String email, String firstName, String lastName, Long id, Date birthday) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.birthday = birthday;
	}

	public ClinicAdminDTO(ClinicAdministratorPOJO admin) {
		this(admin.getEmail(), admin.getFirstName(), admin.getLastName(), admin.getClinic().getId(), admin.getBirthday().getTime());
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
}
