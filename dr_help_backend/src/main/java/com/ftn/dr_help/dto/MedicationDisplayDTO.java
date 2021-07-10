package com.ftn.dr_help.dto;

public class MedicationDisplayDTO {

	public MedicationDisplayDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MedicationDisplayDTO(String medicationName, String medicationDescription) {
		super();
		this.medicationName = medicationName;
		this.medicationDescription = medicationDescription;
	}
	private String medicationName;
	private String medicationDescription;
	
	
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public String getMedicationDescription() {
		return medicationDescription;
	}
	public void setMedicationDescription(String medicationDescription) {
		this.medicationDescription = medicationDescription;
	}
	
}
