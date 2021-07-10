package com.ftn.dr_help.dto.leave_requests;

import java.util.Date;

import com.ftn.dr_help.model.enums.LeaveStatusEnum;
import com.ftn.dr_help.model.enums.LeaveTypeEnum;
import com.ftn.dr_help.model.enums.RoleEnum;

public class LeaveRequestDTO {
	
	Long id;

	private LeaveTypeEnum leaveType;
	private LeaveStatusEnum leaveStatus;
	
	private String note;
	private Date startDate;
	private Date endDate;
	
	private Long staffId;
	private RoleEnum staffRole; //DOCTOR ili NURSE
	
	private String firstName;
	private String lastName;
	
	
	
	public LeaveRequestDTO() {
		
	}


	public LeaveTypeEnum getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(LeaveTypeEnum leaveType) {
		this.leaveType = leaveType;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
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


	public Long getStaffId() {
		return staffId;
	}


	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}


	public RoleEnum getStaffRole() {
		return staffRole;
	}


	public void setStaffRole(RoleEnum staffRole) {
		this.staffRole = staffRole;
	}


	public LeaveStatusEnum getLeaveStatus() {
		return leaveStatus;
	}


	public void setLeaveStatus(LeaveStatusEnum leaveStatus) {
		this.leaveStatus = leaveStatus;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
