package com.ftn.dr_help.dto;

public class RoomCalendarDTO {
	
	private Long appointmentId;
	private Long operationId;
	private String title;
	private String date;
	private String startTime;
	private String endTime;
	
	public RoomCalendarDTO() {
		super();
	}
	
	public RoomCalendarDTO(Long appointmentId, Long operationId, String title, String date, String time, String duration) {
		super();
		this.appointmentId = appointmentId;
		this.operationId = operationId;
		this.title = title;
		this.date = date;
		this.startTime = time;
		this.endTime = duration;
	}
	
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String time) {
		this.startTime = time;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String duration) {
		this.endTime = duration;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

}
