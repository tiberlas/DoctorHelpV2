package com.ftn.dr_help.model.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "doctor_requested")
public class DoctorRequestedAppointmentPOJO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DoctorPOJO doctor;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private AppointmentPOJO appointment;	
	
	public DoctorRequestedAppointmentPOJO() {
		super();
		// TODO Auto-generated constructor stub
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

	public AppointmentPOJO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentPOJO appointment) {
		this.appointment = appointment;
	}
	
	
	
	
}
