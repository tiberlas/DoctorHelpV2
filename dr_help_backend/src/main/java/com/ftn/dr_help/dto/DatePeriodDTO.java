package com.ftn.dr_help.dto;

import java.util.Date;

public class DatePeriodDTO {
	private Date beginDate;
	private Date EndDate;
	
	public DatePeriodDTO() {
		super();
	}
	
	public DatePeriodDTO(Date beginDate, Date endDate) {
		super();
		this.beginDate = beginDate;
		EndDate = endDate;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	
	
}
