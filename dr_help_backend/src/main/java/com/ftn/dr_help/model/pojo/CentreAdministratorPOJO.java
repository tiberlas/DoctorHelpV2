package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ftn.dr_help.model.enums.RoleEnum;

@Entity
@Table(name="CentreAdministrator")
public class CentreAdministratorPOJO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "address", nullable = true)
	private String address;

	@Column(name = "city", nullable = true)
	private String city;
	
	@Column(name = "state", nullable = true)
	private String state;
	
	@Column(name = "phoneNumber", nullable = true)
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private RoleEnum role = RoleEnum.CENTRE_ADMINISTRATOR;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday", nullable = true)
	private Calendar birthday;
	
	@Column(name = "mustChangePassword", nullable = true)
	private Boolean mustChangePassword = false;
	
	public CentreAdministratorPOJO() {
		
	}
	
	public RoleEnum getRole() {
		return role;
	}
	
	
	public CentreAdministratorPOJO( String password, String email,
			 String firstName,  String lastName,  String address,  String city,
			 String state,  String phoneNumber,  RoleEnum role,
			 Calendar birthday) {
		super();
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.birthday = birthday;
	}
	

	@Override
	public String toString() {
		return "CentreAdministratorPOJO [id=" + id + ", password=" + password + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", phoneNumber=" + phoneNumber + ", role=" + role + ", birthday=" + birthday + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public Boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

	
}
