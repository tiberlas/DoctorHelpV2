package com.ftn.dr_help.validation;

import java.util.Calendar;

public class DateValidation {

	/*
	 * Validate birthday 
	 * */
	public boolean isValid(Calendar date) {
		if(date != null && date.before(Calendar.getInstance())) {
			return true;
		}
		
		return false;
	}
}
