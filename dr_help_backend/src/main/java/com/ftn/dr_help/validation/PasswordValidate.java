package com.ftn.dr_help.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.dto.ChangePasswordDTO;

@Service
public class PasswordValidate implements PasswordValidateInterface{
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Override
	public boolean isValid(ChangePasswordDTO newPassword, String encodedPaString) {
		System.out.println("password: " + newPassword.getNewPassword() + newPassword.getOldPassword() + encodedPaString);
		
		if(newPassword == null || newPassword.getNewPassword() == null || newPassword.getOldPassword() == null) {
			return false;
		}
		
		if(newPassword.getOldPassword().trim() == "" || newPassword.getNewPassword().trim() == "") {
			return false;
		}
		
		if(encodedPaString == null)
			return false;
		
		System.out.println(encoder.getClass());
		System.out.println(encoder);
		System.out.println(encoder.getEncoder());

		
		if(encoder.getEncoder().matches(newPassword.getOldPassword(), encodedPaString)) {
			return true;
		}
		
		return false;
	}

}
