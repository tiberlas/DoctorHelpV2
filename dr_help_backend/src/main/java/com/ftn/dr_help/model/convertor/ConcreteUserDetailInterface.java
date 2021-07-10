package com.ftn.dr_help.model.convertor;

import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;

public interface ConcreteUserDetailInterface {

	/*
	 * this adapter is used to convert from EncapsulateUserDetails to Concrete Doctor, Nurse, ...
	 * it is used for changing profile
	 * uses Doctor as references to update it with userDetails
	 * */
	
	void changeTo(CentreAdministratorPOJO to, UserDetailDTO from);
	void changeTo(ClinicAdministratorPOJO to, UserDetailDTO from);
	void changeTo(DoctorPOJO to, UserDetailDTO from);
	void changeTo(NursePOJO to, UserDetailDTO from);
	void changeTo(PatientPOJO to, UserDetailDTO from);

}
