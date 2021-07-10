package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.BloodTypeEnum;

public class HealthRecordDTO {

	public HealthRecordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HealthRecordDTO(String firstName, String lastName, double weight, double height, String birthday,
			double diopter, BloodTypeEnum bloodType, String allergyList) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.weight = weight;
		this.height = height;
		this.birthday = birthday;
		this.diopter = diopter;
		this.bloodType = bloodType;
		this.allergyList = allergyList;
	}
	
	private String firstName;
	private String lastName;
	private double weight;
	private double height;
	private String birthday;
	private double diopter;
	private BloodTypeEnum bloodType;
	private String allergyList;
	
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public double getDiopter() {
		return diopter;
	}
	public void setDiopter(double diopter) {
		this.diopter = diopter;
	}
	public BloodTypeEnum getBloodType() {
		return bloodType;
	}
	public void setBloodType(BloodTypeEnum bloodType) {
		this.bloodType = bloodType;
	}
	public String getAllergyList() {
		return allergyList;
	}
	public void setAllergyList(String allergyList) {
		this.allergyList = allergyList;
	}
	
	
	
	
}
