package com.ftn.dr_help.dto;

public class OperationRequestInfoDTO {

	private Long operationId;
	private String date;
	private String procedureName;
	private Long procedureId;
	private String patient;
	private String procedureDuration;
	
	public OperationRequestInfoDTO() {
		super();
	}
	
	public OperationRequestInfoDTO(Long operationId, String date, String procedureName, Long procedureId, String patient, String procedureDuration) {
		super();
		this.operationId = operationId;
		this.date = date;
		this.procedureName = procedureName;
		this.procedureId = procedureId;
		this.patient = patient;
		this.procedureDuration = procedureDuration;
	}

	public String getProcedureDuration() {
		return procedureDuration;
	}

	public void setProcedureDuration(String procedureDuration) {
		this.procedureDuration = procedureDuration;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public Long getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(Long procedureId) {
		this.procedureId = procedureId;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}
}
