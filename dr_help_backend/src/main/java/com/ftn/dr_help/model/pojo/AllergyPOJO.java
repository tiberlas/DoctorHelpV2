package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class AllergyPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "allergy", nullable = false) 
	private String allergy;
	
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private HealthRecordPOJO healthRecord;
	
	
	public AllergyPOJO() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public HealthRecordPOJO getHealthRecord() {
		return healthRecord;
	}


	public void setHealthRecord(HealthRecordPOJO healthRecord) {
		this.healthRecord = healthRecord;
	}


	public String getAllergy() {
		return allergy;
	}


	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

}
