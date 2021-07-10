package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.CreatingPredefinedAppointmentEnum;

public class PredefinedAppointmentResponseDTO {

	private String dateAndTime;
	private CreatingPredefinedAppointmentEnum status;
	
	public PredefinedAppointmentResponseDTO() {
		super();
	}
	
	public PredefinedAppointmentResponseDTO(String dateAndTime, CreatingPredefinedAppointmentEnum status) {
		super();
		this.dateAndTime = dateAndTime;
		this.status = status;
	}
	
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public CreatingPredefinedAppointmentEnum getStatus() {
		return status;
	}
	public void setStatus(CreatingPredefinedAppointmentEnum status) {
		this.status = status;
	}
	
	
	
}
