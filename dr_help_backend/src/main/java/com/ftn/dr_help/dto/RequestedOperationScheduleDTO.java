package com.ftn.dr_help.dto;

public class RequestedOperationScheduleDTO {

	private Long id;
	private String procedureName;
	private String patientName;
	private String date;
	private String dr0Name;
	private String dr1Name;
	private String dr2Name;
	private String status;
	
	public RequestedOperationScheduleDTO() {
		super();
	}
	
	public RequestedOperationScheduleDTO(Long id,String procedureName, String patientName, String date, String dr0Name,
			String dr1Name, String dr2Name, String status) {
		super();
		this.id = id;
		this.procedureName = procedureName;
		this.patientName = patientName;
		this.date = date;
		this.dr0Name = dr0Name;
		this.dr1Name = dr1Name;
		this.dr2Name = dr2Name;
		this.status = status;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDr0Name() {
		return dr0Name;
	}

	public void setDr0Name(String dr0Name) {
		this.dr0Name = dr0Name;
	}

	public String getDr1Name() {
		return dr1Name;
	}

	public void setDr1Name(String dr1Name) {
		this.dr1Name = dr1Name;
	}

	public String getDr2Name() {
		return dr2Name;
	}

	public void setDr2Name(String dr2Name) {
		this.dr2Name = dr2Name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
