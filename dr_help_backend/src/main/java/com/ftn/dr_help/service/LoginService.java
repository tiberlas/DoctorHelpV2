package com.ftn.dr_help.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.repository.CentreAdministratorRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.PatientRepository;

@Service
public class LoginService {

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
	
	public PatientPOJO getPatientLoginResponse (String email) {
		PatientPOJO retVal = patientRepository.findOneByEmail (email);
		return retVal;
	}
	
	public DoctorPOJO getDoctorLoginResponse (String email) {
		DoctorPOJO retVal = doctorRepository.findOneByEmail(email);
		return retVal;
	}
	
	public CentreAdministratorPOJO getCentreAdministratorLoginResponse (String email) {
		CentreAdministratorPOJO retVal = centreAdministratorRepository.findOneByEmail (email);
		return retVal;
	}
	
	public ClinicAdministratorPOJO getClinicAdministratorLoginResponse (String email) {
		ClinicAdministratorPOJO retVal = clinicAdministratorRepository.findOneByEmail(email);
		return retVal;
	}
	
	public NursePOJO getNurseLoginResponse (String email) {
		NursePOJO retVal = nurseRepository.findOneByEmail(email);
		return retVal;
	}
	
}
