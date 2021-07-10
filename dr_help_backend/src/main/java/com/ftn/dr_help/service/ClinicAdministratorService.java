package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.ClinicAdminDTO;
import com.ftn.dr_help.dto.ClinicAdminNameDTO;
import com.ftn.dr_help.dto.ClinicAdminProfileDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.convertor.ConcreteUserDetailInterface;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.validation.PasswordValidate;

@Service
public class ClinicAdministratorService {
	
	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Autowired
	private PasswordValidate passwordValidate;
	
	@Autowired
	private ConcreteUserDetailInterface convertor;
	
	
	public ClinicAdministratorPOJO findOne(Long id) {
		return clinicAdministratorRepository.findById(id).orElseGet(null);
	}
	
	public ClinicAdminProfileDTO findOneProfile(String email) {
		if(email == null) {
			return null;
		}
		
		ClinicAdministratorPOJO admin = clinicAdministratorRepository.findOneByEmail(email);
		
		if(admin == null)
			return null;
		
		return new ClinicAdminProfileDTO(admin);
	}

	public ClinicAdminNameDTO findOnesName(String email) {
		if(email == null) {
			return null;
		}
		
		ClinicAdministratorPOJO admin = clinicAdministratorRepository.findOneByEmail(email);
		ClinicAdminNameDTO ret;
		
		if(admin == null)
			ret = null;
		else
			ret = new ClinicAdminNameDTO(admin);
		
		return ret;
	}
	
	
	public ClinicAdministratorPOJO findOneByEmail(String mail) {
		return clinicAdministratorRepository.findOneByEmail(mail);
	}
	
	public List<ClinicAdminDTO> findAll() {
		
		List<ClinicAdministratorPOJO> list = clinicAdministratorRepository.findAll();
		List<ClinicAdminDTO> dtoList = new ArrayList<ClinicAdminDTO>();
		for (ClinicAdministratorPOJO clinicAdministratorPOJO : list) {
			ClinicAdminDTO dto = new ClinicAdminDTO();
			dto.setBirthday(clinicAdministratorPOJO.getBirthday().getTime());
			dto.setClinicName(clinicAdministratorPOJO.getClinic().getName());
			dto.setEmail(clinicAdministratorPOJO.getEmail());
			dto.setFirstName(clinicAdministratorPOJO.getFirstName());
			dto.setId(clinicAdministratorPOJO.getId());
			dto.setLastName(clinicAdministratorPOJO.getLastName());
			
			dtoList.add(dto);
		}
		
		return dtoList;
		
		
	}
	
	public Page<ClinicAdministratorPOJO> findAll(Pageable page) {
		return clinicAdministratorRepository.findAll(page);
	}

	public ClinicAdminProfileDTO save(UserDetailDTO admin, String email) {
		if(admin == null) {
			return null;
		}
		ClinicAdministratorPOJO current = clinicAdministratorRepository.findOneByEmail(email);
		
		
		if(current == null)
			return null;
		System.out.println("current salje stvar " + current.getAddress());
		
		convertor.changeTo(current, admin);
		
		System.out.println("posle changeTO" + current.getAddress());
		clinicAdministratorRepository.save(current);
				
		return new ClinicAdminProfileDTO(current);
	}

	public void remove(Long id) {
		if(id == null) {
			return;
		}
		
		clinicAdministratorRepository.deleteById(id);
	}
	
	public ClinicAdministratorPOJO save(ClinicAdministratorPOJO admin) {
		if(admin == null) {
			return null;
		}
		
		return clinicAdministratorRepository.save(admin);
	}
	
	public boolean changePassword(ChangePasswordDTO password, String email) {
		if(password == null) {
			return false;
		}
		
		ClinicAdministratorPOJO finded = clinicAdministratorRepository.findOneByEmail(email);
		if(finded == null)
			return false;
		
		//PasswordValidateInterface validate = new PasswordValidate();
		
		if(passwordValidate.isValid(password, finded.getPassword())) {

			String encoded = encoder.getEncoder().encode(password.getNewPassword());

			finded.setPassword(encoded);
			finded.setMustChangePassword(false);
			clinicAdministratorRepository.save(finded);
			return true;
		}
		
		return false;
	}
	
}
