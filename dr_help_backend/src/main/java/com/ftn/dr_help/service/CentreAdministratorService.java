package com.ftn.dr_help.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.CentreAdminDTO;
import com.ftn.dr_help.dto.CentreAdminProfileDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.PatientRequestDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.convertor.ConcreteUserDetailInterface;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;
import com.ftn.dr_help.repository.CentreAdministratorRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.repository.UserRequestRepository;
import com.ftn.dr_help.validation.PasswordValidate;


@Service
public class CentreAdministratorService {
	
	@Autowired
	private CentreAdministratorRepository administratorRepository;
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Autowired
	private PasswordValidate passwordValidate;
	
	@Autowired
	private ConcreteUserDetailInterface convertor;
	
	@Autowired
	private UserRequestRepository userRequestRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired 
	private Mail mail;
	

	public CentreAdministratorPOJO findOneByEmail(String email) {
		return administratorRepository.findOneByEmail(email);
	}
	
	public CentreAdministratorPOJO findOne(Long id) {
		return administratorRepository.findById(id).orElseGet(null);
	}

	public List<CentreAdministratorPOJO> findAll() {
		return administratorRepository.findAll();
	}

	public CentreAdministratorPOJO save(CentreAdministratorPOJO centerAdministrator) {
		return administratorRepository.save(centerAdministrator);
	}

	public void remove(Long id) {
		administratorRepository.deleteById(id);
	}
	
	public CentreAdminProfileDTO save(UserDetailDTO admin, String email) {
		if(admin == null) {
			return null;
		}
		CentreAdministratorPOJO current = administratorRepository.findOneByEmail(email);
		
		if(current == null)
			return null;
	
		convertor.changeTo(current, admin);
		administratorRepository.save(current);
				
		return new CentreAdminProfileDTO(current);
	}
	
	
	
	public boolean changePassword(ChangePasswordDTO password, String email) {
		if(password == null) {
			return false;
		}
		
		CentreAdministratorPOJO c = administratorRepository.findOneByEmail(email);
		if(c == null)
			return false;
		
		if(passwordValidate.isValid(password, c.getPassword())) {
			String encoded = encoder.getEncoder().encode(password.getNewPassword());
			c.setPassword(encoded);
			c.setMustChangePassword(false);
			administratorRepository.save(c);
			return true;
		}
		
		return false;
	}
	
	
	public CentreAdministratorPOJO createNewAdmin(CentreAdminDTO centreAdminDTO) {
		
		CentreAdministratorPOJO admin = new CentreAdministratorPOJO();
		admin.setFirstName(centreAdminDTO.getFirstName());
		admin.setLastName(centreAdminDTO.getLastName());
		admin.setEmail(centreAdminDTO.getEmail());
		admin.setAddress("...");
		admin.setCity("...");
		admin.setPhoneNumber("...");
		admin.setState("...");
		Calendar birthday = Calendar.getInstance();
		birthday.setTime(centreAdminDTO.getBirthday());
		admin.setBirthday(birthday);
		String password = "coolpassword";
		
		String encoded = encoder.getEncoder().encode(password);
		//p.setPassword(encoded);
		admin.setPassword(encoded);
		mail.sendAccountInfoEmail(admin.getEmail(), password, admin.getFirstName(), admin.getLastName(), RoleEnum.CENTRE_ADMINISTRATOR);
		System.out.println("Successfully sent account info email.");
		
		admin.setMustChangePassword(true);
		
		administratorRepository.save(admin);
		return admin;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public UserRequestPOJO declineRequest(PatientRequestDTO patientDTO) throws Exception {
		 	UserRequestPOJO requested = userRequestRepository.findByEmail(patientDTO.getEmail());
		
			userRequestRepository.deleteById(requested.getId());
			System.out.println("Request successfully deleted");
			
			
			mail.sendDeclineEmail(patientDTO.getEmail(), patientDTO.getDeclinedDescription(), requested.getFirstName(), requested.getLastName());
			System.out.println("Declination mail successfully sent.");
			
			return requested;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public UserRequestPOJO acceptRequest(PatientRequestDTO patientDTO) throws Exception {
		UserRequestPOJO requested = userRequestRepository.findByEmail(patientDTO.getEmail());
		
		PatientPOJO p = new PatientPOJO();
		p.setActivated(false);
		p.setEmail(requested.getEmail());
		p.setFirstName(requested.getFirstName());
		p.setLastName(requested.getLastName());
		p.setAddress(requested.getAddress());
		p.setCity(requested.getCity());
		p.setState(requested.getState());
		p.setBirthday(requested.getBirthday());
		p.setInsuranceNumber(requested.getInsuranceNumber());
		p.setPhoneNumber(requested.getPhoneNumber());
		
		System.out.println("Password is " + requested.getPassword());
		String encoded = encoder.getEncoder().encode(requested.getPassword());
		p.setPassword(encoded);

		
		patientRepository.save(p);
		System.out.println("Patient successfully registered.");
		
		try {
			mail.sendAcceptEmail(p.getEmail(), p.getFirstName(), p.getLastName());
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Successfully sent email.");
		
		userRequestRepository.deleteById(requested.getId());
		System.out.println("Request successfully deleted");
		
		return requested;
		
	}
	

}
