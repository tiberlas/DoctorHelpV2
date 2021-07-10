package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.AppointmentBlessing;

public class AppointmentInternalBlessedDTO {

	private AppointmentBlessing blessingLvl;
	private String message;
	
	public AppointmentInternalBlessedDTO() {
		super();
	}
	
	public AppointmentInternalBlessedDTO(AppointmentBlessing blessingLvl, String message) {
		super();
		this.blessingLvl = blessingLvl;
		this.message = message;
	}

	public AppointmentBlessing getBlessingLvl() {
		return blessingLvl;
	}

	public void setBlessingLvl(AppointmentBlessing blessingLvl) {
		this.blessingLvl = blessingLvl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
