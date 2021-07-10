package com.ftn.dr_help.dto;

import java.util.List;

import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.model.pojo.PerscriptionPOJO;

public class PerscriptionDisplayDTO {

	private String Diagnosis = "No diagnosis established";
	private String Description;
	private String Advice;
	private List<MedicationDisplayDTO> medicationList;
	private Long clinicId;
	
	public PerscriptionDisplayDTO() {
		super();
	}
	
	
	public PerscriptionDisplayDTO(PerscriptionPOJO per) {
		
		System.out.println("PERSCRIPTION: " + per.getDiagnosis().getDiagnosis() + " " + per.getDiagnosis().getPerscription());
		
		this.Diagnosis = per.getDiagnosis().getDiagnosis();
		this.Description = per.getDiagnosis().getDescription();
		
		for (MedicationPOJO m : per.getMedicationList()) {
			MedicationDisplayDTO mdDTO = new MedicationDisplayDTO();
			mdDTO.setMedicationName(m.getMedicationName());
			mdDTO.setMedicationDescription(m.getMedDescription());
			medicationList.add(mdDTO);
		}
		
		this.Advice = per.getTherapy().getAdvice();
		
		this.clinicId = per.getExaminationReport().getClinic().getId(); //irrelevant info for me right now
	}
	
	public PerscriptionDisplayDTO(String diagnosis, String description, String advice,
			List<MedicationDisplayDTO> medicationList, Long clinicId) {
		super();
		Diagnosis = diagnosis;
		Description = description;
		Advice = advice;
		this.medicationList = medicationList;
		this.clinicId = clinicId;
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
	public Long getClinicId() {
		return clinicId;
	}
	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}
	
}
