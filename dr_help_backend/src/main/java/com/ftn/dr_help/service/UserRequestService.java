package com.ftn.dr_help.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;
import com.ftn.dr_help.repository.CentreAdministratorRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.repository.UserRequestRepository;

@Service
public class UserRequestService {

	@Autowired
	private UserRequestRepository userRequestRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private CentreAdministratorRepository centreAdministratorRepository;
	
	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	public boolean patientExists (String email) {
		 PatientPOJO finded = patientRepository.findOneByEmail(email);
		 if(finded != null && finded.isActivated()) {
			 return true;
		 }
		 
		return false;
	}
	
	public boolean doctorExists (String email) {
		return (doctorRepository.findOneByEmail(email) != null);
	}
	
	public boolean nurseExists (String email) {
		return (nurseRepository.findOneByEmail (email) != null);
	}
	
	public boolean clinicalAdminExists (String email) {
		return (clinicAdministratorRepository.findOneByEmail (email) != null);
	}
	
	public boolean centreAdministratorExists (String email) {
		return (centreAdministratorRepository.findOneByEmail(email) != null);
	}
	
	public boolean requestEmailExists (String email) {
		return (userRequestRepository.findByEmail(email) != null);
	}
	
	public boolean insuranceNumberExists (String insuranceNumber) {
		return (patientRepository.findByInsuranceNumber(Long.parseLong(insuranceNumber)) != null);
	}
	
	public boolean requestInsuranceNumberExists (String insuranceNumber) {
		return (userRequestRepository.findByInsuranceNumber(Long.parseLong(insuranceNumber)) != null);
	}
	
	public void addNewRequest (UserRequestPOJO newUserRequest) {
		userRequestRepository.save(newUserRequest);
	}
	
}
