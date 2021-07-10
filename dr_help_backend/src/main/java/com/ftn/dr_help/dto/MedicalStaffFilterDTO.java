package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.FilterMedicalStaffEnum;

public class MedicalStaffFilterDTO {

	private String string;
	private FilterMedicalStaffEnum role;
	
	public MedicalStaffFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalStaffFilterDTO(String string, FilterMedicalStaffEnum role) {
		super();
		this.string = string;
		this.role = role;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public FilterMedicalStaffEnum getRole() {
		return role;
	}

	public void setRole(FilterMedicalStaffEnum role) {
		this.role = role;
	}
	
}
