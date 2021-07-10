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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.enums.Shift;

@Entity
@Table(name = "nurse")
public class NursePOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private RoleEnum role = RoleEnum.NURSE;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	private Calendar birthday;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private ClinicPOJO clinic;
	
	@OneToMany (mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<AppointmentPOJO> operationList;
	
	@OneToMany (mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<AppointmentPOJO> appointments;
	
	@OneToMany(mappedBy = "signingNurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<PerscriptionPOJO> perscription;
	
	@Column (name = "deleted", nullable = false)
	private boolean deleted;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "monday", nullable = false)
	public Shift monday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "tuesday", nullable = false)
	public Shift tuesday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "wednesday", nullable = false)
	public Shift wednesday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "thursday", nullable = false)
	public Shift thursday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "friday", nullable = false)
	public Shift friday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "saturday", nullable = false)
	public Shift saturday;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "sunday", nullable = false)
	public Shift sunday;
	
	@Column(name = "mustChangePassword", nullable = true)
	private Boolean mustChangePassword = false;
	
	@OneToMany(mappedBy="nurse",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LeaveRequestPOJO> leaveRequest;
	
	public NursePOJO() {
		super();
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
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

	public ClinicPOJO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicPOJO clinic) {
		this.clinic = clinic;
	}

	public List<AppointmentPOJO> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<AppointmentPOJO> operationList) {
		this.operationList = operationList;
	}

	public List<AppointmentPOJO> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<AppointmentPOJO> appointments) {
		this.appointments = appointments;
	}

	public List<PerscriptionPOJO> getPerscription() {
		return perscription;
	}

	public void setPerscription(List<PerscriptionPOJO> perscription) {
		this.perscription = perscription;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}
}
