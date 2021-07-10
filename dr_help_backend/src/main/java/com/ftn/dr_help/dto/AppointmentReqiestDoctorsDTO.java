package com.ftn.dr_help.dto;

import java.util.ArrayList;
import java.util.List;

public class AppointmentReqiestDoctorsDTO {

	

	List<DoctorListingDTO> doctorListing;
	String clinicName;
	String address; 
	
	
	public AppointmentReqiestDoctorsDTO() {
		super();
		doctorListing = new ArrayList<DoctorListingDTO> ();
	}
	
	public AppointmentReqiestDoctorsDTO(List<DoctorListingDTO> doctorListing, String clinicName, String address) {
		super();
		this.doctorListing = doctorListing;
		this.clinicName = clinicName;
		this.address = address;
	}
	
	public List<DoctorListingDTO> getDoctorListing() {
		return doctorListing;
	}
	public void setDoctorListing(List<DoctorListingDTO> doctorListing) {
		this.doctorListing = doctorListing;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
