package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TherapyPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "advice", nullable = true)
	private String advice;
	
	@OneToOne (fetch = FetchType.LAZY)
	@JsonIgnore
	private PerscriptionPOJO perscription;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
//	public PerscriptionPOJO getPerscription() {
//		return perscription;
//	}
//	public void setPerscription(PerscriptionPOJO perscription) {
//		this.perscription = perscription;
//	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
