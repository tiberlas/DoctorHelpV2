package com.ftn.dr_help.validation;

import com.ftn.dr_help.dto.UserDetailDTO;

public class ProfileValidation implements ProfileValidationInterface{

	private NameValidation name;
	private DateValidation date;
	private PlaceValidation place;
	private PhoneNumberValidation phone;
	
	public ProfileValidation() {
		super();
		this.name = new NameValidation();
		this.date = new DateValidation();
		this.place = new PlaceValidation();
		this.phone = new PhoneNumberValidation();
	}

	@Override
	public boolean validUser(UserDetailDTO user) {
		
		if(!name.isValid(user.getFirstName()) && user.getFirstName()!="") {
			return false;
		}
		
		if(!name.isValid(user.getLastName()) && user.getLastName()!="") {
			return false;
		}
		
		if(!place.isValid(user.getAddress()) && user.getAddress()!="") {
			return false;
		}
		
		if(!place.isValid(user.getCity()) && user.getCity()!="") {
			return false;
		}
		
		if(!place.isValid(user.getState()) && user.getState()!="") {
			return false;
		}
		
		if(!date.isValid(user.getBirthday()) && user.getBirthday()!=null) {
			return false;
		}
		
		if(!phone.isValid(user.getPhoneNumber()) && user.getPhoneNumber()!="") {
			return false;
		}

		
		return true;
	}



}
