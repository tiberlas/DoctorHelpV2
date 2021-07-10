package com.ftn.dr_help.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.convertor.EncapsulateUserDetails;
import com.ftn.dr_help.model.convertor.EncapsulateUserDetailsInterface;
import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserPOJO;
import com.ftn.dr_help.repository.CentreAdministratorRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.PatientRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	/*
	 * when requesting to log in it has only e-mail and password of user
	 * this seeks all repositories and with UserDetailsAdapter encapsulates the user
	 * and sends the user (if finded) to JwtRequestFilter
	 * */
	
	@Autowired
	private CentreAdministratorRepository centreAdminRepository;

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	private EncapsulateUserDetailsInterface convertUser;
	
	private UserPOJO loadOneByEmail(String email) {
		if(email == null || email.trim().equals("")) {
			return null;
		}
		
		
		convertUser = new EncapsulateUserDetails();
		
		CentreAdministratorPOJO finded = centreAdminRepository.findOneByEmail(email);
		
		if(finded != null) {
			return convertUser.getUser(finded);
		}
		
		ClinicAdministratorPOJO finded1 = clinicAdministratorRepository.findOneByEmail(email);
		
		if(finded1 != null) {
			return convertUser.getUser(finded1);
		}
		
		DoctorPOJO finded2 = doctorRepository.findOneByEmail(email);
		
		if(finded2 != null) {
			return convertUser.getUser(finded2);
		}
		
		NursePOJO finded3 = nurseRepository.findOneByEmail(email);
		
		if(finded3 != null) {
			return convertUser.getUser(finded3);
		}
		
		PatientPOJO finded4 = patientRepository.findOneByEmail(email);
		
		if(finded4 != null) {
			return convertUser.getUser(finded4);
		}
		
		
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username == null || username.trim() == "") {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		}
		
		UserPOJO finded = loadOneByEmail(username);
		
		if(finded == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		} else {
			return finded;
		}
	}

}
