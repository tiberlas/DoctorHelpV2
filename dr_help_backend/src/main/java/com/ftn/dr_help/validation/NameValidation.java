package com.ftn.dr_help.validation;

public class NameValidation {

	/*
	 * validation for FirstName and LastName
	 * */
	
	public boolean isValid(String name) {
		if(name != null) {
			String trimName = name.trim();
			if(trimName.length() > 0 && trimName.length() < 31) {
				return true;
			}
		}
		
		return false;
	}
}
