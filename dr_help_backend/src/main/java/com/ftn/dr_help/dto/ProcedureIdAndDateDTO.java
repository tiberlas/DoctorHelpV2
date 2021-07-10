package com.ftn.dr_help.dto;

public class ProcedureIdAndDateDTO {

	private Long typeId;
	private String date;
	
	public ProcedureIdAndDateDTO() {
		super();
	}
	
	public ProcedureIdAndDateDTO(Long typeId, String date) {
		super();
		this.typeId = typeId;
		this.date = date;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
