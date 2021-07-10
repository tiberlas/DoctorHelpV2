package com.ftn.dr_help.validation;

public class PhoneNumberValidation {

	public boolean isValid(String phonenumber) {
		if(phonenumber != null) {
			String trimPhone = phonenumber.trim();
			if(trimPhone.length() > 0) {
				return isNumbers(trimPhone);
			}
		}
		
		return false;
	}
	
	private boolean isNumbers(String toCheck) {
		for(int i=0; i<toCheck.length(); ++i) {
			if(toCheck.charAt(i) < '0' || toCheck.charAt(i) > '9') {
				return false;
			}
		}
		
		return true;
	}
}
