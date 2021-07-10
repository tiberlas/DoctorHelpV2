package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ftn.dr_help.model.enums.LeaveStatusEnum;
import com.ftn.dr_help.model.enums.LeaveTypeEnum;
import com.ftn.dr_help.model.enums.RoleEnum;

@Entity(name="leaveRequests")
public class LeaveRequestPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "doctor_id", referencedColumnName = "id")
	DoctorPOJO doctor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "nurse_id", referencedColumnName = "id")
	NursePOJO nurse;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "staffRole", nullable = false)
	private RoleEnum staffRole;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "leaveType", nullable = false)
	private LeaveTypeEnum leaveType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "leaveStatus", nullable = false)
	private LeaveStatusEnum leaveStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar firstDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastDay;
	
	@Column(name = "requestNote", nullable = true)
	private String requestNote;
	
	@Version
	@Column(name="version", unique=false, nullable=false)
	private Long version;
	
	
	public LeaveRequestPOJO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleEnum getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(RoleEnum staffRole) {
		this.staffRole = staffRole;
	}

	public Calendar getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Calendar firstDay) {
		this.firstDay = firstDay;
	}

	public Calendar getLastDay() {
		return lastDay;
	}

	public void setLastDay(Calendar lastDay) {
		this.lastDay = lastDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getRequestNote() {
		return requestNote;
	}

	public void setRequestNote(String requestNote) {
		this.requestNote = requestNote;
	}

	public DoctorPOJO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorPOJO doctor) {
		this.doctor = doctor;
	}

	public NursePOJO getNurse() {
		return nurse;
	}

	public void setNurse(NursePOJO nurse) {
		this.nurse = nurse;
	}

	public LeaveTypeEnum getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveTypeEnum leaveType) {
		this.leaveType = leaveType;
	}

	public LeaveStatusEnum getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatusEnum leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	
	
	

}
