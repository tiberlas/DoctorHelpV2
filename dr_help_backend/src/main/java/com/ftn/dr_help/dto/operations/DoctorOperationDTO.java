package com.ftn.dr_help.dto.operations;

import java.util.Date;

public class DoctorOperationDTO {

	//za operaciju
	private String firstDoctor;
	private String secondDoctor;
	private String thirdDoctor;
	//
	
	private Date startDate;
	private Date endDate;
	
	private String roomName;
	private String roomNumber;
	
	private String procedureName;
	private String patientFirstName;
	private String patientLastName;
	
	private String price;
	private String discount;
	
	private String status;
	private String insuranceNumber;
	
	public DoctorOperationDTO() {
		
	}
	
	public String getFirstDoctor() {
		return firstDoctor;
	}
	public void setFirstDoctor(String firstDoctor) {
		this.firstDoctor = firstDoctor;
	}
	public String getSecondDoctor() {
		return secondDoctor;
	}
	public void setSecondDoctor(String secondDoctor) {
		this.secondDoctor = secondDoctor;
	}
	public String getThirdDoctor() {
		return thirdDoctor;
	}
	public void setThirdDoctor(String thirdDoctor) {
		this.thirdDoctor = thirdDoctor;
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
