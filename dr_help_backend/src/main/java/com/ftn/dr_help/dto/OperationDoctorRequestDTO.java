package com.ftn.dr_help.dto;

public class OperationDoctorRequestDTO {
	
	private Long appointmentId;
	private String dateAndTimeString;
	private Long procedureTypeId;
	
	public OperationDoctorRequestDTO() {
		super();
	}
	
	public OperationDoctorRequestDTO(Long appointmentId, String dateAndTimeString, Long procedureTypeId) {
		super();
		this.appointmentId = appointmentId;
		this.dateAndTimeString = dateAndTimeString;
		this.procedureTypeId = procedureTypeId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDateAndTimeString() {
		return dateAndTimeString;
	}

	public void setDateAndTimeString(String dateAndTimeString) {
		this.dateAndTimeString = dateAndTimeString;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}
	
}
