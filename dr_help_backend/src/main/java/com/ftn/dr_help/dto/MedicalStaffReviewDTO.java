package com.ftn.dr_help.dto;

public class MedicalStaffReviewDTO {

	private Long medicalStaffId;
	private Float review;
	private String fullName;
	
	public MedicalStaffReviewDTO() {
		super();
	}
	
	public MedicalStaffReviewDTO(Long medicalStaffId, Float review, String fullName) {
		super();
		this.medicalStaffId = medicalStaffId;
		this.review = review;
		this.fullName = fullName;
	}
	
	public Long getMedicalStaffId() {
		return medicalStaffId;
	}
	public void setMedicalStaffId(Long medicalStaffId) {
		this.medicalStaffId = medicalStaffId;
	}
	public Float getReview() {
		return review;
	}
	public void setReview(Float review) {
		this.review = review;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
