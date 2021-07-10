package com.ftn.dr_help.dto;

import java.util.ArrayList;
import java.util.List;

public class ClinicListingDTO {

	

	public ClinicListingDTO(List<ClinicPreviewDTO> clinicList, List<String> procedureType, String procedureTypeString,
			String dateString) {
		super();
		this.clinicList = clinicList;
		this.procedureType = procedureType;
		this.procedureTypeString = procedureTypeString;
		this.dateString = dateString;
	}


	private String procedureTypeString;
	private String dateString;
	private String stateString;
	private String cityString;
	private String addressString;
	private List<ClinicPreviewDTO> clinicList;
	private List<String> procedureType;
	private List<String> stateList;
	private List<String> cityList;
	private List<String> addressList;
	
	public ClinicListingDTO () {
		clinicList = new ArrayList<ClinicPreviewDTO> ();
		procedureType = new ArrayList<String> ();
	}
	
	
	public List<ClinicPreviewDTO> getClinicList() {
		return clinicList;
	}

	public void setClinicList(List<ClinicPreviewDTO> clinicList) {
		this.clinicList = clinicList;
	}

	public List<String> getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(List<String> procedureType) {
		this.procedureType = procedureType;
	}


	public String getProcedureTypeString() {
		return procedureTypeString;
	}


	public void setProcedureTypeString(String procedureTypeString) {
		this.procedureTypeString = procedureTypeString;
	}


	public String getDateString() {
		return dateString;
	}


	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	public List<String> getStateList() {
		return stateList;
	}


	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}


	public String getStateString() {
		return stateString;
	}


	public void setStateString(String stateString) {
		this.stateString = stateString;
	}


	public String getCityString() {
		return cityString;
	}


	public void setCityString(String cityString) {
		this.cityString = cityString;
	}


	public List<String> getCityList() {
		return cityList;
	}


	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}


	public List<String> getAddressList() {
		return addressList;
	}


	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}


	public String getAddressString() {
		return addressString;
	}


	public void setAddressString(String addressString) {
		this.addressString = addressString;
	}
	
	
	
}
