package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.DiagnosisPOJO;

public class DiagnosisDTO {

	Long id;
	private String name;
	private String description;
	private Boolean reserved;
	
	
	public DiagnosisDTO() {
		
	}
	
	public DiagnosisDTO(DiagnosisPOJO med) {
		super();
		this.id = med.getId();
		this.name = med.getDiagnosis();
		this.description = med.getDescription();
	}
	
	public DiagnosisDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public DiagnosisDTO(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}
}