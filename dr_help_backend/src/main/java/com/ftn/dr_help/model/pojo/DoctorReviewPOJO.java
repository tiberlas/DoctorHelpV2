package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DoctorReviewPOJO implements Serializable {

	public DoctorReviewPOJO(DoctorPOJO doctor, PatientPOJO patient, Integer rating) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.rating = rating;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private DoctorPOJO doctor;
	
	@OneToOne(fetch = FetchType.LAZY)
	private PatientPOJO patient;
	
	@Column(name = "rating", nullable = true)
	private Integer rating;
	
	public DoctorReviewPOJO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DoctorPOJO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorPOJO doctor) {
		this.doctor = doctor;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
