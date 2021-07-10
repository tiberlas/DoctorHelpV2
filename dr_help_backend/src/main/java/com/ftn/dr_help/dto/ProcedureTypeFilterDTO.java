package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.FilterOperationEnum;

public class ProcedureTypeFilterDTO {
	
	private String string;
	private FilterOperationEnum operation;
	
	public ProcedureTypeFilterDTO() {
		super();
	}

	public ProcedureTypeFilterDTO(String string, FilterOperationEnum operation) {
		super();
		this.string = string;
		this.operation = operation;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public FilterOperationEnum getOperation() {
		return operation;
	}

	public void setOperation(FilterOperationEnum operation) {
		this.operation = operation;
	}

}
