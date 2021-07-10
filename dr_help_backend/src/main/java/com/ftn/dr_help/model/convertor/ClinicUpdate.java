package com.ftn.dr_help.model.convertor;

import com.ftn.dr_help.dto.ClinicDTO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;

public class ClinicUpdate {

	/*
	 * updates though's clinic's fields that are not empty and are valid  
	 * */
	
	public static void update(ClinicPOJO oldClinic, ClinicDTO newClinic) {
		
		if(!newClinic.getAddress().equals("")) {
			oldClinic.setAddress(newClinic.getAddress());
		}
		
		if(!newClinic.getDescription().equals("")) {
			oldClinic.setDescription(newClinic.getDescription());
		}
		
		if(!newClinic.getName().equals("")) {
			oldClinic.setName(newClinic.getName());
		}
		
	} 
}
