package com.ftn.dr_help.dto;

public class DoctorRequestAppointmentDTO {
	
	private Long oldAppointmentID;
	private String dateAndTime;
	
	public DoctorRequestAppointmentDTO() {
		super();
	}
	
	public DoctorRequestAppointmentDTO(Long oldAppointmentID, String dateAndTime) {
		super();
		this.oldAppointmentID = oldAppointmentID;
		this.dateAndTime = dateAndTime;
	}

	public Long getOldAppointmentID() {
		return oldAppointmentID;
	}

	public void setOldAppointmentID(Long oldAppointmentID) {
		this.oldAppointmentID = oldAppointmentID;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	
	
}
