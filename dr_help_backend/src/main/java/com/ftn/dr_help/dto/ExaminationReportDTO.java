package com.ftn.dr_help.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExaminationReportDTO {

	private String diagnosis;
	private ArrayList<String> medicationList;
	private String note;
	private Date dateStart;
	
	private List<MedicationDisplayDTO> medicationArray; //idk man ovo koristim
	private boolean myExamination; ///myExamination -> da li je obavljeni appointment bas od strane ovog doktora koji trazi
	
	private boolean nurseSigned;
	private Long nurseId;
	private String nurse;
	
	public ExaminationReportDTO() {
		
	}
	
	public ExaminationReportDTO(String diagnosis,
			ArrayList<String> medicationList, String note) {
		super();
		this.diagnosis = diagnosis;
		this.medicationList = medicationList;
		this.note = note;
	}

	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public ArrayList<String> getMedicationList() {
		return medicationList;
	}
	public void setMedicationList(ArrayList<String> medicationList) {
		this.medicationList = medicationList;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public boolean isMyExamination() {
		return myExamination;
	}

	public void setMyExamination(boolean myExamination) {
		this.myExamination = myExamination;
	}

	public List<MedicationDisplayDTO> getMedicationArray() {
		return medicationArray;
	}

	public void setMedicationArray(List<MedicationDisplayDTO> medicationArray) {
		this.medicationArray = medicationArray;
	}

	public boolean isNurseSigned() {
		return nurseSigned;
	}

	public void setNurseSigned(boolean nurseSigned) {
		this.nurseSigned = nurseSigned;
	}

	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}
	
}
