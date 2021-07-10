package com.ftn.dr_help.dto;

public class ThreeDoctorsIdDTO {

	private Long doctor0;
	private Long doctor1;
	private Long doctor2;
	
	public ThreeDoctorsIdDTO() {
		super();
	}
	
	public ThreeDoctorsIdDTO(Long doctor0, Long doctor1, Long doctor2) {
		super();
		this.doctor0 = doctor0;
		this.doctor1 = doctor1;
		this.doctor2 = doctor2;
	}

	public Long getDoctor0() {
		return doctor0;
	}

	public void setDoctor0(Long doctor0) {
		this.doctor0 = doctor0;
	}

	public Long getDoctor1() {
		return doctor1;
	}

	public void setDoctor1(Long doctor1) {
		this.doctor1 = doctor1;
	}

	public Long getDoctor2() {
		return doctor2;
	}

	public void setDoctor2(Long doctor2) {
		this.doctor2 = doctor2;
	}
	
	
}
