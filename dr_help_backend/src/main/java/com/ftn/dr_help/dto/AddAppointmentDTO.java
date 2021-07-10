package com.ftn.dr_help.dto;

public class AddAppointmentDTO {

	public AddAppointmentDTO(String doctorId, String date, String time, String patientId) {
		super();
		this.doctorId = doctorId;
		this.date = date;
		this.time = time;
		this.patientId = patientId;
	}
	public AddAppointmentDTO() {
		super();
	}
	private String doctorId;
	private String date;
	private String time;
	private String patientId;
	
	
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	
}
