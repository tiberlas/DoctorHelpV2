package com.ftn.dr_help.dto;

import java.util.Date;

import com.ftn.dr_help.model.enums.Shift;

public class MedicalStaffSaveingDTO {

	private String firstName;
	private String lastName;
	private String email;
	private Long procedureId;
	private Date birthday; 
	private Shift monday;
	private Shift tuesday;
	private Shift wednesday;
	private Shift thursday;
	private Shift friday;
	private Shift saturday;
	private Shift sunday;
	
	public MedicalStaffSaveingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MedicalStaffSaveingDTO(String firstName, String lastName, String email, Long procedure, Date birthday, Shift monday,
			Shift tuesday, Shift wednesday, Shift thursday, Shift friday, Shift saturday, Shift sunday) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.procedureId = procedure;
		this.birthday = birthday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Long getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(Long procedureId) {
		this.procedureId = procedureId;
	}
	
}
