package com.ftn.dr_help.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.ClinicReviewRepository;

@Service
public class ClinicReviewService {

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	@Autowired
	private ClinicReviewRepository clinicReviewRepository;
	
	public Float getAverage(String email) {
		try {
			Long clinicId = clinicAdministratorRepository.findOneByEmail(email).getClinic().getId();
			
			return clinicReviewRepository.getAverageReview(clinicId);
		} catch (Exception e) {
			return null;
		}
	} 
}
