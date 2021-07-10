package com.ftn.dr_help.validation;

import com.ftn.dr_help.dto.ChangePasswordDTO;

public interface PasswordValidateInterface {

	boolean isValid(ChangePasswordDTO newPassword, String oldPaString);
}
