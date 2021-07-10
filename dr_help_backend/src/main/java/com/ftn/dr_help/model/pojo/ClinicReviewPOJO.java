package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clinicRewiew")
public class ClinicReviewPOJO implements Serializable {

	public ClinicReviewPOJO(ClinicPOJO clinic, PatientPOJO patient, Integer rating) {
		super();
		this.clinic = clinic;
		this.patient = patient;
		this.rating = rating;
	}

	public ClinicReviewPOJO(Long id, ClinicPOJO clinic, PatientPOJO patient, Integer rating) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.patient = patient;
		this.rating = rating;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne (fetch = FetchType.LAZY)
	private ClinicPOJO clinic;

	@OneToOne (fetch = FetchType.LAZY)
	private PatientPOJO patient;
	
	@Column (name = "rating", nullable = true)
	private Integer rating;
	
	public ClinicReviewPOJO() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PatientPOJO getPatient() {
		return patient;
	}
	public void setPatient(PatientPOJO patient) {
		this.patient = patient;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public ClinicPOJO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicPOJO clinic) {
		this.clinic = clinic;
	}
}
