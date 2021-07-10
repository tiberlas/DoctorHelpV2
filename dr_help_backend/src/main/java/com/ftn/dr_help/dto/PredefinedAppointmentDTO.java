package com.ftn.dr_help.dto;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;

public class PredefinedAppointmentDTO {

	DateConverter dateConverter = new DateConverter ();
	
	private Long id;
	private String dateAndTime;
	private Long procedureTypeId;
	private Long roomId;
	private Long nurseId;
	private Long doctorId;
	private double price;
	private double disscount;
	private Long clinicId;
	
	
	public PredefinedAppointmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PredefinedAppointmentDTO(Long id, String dateAndTime, Long proceduretypeId, Long roomId, Long nurseId, Long doctorId, double price,
			double disscount, Long clinicId) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.procedureTypeId = proceduretypeId;
		this.roomId = roomId;
		this.nurseId = nurseId;
		this.doctorId = doctorId;
		this.price = price;
		this.disscount = disscount;
		this.clinicId = clinicId;
	}
	
	public PredefinedAppointmentDTO(AppointmentPOJO pojo) {
		super();
		this.id = pojo.getId();
		this.dateAndTime = dateConverter.dateForFrontEndString(pojo.getDate());
		this.procedureTypeId = pojo.getProcedureType().getId();
		this.roomId = pojo.getRoom().getId();
		this.doctorId = pojo.getDoctor().getId();
		this.price = pojo.getProcedureType().getPrice();
		this.disscount = pojo.getDiscount();
		this.clinicId = pojo.getRoom().getClinic().getId();
		this.nurseId = pojo.getNurse().getId();
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long proceduretypeId) {
		this.procedureTypeId = proceduretypeId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDisscount() {
		return disscount;
	}

	public void setDisscount(double disscount) {
		this.disscount = disscount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}
	
}
