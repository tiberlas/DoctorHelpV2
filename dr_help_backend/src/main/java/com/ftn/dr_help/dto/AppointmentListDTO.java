package com.ftn.dr_help.dto;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListDTO {

	public AppointmentListDTO(List<PatientHistoryDTO> appointmentList, List<String> possibleDates) {
		super();
		this.appointmentList = appointmentList;
		this.possibleDates = possibleDates;
	}
	public AppointmentListDTO() {
		super();
		appointmentList = new ArrayList<PatientHistoryDTO>();
		possibleDates = new ArrayList<String> ();
		possibleDoctors = new ArrayList<String>();
		possibleClinics = new ArrayList<String>();
		possibleTypes = new ArrayList<String>();
		possibleStatuses = new ArrayList<String>();
	}
	
	
	private List<PatientHistoryDTO> appointmentList;
	private List<String> possibleDates;
	private List<String> possibleDoctors;
	private List<String> possibleClinics;
	private List<String> possibleTypes;
	private List<String> possibleStatuses; 
	
	public List<PatientHistoryDTO> getAppointmentList() {
		return appointmentList;
	}
	public void setAppointmentList(List<PatientHistoryDTO> appointmentList) {
		this.appointmentList = appointmentList;
	}
	public List<String> getPossibleDates() {
		return possibleDates;
	}
	public void setPossibleDates(List<String> possibleDates) {
		this.possibleDates = possibleDates;
	}
	public List<String> getPossibleDoctors() {
		return possibleDoctors;
	}
	public void setPossibleDoctors(List<String> possibleDoctors) {
		this.possibleDoctors = possibleDoctors;
	}
	public List<String> getPossibleClinics() {
		return possibleClinics;
	}
	public void setPossibleClinics(List<String> possibleClinics) {
		this.possibleClinics = possibleClinics;
	}
	public List<String> getPossibleTypes() {
		return possibleTypes;
	}
	public void setPossibleTypes(List<String> possibleTypes) {
		this.possibleTypes = possibleTypes;
	}
	public List<String> getPossibleStatuses() {
		return possibleStatuses;
	}
	public void setPossibleStatuses(List<String> possibleStatuses) {
		this.possibleStatuses = possibleStatuses;
	}
	
	
}
