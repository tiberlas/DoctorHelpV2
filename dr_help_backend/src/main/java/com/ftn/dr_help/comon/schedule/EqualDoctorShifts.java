package com.ftn.dr_help.comon.schedule;

import com.ftn.dr_help.model.enums.DayEnum;
import com.ftn.dr_help.model.enums.Shift;

public class EqualDoctorShifts {
	/*
	 * Struktura podataka koja cuva dan i radnu smenu;
	 * koristi se prilikom zakazivanja za operaciju u klasi checkShift
	 * */
	
	private DayEnum day;
	private Shift shift; //razlicito od NONE
	
	public EqualDoctorShifts() {
		super();
		this.day = DayEnum.MONDAY;
		this.shift = Shift.NONE;
	}
	
	public EqualDoctorShifts(DayEnum day, Shift shift) {
		super();
		this.day = day;
		this.shift = shift;
	}

	public DayEnum getDay() {
		return day;
	}

	public void setDay(DayEnum day) {
		this.day = day;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

}
