package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.MedicationPOJO;

public class MedicationDTO {

	private String name;
	private String description;
	private Long id;
	
	private Boolean reserved;
	
	
	
	public MedicationDTO() {
		
	}
	
	public MedicationDTO(MedicationPOJO med) {
		this.name = med.getMedicationName();
		this.description = med.getMedDescription();
	}
	
	public MedicationDTO(String name, String description) {
		super();
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
