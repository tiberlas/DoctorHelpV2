package com.ftn.dr_help.dto.nurse;

import java.util.Date;

public class NurseAppointmentDTO {

	
	private Long appointment_id;

	private String patientFirstName;
	private String patientLastName;
	
	private Long doctor_id;
	private String doctorFirstName;
	private String doctorLastName;
	
	private Date startDate;
	private Date endDate;
	
	private String roomName;
	private String roomNumber;
	
	private String procedureName;
	private String price;
	private String discount;
	
	private String status;
	
	
	private String insuranceNumber;
	
	
	public NurseAppointmentDTO() {
		
	}
	
	


	public NurseAppointmentDTO(Long appointment_id, String patientFirstName,
			String patientLastName, Long doctor_id, String doctorFirstName,
			String doctorLastName, Date startDate, Date endDate,
			String roomName, String roomNumber, String procedureName,
			String price, String discount, String status, String insuranceNumber) {
		super();
		this.appointment_id = appointment_id;
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.doctor_id = doctor_id;
		this.doctorFirstName = doctorFirstName;
		this.doctorLastName = doctorLastName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomName = roomName;
		this.roomNumber = roomNumber;
		this.procedureName = procedureName;
		this.price = price;
		this.discount = discount;
		this.status = status;
		this.insuranceNumber = insuranceNumber;
	}




	public Long getAppointment_id() {
		return appointment_id;
	}


	public void setAppointment_id(Long appointment_id) {
		this.appointment_id = appointment_id;
	}


	public String getPatientFirstName() {
		return patientFirstName;
	}


	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}


	public String getPatientLastName() {
		return patientLastName;
	}


	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}


	public Long getDoctor_id() {
		return doctor_id;
	}


	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}


	public String getDoctorFirstName() {
		return doctorFirstName;
	}


	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}


	public String getDoctorLastName() {
		return doctorLastName;
	}


	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}


	public String getProcedureName() {
		return procedureName;
	}


	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInsuranceNumber() {
		return insuranceNumber;
	}


	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
}
