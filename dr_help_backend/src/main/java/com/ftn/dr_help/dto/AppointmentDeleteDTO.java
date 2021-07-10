package com.ftn.dr_help.dto;

public class AppointmentDeleteDTO {

	public AppointmentDeleteDTO(Long appointmentId, Long patientId) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
	}

	public AppointmentDeleteDTO(Long appointmentId) {
		super();
		this.appointmentId = appointmentId;
	}

	public AppointmentDeleteDTO() {
		super();
	}

	private Long appointmentId;
	private Long patientId;

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	
}
