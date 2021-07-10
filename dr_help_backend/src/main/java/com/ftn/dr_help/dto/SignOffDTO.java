package com.ftn.dr_help.dto;

import java.util.List;

public class SignOffDTO {

	private String Diagnosis;
	private String Description;
	private String Advice;
	private List<MedicationDisplayDTO> medicationList;
	private String doctor;
	private String patient;
	private Long perscriptionId;
	
	private boolean isSigned;
	
	
	public SignOffDTO() {
		super();
	}
	
	
	
	public SignOffDTO(String diagnosis, String description, String advice,
			List<MedicationDisplayDTO> medicationList, String doctor,
			String patient, Long perscriptionId) {
		super();
		Diagnosis = diagnosis;
		Description = description;
		Advice = advice;
		this.medicationList = medicationList;
		this.doctor = doctor;
		this.patient = patient;
		this.perscriptionId = perscriptionId;
	}



	public String getDiagnosis() {
		return Diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		Diagnosis = diagnosis;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getAdvice() {
		return Advice;
	}
	public void setAdvice(String advice) {
		Advice = advice;
	}
	public List<MedicationDisplayDTO> getMedicationList() {
		return medicationList;
	}
	public void setMedicationList(List<MedicationDisplayDTO> medicationList) {
		this.medicationList = medicationList;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}



	public Long getPerscriptionId() {
		return perscriptionId;
	}



	public void setPerscriptionId(Long perscriptionId) {
		this.perscriptionId = perscriptionId;
	}



	public boolean isSigned() {
		return isSigned;
	}



	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	
	
	
}
