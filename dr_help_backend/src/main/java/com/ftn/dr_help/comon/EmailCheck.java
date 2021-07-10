package com.ftn.dr_help.comon;

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
public class EmailCheck {
	/**
	 * Checks the email on the hole DataBase
	 * if an email is in use then returns false
	 * */

	@Autowired
	private ClinicAdministratorRepository clinicAdminRepository;
	
	@Autowired
	private CentreAdministratorRepository centreAdminRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	public boolean checkIfValid(String email) {
		
		ClinicAdministratorPOJO clinicAdmin = clinicAdminRepository.findOneByEmail(email);
		if(clinicAdmin != null) {
			return false;
		}
		
		CentreAdministratorPOJO centreAdmin = centreAdminRepository.findOneByEmail(email);
		if(centreAdmin != null) {
			return false;
		}
		
		DoctorPOJO doctor = doctorRepository.findOneByEmail(email);
		if(doctor != null) {
			return false;
		}
		
		NursePOJO nurse = nurseRepository.findOneByEmail(email);
		if(nurse != null) {
			return false;
		}
		
		PatientPOJO patient = patientRepository.findOneByEmail(email);
		if(patient != null) {
			return false;
		}
		
		return true;
	}
}
