package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.MedicalStaffFilterDTO;
import com.ftn.dr_help.dto.MedicalStaffInfoDTO;
import com.ftn.dr_help.model.enums.FilterMedicalStaffEnum;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.NurseRepository;

@Service
public class MedicalStuffService {

	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	public List<MedicalStaffInfoDTO> findAll(Long clinic_id) {
		
		if(clinic_id == null) {
			return null;
		}
		
		List<MedicalStaffInfoDTO> ret = new ArrayList<MedicalStaffInfoDTO>();
		
		if (findDoctors(clinic_id) != null) {
			ret.addAll(findDoctors(clinic_id));
		}
		if (findNurses(clinic_id) != null) {
			ret.addAll(findNurses(clinic_id));
		}
		
		if(ret.isEmpty()) {
			return new ArrayList<MedicalStaffInfoDTO> ();
		}
		
		return ret;
	}
	
	public List<MedicalStaffInfoDTO> findDoctors(Long clinicId) {
		if(clinicId == null) {
			return null;
		}
		
		List<MedicalStaffInfoDTO> ret = new ArrayList<MedicalStaffInfoDTO>();
		List<DoctorPOJO> findedDoctors = doctorRepository.findAllByClinic_id(clinicId);
		
		if(findedDoctors != null) {
			for(DoctorPOJO doctor : findedDoctors) {
				//logic delete
				if(doctor.isDeleted()) {
					continue;
				}
				
				Long count = doctorRepository.getDoctorsAppointmentsCount(doctor.getId());
				boolean canDelete = (count!=null && count>0)? false: true;
				
				Float rating = doctorRepository.getAverageRatingFor(doctor.getId());
				if(rating == null) {
					rating = new Float(0.0f);
				}
				
				ret.add(new MedicalStaffInfoDTO(
							doctor.getEmail(),
							doctor.getFirstName(),
							doctor.getLastName(),
							canDelete,
							RoleEnum.DOCTOR,
							doctor.getId(),
							rating
						));
			}
		}
		
		if(ret.isEmpty()) {
			return null;
		}
		
		return ret;
	}
	
	public List<MedicalStaffInfoDTO> findNurses(Long clinicId) {
		if(clinicId == null) {
			return null;
		}
		
		List<MedicalStaffInfoDTO> ret = new ArrayList<MedicalStaffInfoDTO>();
		List<NursePOJO> findedNurses = nurseRepository.findAllByClinic_id(clinicId);
		
		if(findedNurses != null) {
			for(NursePOJO nurse : findedNurses) {
				//logic delete
				if(nurse.isDeleted()) {
					continue;
				}
				
				Long count = nurseRepository.getNursesAppointmentsCount(nurse.getId());
				
				boolean canDelete = (count!=null && count>0)? false: true;
				
				ret.add(new MedicalStaffInfoDTO(
							nurse.getEmail(),
							nurse.getFirstName(),
							nurse.getLastName(),
							canDelete,
							RoleEnum.NURSE,
							nurse.getId(),
							0.0f
						));
			}
		}
		
		if(ret.isEmpty()) {
			return null;
		}
		
		return ret;
	}
	
	public List<MedicalStaffInfoDTO> filter(MedicalStaffFilterDTO filter, String email) {
		
		System.out.println("role" + filter.getRole());
		System.out.println( filter.getRole() == FilterMedicalStaffEnum.DOCTORS);
		
		Long clinicId = adminRepository.findOneByEmail(email).getClinic().getId();
		try {
			
			if(filter.getRole() == FilterMedicalStaffEnum.DISABLED) {
				List<MedicalStaffInfoDTO> list = findAll(clinicId);
				
				if(filter.getString().isEmpty()) {
					return list;
				} else {
					return searchFilter(list, filter.getString());
				}
			} else if(filter.getRole() == FilterMedicalStaffEnum.DOCTORS) {
				List<MedicalStaffInfoDTO> list = findDoctors(clinicId);
				
				if(filter.getString().isEmpty()) {
					return list;
				} else {
					return searchFilter(list, filter.getString());
				}
			} else {
				List<MedicalStaffInfoDTO> list = findNurses(clinicId);
				
				if(filter.getString().isEmpty()) {
					return list;
				} else {
					return searchFilter(list, filter.getString());
				}
			}
			
		} catch (Exception e) {
			return findAll(clinicId);
		}
	}

	private List<MedicalStaffInfoDTO> searchFilter(List<MedicalStaffInfoDTO> list, String filter) {
		List<MedicalStaffInfoDTO> finded = new ArrayList<MedicalStaffInfoDTO>();
		
		String search = "";
		for(MedicalStaffInfoDTO staff : list) {
			search = staff.getFirstName().toLowerCase() + staff.getLastName().toLowerCase() + staff.getEmail().toLowerCase();
			
			if(search.contains(filter.toLowerCase())) {
				finded.add(staff);
			}
		}
		
		return finded;
	}

}
