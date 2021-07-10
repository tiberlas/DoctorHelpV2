package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ExaminationReportPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PerscriptionPOJO perscription;
	
	@OneToOne(fetch = FetchType.LAZY)
	private AppointmentPOJO appointment;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private HealthRecordPOJO healthRecord;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private ClinicPOJO clinic;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PerscriptionPOJO getPerscription() {
		return perscription;
	}
	public void setPerscription(PerscriptionPOJO perscription) {
		this.perscription = perscription;
	}
	public AppointmentPOJO getAppointment() {
		return appointment;
	}
	public void setAppointment(AppointmentPOJO appointment) {
		this.appointment = appointment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public HealthRecordPOJO getHealthRecord() {
		return healthRecord;
	}
	public void setHealthRecord(HealthRecordPOJO healthRecord) {
		this.healthRecord = healthRecord;
	}
	public ClinicPOJO getClinic() {
		return clinic;
	}
	public void setClinic(ClinicPOJO clinic) {
		this.clinic = clinic;
	}
	
}
