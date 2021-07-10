package com.ftn.dr_help.dto;

import java.util.Calendar;

public class NurseCheckIfAvailableInnerDTO {

	private boolean isFree;
	private Calendar firstFree;
	
	public NurseCheckIfAvailableInnerDTO() {
		super();
	}
	
	public NurseCheckIfAvailableInnerDTO(boolean isFree, Calendar firstFree) {
		super();
		this.isFree = isFree;
		this.firstFree = firstFree;
	}
	
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public Calendar getFirstFree() {
		return firstFree;
	}
	public void setFirstFree(Calendar firstFree) {
		this.firstFree = firstFree;
	}
	
	
}
