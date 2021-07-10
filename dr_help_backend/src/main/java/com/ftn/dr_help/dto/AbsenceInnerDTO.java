package com.ftn.dr_help.dto;

import java.util.Date;

public class AbsenceInnerDTO {
	
	private Date beginning;
	private Date ending;
	
	public AbsenceInnerDTO() {
		super();
	}
	
	public AbsenceInnerDTO(Date beginning, Date ending) {
		super();
		this.beginning = beginning;
		this.ending = ending;
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnding() {
		return ending;
	}

	public void setEnding(Date ending) {
		this.ending = ending;
	}
	
}
