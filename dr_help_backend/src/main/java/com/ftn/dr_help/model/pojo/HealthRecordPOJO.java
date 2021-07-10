package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ftn.dr_help.model.enums.BloodTypeEnum;

@Entity
@Table(name = "healthrecord")
public class HealthRecordPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="healthRecord")
	private List<AllergyPOJO> allergyList;

	
	@Column(name = "weight", nullable = true)
	private Double weight;
	
	@Column(name = "height", nullable = true)
	private Double height;
	
	@Column(name = "diopter", nullable = true)
	private Double diopter;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "bloodType", nullable = true)
	private BloodTypeEnum bloodType;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ExaminationReportPOJO> examinationReport;


	@OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "healthRecord")
	private PatientPOJO patient;
	
	
	public HealthRecordPOJO () {
		super ();
	}
	
	public List<AllergyPOJO> getAllergyList() {
		return allergyList;
	}
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setAlergyList(List<AllergyPOJO> alergyList) {
		this.allergyList = alergyList;


	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Double getDiopter() {
		return diopter;
	}
	public void setDiopter(double diopter) {
		this.diopter = diopter;
	}
	public BloodTypeEnum getBloodType() {
		return bloodType;
	}
	public void setBloodType(BloodTypeEnum bloodType) {
		this.bloodType = bloodType;
	}

	public List<ExaminationReportPOJO> getExaminationReport() {
		return examinationReport;
	}

	public void setExaminationReport(List<ExaminationReportPOJO> examinationReport) {
		this.examinationReport = examinationReport;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PatientPOJO getPatient() {
		return patient;
	}

	public void setPatient(PatientPOJO patient) {
		this.patient = patient;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void setDiopter(Double diopter) {
		this.diopter = diopter;
	}
	
	
	
}
