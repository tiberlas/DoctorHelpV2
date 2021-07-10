package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.MedicalStaffReviewDTO;
import com.ftn.dr_help.model.pojo.DoctorReviewPOJO;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorReviewRepository;

@Service
public class MedicalStaffReviewService {

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	@Autowired
	private DoctorReviewRepository doctorReviewRepository;
	
	public List<MedicalStaffReviewDTO> getAll(String email) {
		try {
			
		Long clinicId = clinicAdministratorRepository.findOneByEmail(email).getClinic().getId();
		
 		List<DoctorReviewPOJO> finded = doctorReviewRepository.getAllWithNameAndRating(clinicId);
 		List<MedicalStaffReviewDTO> retVal = new ArrayList<MedicalStaffReviewDTO>();
 		
 		int indexRetVal = -1;
 		int countOfSameDoctors = 1;
 		for(int indexFinded = 0; indexFinded<finded.size(); ++indexFinded) {
 			if(!retVal.isEmpty() && retVal.get(indexRetVal).getMedicalStaffId().equals(finded.get(indexFinded).getDoctor().getId())) {
 				++countOfSameDoctors;
 				retVal.set(indexRetVal, new MedicalStaffReviewDTO(
 						retVal.get(indexRetVal).getMedicalStaffId(),
 						retVal.get(indexRetVal).getReview() +  new Float(finded.get(indexFinded).getRating()),
 						retVal.get(indexRetVal).getFullName()));
 			} else {
 				//izracuna srednju vrednost od prethodnog lekara
 				if(!retVal.isEmpty()) {
 				retVal.set(indexRetVal, new MedicalStaffReviewDTO(
 						retVal.get(indexRetVal).getMedicalStaffId(),
 						(retVal.get(indexRetVal).getReview() / countOfSameDoctors),
 						retVal.get(indexRetVal).getFullName())); 				
 				}
 				
 				countOfSameDoctors = 1;
 				++indexRetVal;
 				retVal.add(
 						new MedicalStaffReviewDTO(
 								finded.get(indexFinded).getDoctor().getId(),
 								new Float(finded.get(indexFinded).getRating()),
 								finded.get(indexFinded).getDoctor().getFirstName() +" "+ finded.get(indexFinded).getDoctor().getLastName()));
 			}
 		}
 		
 		//izracuna srednju vrednost od poslednjeg lekara
 		retVal.set(indexRetVal, new MedicalStaffReviewDTO(
					retVal.get(indexRetVal).getMedicalStaffId(),
					(retVal.get(indexRetVal).getReview() / countOfSameDoctors),
					retVal.get(indexRetVal).getFullName())); 
 		
 		return retVal;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
