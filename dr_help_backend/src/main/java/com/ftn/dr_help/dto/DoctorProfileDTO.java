package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.DoctorPOJO;

public class DoctorProfileDTO {
	
	private Long id;
	private Long procedureTypeId;
	private String firstName;
	private String lastName;
	private String email;
	private boolean deleted;
	
	public DoctorProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DoctorProfileDTO(DoctorPOJO pojo) {
		super();
		this.id = pojo.getId();
		this.firstName = pojo.getFirstName();
		this.lastName = pojo.getLastName();
		this.email = pojo.getEmail();
		this.deleted = pojo.isDeleted();
		this.procedureTypeId = pojo.getProcedureType().getId();
	}
	
	public DoctorProfileDTO(Long id, String firstName, String lastName, String email, boolean deleted, Long procedureTypeId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.deleted = deleted;
		this.procedureTypeId = procedureTypeId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}
	
	

}
