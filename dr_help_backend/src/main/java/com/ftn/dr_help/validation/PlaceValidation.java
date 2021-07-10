package com.ftn.dr_help.validation;

public class PlaceValidation {

	/*
	 * Validation for Address, City, State
	 * */
	
	boolean isValid(String place) {
		if(place != null) {
			String trimPlace = place.trim();
			if(trimPlace.length() > 0 && trimPlace.length() < 102) {
				return true;
			}
		}
		
		return false;
	}
}
