package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class PerscriptionPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DiagnosisPOJO diagnosis;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MedicationPOJO> medicationList;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private NursePOJO signingNurse;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JsonManagedReference
	private TherapyPOJO therapy;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private ExaminationReportPOJO examinationReport;
	
	public DiagnosisPOJO getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(DiagnosisPOJO diagnosis) {
		this.diagnosis = diagnosis;
	}
	public List<MedicationPOJO> getMedicationList() {
		return medicationList;
	}
	public void setMedicationList(ArrayList<MedicationPOJO> medicationList) {
		this.medicationList = medicationList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public NursePOJO getSigningNurse () {
		return this.signingNurse;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSigningNurse(NursePOJO signingNurse) {
		this.signingNurse = signingNurse;
	}
	public TherapyPOJO getTherapy() {
		return therapy;
	}
	public void setTherapy(TherapyPOJO therapy) {
		this.therapy = therapy;
	}
	public ExaminationReportPOJO getExaminationReport() {
		return examinationReport;
	}
	public void setExaminationReport(ExaminationReportPOJO examinationReport) {
		this.examinationReport = examinationReport;
	}
	public void setMedicationList(List<MedicationPOJO> medicationList) {
		this.medicationList = medicationList;
	}
	

}
