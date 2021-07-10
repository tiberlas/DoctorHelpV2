package com.ftn.dr_help.model.convertor;

import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserPOJO;

public interface EncapsulateUserDetailsInterface {

	/*
	 * this adapter converts specific user from data base to an abstraction of an user
	 * it is used for JWT to encapsulate all different users 
	 * */
	
	UserPOJO getUser(CentreAdministratorPOJO pojo);
	UserPOJO getUser(ClinicAdministratorPOJO pojo);
	UserPOJO getUser(DoctorPOJO pojo);
	UserPOJO getUser(NursePOJO pojo);
	UserPOJO getUser(PatientPOJO pojo);

	
}
