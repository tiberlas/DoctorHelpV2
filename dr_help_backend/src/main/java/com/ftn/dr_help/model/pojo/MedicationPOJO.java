package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class MedicationPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name = "medicationName", nullable = false, unique = true)
	private String medicationName;
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "medDescription", nullable = true)
	private String medDescription;
	
	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medicationList")
	private List<PerscriptionPOJO> perscription;
	
	public MedicationPOJO () {
		
	}
	
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMedDescription() {
		return medDescription;
	}
	public void setMedDescription(String medDescription) {
		this.medDescription = medDescription;
	}

	public List<PerscriptionPOJO> getPerscription() {
		return perscription;
	}

	public void setPerscription(List<PerscriptionPOJO> perscription) {
		this.perscription = perscription;
	}

	
	
}
