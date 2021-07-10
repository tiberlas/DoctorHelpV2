package com.ftn.dr_help.dto;

import java.util.Calendar;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;

public class ClinicAdminProfileDTO {

	DateConverter dateConverter = new DateConverter ();
	
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String phoneNumber;
	private String birthday;
	private Long clinicId;
	
	public ClinicAdminProfileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicAdminProfileDTO(Long id, String email, String firstName, String lastName, String address, String city,
			String state, String phoneNumber, Calendar birthday, Long clinicId) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.birthday = dateConverter.toString(birthday);
		this.clinicId = clinicId;
	}

	public ClinicAdminProfileDTO(ClinicAdministratorPOJO admin) {
		this.id = admin.getId();
		this.email = admin.getEmail();
		this.firstName = admin.getFirstName();
		this.lastName = admin.getLastName();
		this.address = admin.getAddress();
		this.city = admin.getCity();
		this.state = admin.getState();
		this.phoneNumber = admin.getPhoneNumber();
		this.birthday = dateConverter.toString (admin.getBirthday());
		this.clinicId = admin.getClinic().getId();
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
	public String getBirthday() {
		System.out.println("Siso, klinicki admin birthday je: " + birthday);
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = dateConverter.toString(birthday);
	}
	public Long getClinicId() {
		return clinicId;
	}
	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}
	
	
}
