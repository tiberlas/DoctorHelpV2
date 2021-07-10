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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "diagnosispojo")
public class DiagnosisPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "diagnosis", nullable = false)
	private String diagnosis;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@JsonBackReference
	@OneToMany(mappedBy = "diagnosis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PerscriptionPOJO> perscription;
	
	public DiagnosisPOJO() {
		
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<PerscriptionPOJO> getPerscription() {
		return perscription;
	}

	public void setPerscription(List<PerscriptionPOJO> perscription) {
		this.perscription = perscription;
	}
	
	
	
}
