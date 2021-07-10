package com.ftn.dr_help.dto;

import java.util.Date;

public class HeldAppointmentsChartDataDTO {

	private Date x;
	private int y;
	
	public HeldAppointmentsChartDataDTO() {
		super();
	}
	
	public HeldAppointmentsChartDataDTO(Date x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Date getX() {
		return x;
	}
	public void setX(Date x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
