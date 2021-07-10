package com.ftn.dr_help.model.convertor;

import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserPOJO;

public class EncapsulateUserDetails implements EncapsulateUserDetailsInterface {

	@Override
	public UserPOJO getUser(CentreAdministratorPOJO pojo) {
		return new UserPOJO(pojo.getId(),
							pojo.getFirstName(),
							pojo.getLastName(),
							pojo.getEmail(),
							pojo.getPassword(),
							pojo.getAddress(),
							pojo.getCity(),
							pojo.getState(),
							pojo.getPhoneNumber(),
							pojo.getBirthday(),
							pojo.getRole(),
							pojo.getMustChangePassword(),
							true);
							
	}

	@Override
	public UserPOJO getUser(ClinicAdministratorPOJO pojo) {
		return new UserPOJO(pojo.getId(),
				pojo.getFirstName(),
				pojo.getLastName(),
				pojo.getEmail(),
				pojo.getPassword(),
				pojo.getAddress(),
				pojo.getCity(),
				pojo.getState(),
				pojo.getPhoneNumber(),
				pojo.getBirthday(),
				pojo.getRole(),
				pojo.getMustChangePassword(),
				true);
	}

	@Override
	public UserPOJO getUser(DoctorPOJO pojo) {
		return new UserPOJO(pojo.getId(),
				pojo.getFirstName(),
				pojo.getLastName(),
				pojo.getEmail(),
				pojo.getPassword(),
				pojo.getAddress(),
				pojo.getCity(),
				pojo.getState(),
				pojo.getPhoneNumber(),
				pojo.getBirthday(),
				pojo.getRole(),
				pojo.getMustChangePassword(),
				true);				
	}

	@Override
	public UserPOJO getUser(NursePOJO pojo) {
		return new UserPOJO(pojo.getId(),
				pojo.getFirstName(),
				pojo.getLastName(),
				pojo.getEmail(),
				pojo.getPassword(),
				pojo.getAddress(),
				pojo.getCity(),
				pojo.getState(),
				pojo.getPhoneNumber(),
				pojo.getBirthday(),
				pojo.getRole(),
				pojo.getMustChangePassword(),
				true);
	}

	@Override
	public UserPOJO getUser(PatientPOJO pojo) {
		return new UserPOJO(pojo.getId(),
				pojo.getFirstName(),
				pojo.getLastName(),
				pojo.getEmail(),
				pojo.getPassword(),
				pojo.getAddress(),
				pojo.getCity(),
				pojo.getState(),
				pojo.getPhoneNumber(),
				pojo.getBirthday(),
				pojo.getRole(),
				pojo.isActivated());
	}

}
