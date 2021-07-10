package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ftn.dr_help.model.enums.RoleEnum;

@Entity
@Table (name = "patiens")
public class PatientPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name = "password", nullable = false)
	private String password;
	
	@Column (name = "email", nullable = false, unique = true)
	private String email;
	
	@Column (name = "firstName", nullable = false)
	private String firstName;
	
	@Column (name = "lastName", nullable = false)
	private String lastName;
	
	@Column (name = "address", nullable = false)
	private String address;
	
	@Column (name = "city", nullable = false)
	private String city;
	
	@Column (name = "state", nullable = false)
	private String state;
	
	@Column (name = "phoneNumber", nullable = false)
	private String phoneNumber;
	
	@Column (name = "isActivated", nullable = false)
	private boolean isActivated;
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private RoleEnum role = RoleEnum.PATIENT;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "birthday", nullable = false)
	private Calendar birthday;
	
	@Column (name = "insuranceNumber", nullable = false, unique = true)
	private Long insuranceNumber;
	
	
	@OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private HealthRecordPOJO healthRecord;

	@OneToMany (mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OperationPOJO> operationList;
	
	@OneToMany (mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AppointmentPOJO> appointments;

	
	public PatientPOJO() {
		super();
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public HealthRecordPOJO getHealthRecord() {
		return healthRecord;
	}
	public void setHealthRecord(HealthRecordPOJO healthRecord) {
		this.healthRecord = healthRecord;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setInsuranceNumber(Long insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public List<OperationPOJO> getOperationList() {
		return operationList;
	}
	public void setOperationList(List<OperationPOJO> operationList) {
		this.operationList = operationList;
	}
	public List<AppointmentPOJO> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<AppointmentPOJO> appointments) {
		this.appointments = appointments;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	public Long getInsuranceNumber() {
		return insuranceNumber;
	}
	@Override
	public String toString() {
		return "PatientPOJO [password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", city=" + city + ", state="
				+ state + ", phoneNumber=" + phoneNumber + ", isActivated="
				+ isActivated + ", id=" + id + ", role=" + role + ", birthday="
				+ birthday + ", insuranceNumber=" + insuranceNumber
				+ ", healthRecord=" + healthRecord + ", operationList="
				+ operationList + ", appointments=" + appointments + "]";
	}
	
}
