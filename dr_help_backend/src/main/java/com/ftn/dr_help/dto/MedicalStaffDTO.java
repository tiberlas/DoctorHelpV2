package com.ftn.dr_help.dto;

import java.util.Calendar;

import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;

public class MedicalStaffDTO {

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String phoneNumber;
	private Calendar birthday;
	private RoleEnum role;
	
	public MedicalStaffDTO(NursePOJO nurse) {
		super();
		this.id = nurse.getId();
		this.email = nurse.getEmail();
		this.firstName = nurse.getFirstName();
		this.lastName = nurse.getLastName();
		this.address = nurse.getAddress();
		this.city = nurse.getCity();
		this.state = nurse.getState();
		this.phoneNumber = nurse.getPhoneNumber();
		this.birthday = nurse.getBirthday();
		this.role = RoleEnum.NURSE;
	}
	
	public MedicalStaffDTO(DoctorPOJO doctor) {
		super();
		this.id = doctor.getId();
		this.email = doctor.getEmail();
		this.firstName = doctor.getFirstName();
		this.lastName = doctor.getLastName();
		this.address = doctor.getAddress();
		this.city = doctor.getCity();
		this.state = doctor.getState();
		this.phoneNumber = doctor.getPhoneNumber();
		this.birthday = doctor.getBirthday();
		this.role = RoleEnum.DOCTOR;
	}
	
	public MedicalStaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MedicalStaffDTO(Long id, String email, String firstName, String lastName, String address, String city,
			String state, String phoneNumber, Calendar birthday, RoleEnum role) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	
	
	
}
