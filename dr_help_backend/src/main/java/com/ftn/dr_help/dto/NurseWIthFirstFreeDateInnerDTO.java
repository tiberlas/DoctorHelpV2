package com.ftn.dr_help.dto;

import java.util.Calendar;

import com.ftn.dr_help.model.pojo.NursePOJO;

public class NurseWIthFirstFreeDateInnerDTO {

	private Calendar firstFreeDate;
	private NursePOJO nurse;
	
	public NurseWIthFirstFreeDateInnerDTO() {
		super();
	}
	
	public NurseWIthFirstFreeDateInnerDTO(Calendar firstFreeDate, NursePOJO nurse) {
		super();
		this.firstFreeDate = firstFreeDate;
		this.nurse = nurse;
	}

	public Calendar getFirstFreeDate() {
		return firstFreeDate;
	}

	public void setFirstFreeDate(Calendar firstFreeDate) {
		this.firstFreeDate = firstFreeDate;
	}

	public NursePOJO getNurse() {
		return nurse;
	}

	public void setNurse(NursePOJO nurse) {
		this.nurse = nurse;
	}
}
