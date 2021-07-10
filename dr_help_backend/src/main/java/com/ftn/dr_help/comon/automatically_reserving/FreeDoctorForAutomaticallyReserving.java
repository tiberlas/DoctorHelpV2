package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;

import com.ftn.dr_help.model.pojo.DoctorPOJO;

public class FreeDoctorForAutomaticallyReserving {

	private Calendar recomendedDate;
	private DoctorPOJO doctor;
	
	
	//recycling for operations
	private DoctorPOJO doctor1;
	private DoctorPOJO doctor2;
	private DoctorPOJO doctor3;
	
	public FreeDoctorForAutomaticallyReserving() {
		super();
	}
	
	public FreeDoctorForAutomaticallyReserving(Calendar recomendedDate, DoctorPOJO doctor) {
		super();
		this.recomendedDate = recomendedDate;
		this.doctor = doctor;
	}
	
	public FreeDoctorForAutomaticallyReserving(Calendar recommendedDate, DoctorPOJO doctor1, DoctorPOJO doctor2, DoctorPOJO doctor3) {
		super();
		this.recomendedDate = recommendedDate;
		this.doctor1 = doctor1;
		this.doctor2 = doctor2;
		this.doctor3 =doctor3;
	}

	public Calendar getRecomendedDate() {
		return recomendedDate;
	}

	public void setRecomendedDate(Calendar recomendedDate) {
		this.recomendedDate = recomendedDate;
	}

	public DoctorPOJO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorPOJO doctor) {
		this.doctor = doctor;
	}

	public DoctorPOJO getDoctor1() {
		return doctor1;
	}

	public void setDoctor1(DoctorPOJO doctor1) {
		this.doctor1 = doctor1;
	}

	public DoctorPOJO getDoctor2() {
		return doctor2;
	}

	public void setDoctor2(DoctorPOJO doctor2) {
		this.doctor2 = doctor2;
	}

	public DoctorPOJO getDoctor3() {
		return doctor3;
	}

	public void setDoctor3(DoctorPOJO doctor3) {
		this.doctor3 = doctor3;
	}
	
	
}
