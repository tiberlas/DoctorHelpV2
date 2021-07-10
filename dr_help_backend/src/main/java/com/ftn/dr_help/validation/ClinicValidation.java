package com.ftn.dr_help.validation;

import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.ClinicDTO;

@Service
public class ClinicValidation {
	
	/*
	 * checks if clinic is valid
	 * a field must not be null or have only white spaces
	 * the address must start with a number
	 * */

	public boolean isValid(ClinicDTO clinic) {
		
		if(!validField(clinic.getAddress())) {
			return false;
		}
		
		if(startsWithNumber(clinic.getAddress())) {
			return false;
		}
		
		if(!validField(clinic.getCity())) {
			return false;
		}
		
		if(!validField(clinic.getState())) {
			return false;
		}
		
		if(!validField(clinic.getName())) {
			return false;
		}
		
		if(!validField(clinic.getDescription())) {
			return false;
		}
		
		if(clinic.getId() == null) {
			return false;
		}
		
		
		return true;
	}
	
	private boolean validField(String address) {
		if(address != null) {
			String trimName = address.trim();
			if(trimName.length() > 0) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	private boolean startsWithNumber(String address) {
		if(address.startsWith("0") || 
				address.startsWith("1") || 
				address.startsWith("2") || 
				address.startsWith("3") || 
				address.startsWith("4") || 
				address.startsWith("5") || 
				address.startsWith("6") || 
				address.startsWith("7") || 
				address.startsWith("8") || 
				address.startsWith("9") ) {
			return true;
		}
		
		return false;
	}
}
