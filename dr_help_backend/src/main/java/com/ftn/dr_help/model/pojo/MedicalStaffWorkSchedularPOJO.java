package com.ftn.dr_help.model.pojo;

import java.util.Calendar;

import com.ftn.dr_help.model.enums.Shift;

public class MedicalStaffWorkSchedularPOJO {

	private Shift monday;
	private Shift tuesday;
	private Shift wednesday;
	private Shift thursday;
	private Shift friday;
	private Shift saturday;
	private Shift sunday;
	private Calendar duration;
	
	public MedicalStaffWorkSchedularPOJO() {
		super();
	}

	public MedicalStaffWorkSchedularPOJO(Shift monday, Shift tuesday, Shift wednesday, Shift thursday, Shift friday,
			Shift saturday, Shift sunday, Calendar duration) {
		super();
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.duration = duration;
	}

	public Shift getMonday() {
		return monday;
	}

	public void setMonday(Shift monday) {
		this.monday = monday;
	}

	public Shift getTuesday() {
		return tuesday;
	}

	public void setTuesday(Shift tuesday) {
		this.tuesday = tuesday;
	}

	public Shift getWednesday() {
		return wednesday;
	}

	public void setWednesday(Shift wednesday) {
		this.wednesday = wednesday;
	}

	public Shift getThursday() {
		return thursday;
	}

	public void setThursday(Shift thursday) {
		this.thursday = thursday;
	}

	public Shift getFriday() {
		return friday;
	}

	public void setFriday(Shift friday) {
		this.friday = friday;
	}

	public Shift getSaturday() {
		return saturday;
	}

	public void setSaturday(Shift saturday) {
		this.saturday = saturday;
	}

	public Shift getSunday() {
		return sunday;
	}

	public void setSunday(Shift sunday) {
		this.sunday = sunday;
	}

	public Calendar getDuration() {
		return duration;
	}

	public void setDuration(Calendar duration) {
		this.duration = duration;
	}
	
	
}	
