package com.ftn.dr_help.dto;

public class AppointmentForSchedulingDTO {

	private String patientEmail;
	private String doctorEmail;
	private Long roomId;
	private Long procedureId;
	private String dateAndTime; //yyyy-MM-dd HH:mm
	private Long appointmentRequestedId;
	
	public AppointmentForSchedulingDTO() {
		super();
	}
	
	public AppointmentForSchedulingDTO(String patientEmail, String doctorEmail, Long roomId,
			Long procedureId, String dateAndTime, Long appointmentId) {
		super();
		this.patientEmail = patientEmail;
		this.doctorEmail = doctorEmail;
		this.roomId = roomId;
		this.procedureId = procedureId;
		this.dateAndTime = dateAndTime;
		this.appointmentRequestedId = appointmentId;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getDoctorEmail() {
		return doctorEmail;
	}

	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(Long procedureId) {
		this.procedureId = procedureId;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Long getAppointmentRequestedId() {
		return appointmentRequestedId;
	}

	public void setAppointmentRequestedId(Long appointmentRequestedId) {
		this.appointmentRequestedId = appointmentRequestedId;
	}

}
