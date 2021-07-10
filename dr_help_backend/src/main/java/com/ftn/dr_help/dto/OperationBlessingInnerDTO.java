package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.OperationBlessing;

public class OperationBlessingInnerDTO {

	private String recomendedDate;
	private OperationBlessing blessedLvl;
	
	public OperationBlessingInnerDTO() {
		super();
	}
	
	public OperationBlessingInnerDTO(String recomendedDate, OperationBlessing blessedLvl) {
		super();
		this.recomendedDate = recomendedDate;
		this.blessedLvl = blessedLvl;
	}

	public String getRecomendedDate() {
		return recomendedDate;
	}

	public void setRecomendedDate(String recomendedDate) {
		this.recomendedDate = recomendedDate;
	}

	public OperationBlessing getBlessedLvl() {
		return blessedLvl;
	}

	public void setBlessedLvl(OperationBlessing blessedLvl) {
		this.blessedLvl = blessedLvl;
	}
	
}
