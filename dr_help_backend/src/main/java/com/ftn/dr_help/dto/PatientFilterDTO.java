package com.ftn.dr_help.dto;


public class PatientFilterDTO {

	private String filterResults;
	
	public PatientFilterDTO() {
		
	}
	
	
	public PatientFilterDTO(String filter) {
		this.filterResults = filter;
	}


	public String getFilterResults() {
		return filterResults;
	}


	public void setFilterResults(String filterResults) {
		this.filterResults = filterResults;
	}
	
	
}
