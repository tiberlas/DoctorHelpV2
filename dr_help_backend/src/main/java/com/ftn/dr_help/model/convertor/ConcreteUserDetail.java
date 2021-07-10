package com.ftn.dr_help.model.convertor;

import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;

@Service
public class ConcreteUserDetail implements ConcreteUserDetailInterface{

	/*
	 * assumption UserDetailDTO is valid but may have empty fields 
	 * */
	
	@Override
	public void changeTo(CentreAdministratorPOJO to, UserDetailDTO from) {
		if(from.getFirstName() != null && !from.getFirstName().trim().equals("")) {
			to.setFirstName(from.getFirstName());
		}
		if(from.getLastName() != null && !from.getLastName().trim().equals("")) {
			to.setLastName(from.getLastName());
		}
		if(from.getAddress() != null && !from.getAddress().trim().equals("")) {
			to.setAddress(from.getAddress());
		}
		if(from.getState() != null && !from.getState().trim().equals("")) {
			to.setState(from.getState());
		}
		if(from.getCity() != null && !from.getCity().trim().equals("")) {
			to.setCity(from.getCity());
		}
		if(from.getPhoneNumber() != null && !from.getPhoneNumber().trim().equals("")) {
			to.setPhoneNumber(from.getPhoneNumber());
		}
		
	}

	@Override
	public void changeTo(ClinicAdministratorPOJO to, UserDetailDTO from) {
		if(from.getFirstName() != null && !from.getFirstName().trim().equals("")) {
			to.setFirstName(from.getFirstName());
		}
		if(from.getLastName() != null && !from.getLastName().trim().equals("")) {
			to.setLastName(from.getLastName());
		}
		if(from.getAddress() != null && !from.getAddress().trim().equals("")) {
			to.setAddress(from.getAddress());
		}
		if(from.getState() != null && !from.getState().trim().equals("")) {
			to.setState(from.getState());
		}
		if(from.getCity() != null && !from.getCity().trim().equals("")) {
			to.setCity(from.getCity());
		}
		if(from.getPhoneNumber() != null && !from.getPhoneNumber().trim().equals("")) {
			to.setPhoneNumber(from.getPhoneNumber());
		}
		
	}

	@Override
	public void changeTo(DoctorPOJO to, UserDetailDTO from) {
		if(from.getFirstName() != null && !from.getFirstName().trim().equals("")) {
			to.setFirstName(from.getFirstName());
		}
		if(from.getLastName() != null && !from.getLastName().trim().equals("")) {
			to.setLastName(from.getLastName());
		}
		if(from.getAddress() != null && !from.getAddress().trim().equals("")) {
			to.setAddress(from.getAddress());
		}
		if(from.getState() != null && !from.getState().trim().equals("")) {
			to.setState(from.getState());
		}
		if(from.getCity() != null && !from.getCity().trim().equals("")) {
			to.setCity(from.getCity());
		}
		if(from.getPhoneNumber() != null && !from.getPhoneNumber().trim().equals("")) {
			to.setPhoneNumber(from.getPhoneNumber());
		}
		
	}

	@Override
	public void changeTo(NursePOJO to, UserDetailDTO from) {
		if(from.getFirstName() != null && !from.getFirstName().trim().equals("")) {
			to.setFirstName(from.getFirstName());
		}
		if(from.getLastName() != null && !from.getLastName().trim().equals("")) {
			to.setLastName(from.getLastName());
		}
		if(from.getAddress() != null && !from.getAddress().trim().equals("")) {
			to.setAddress(from.getAddress());
		}
		if(from.getState() != null && !from.getState().trim().equals("")) {
			to.setState(from.getState());
		}
		if(from.getCity() != null && !from.getCity().trim().equals("")) {
			to.setCity(from.getCity());
		}
		if(from.getPhoneNumber() != null && !from.getPhoneNumber().trim().equals("")) {
			to.setPhoneNumber(from.getPhoneNumber());
		}
		
	}

	@Override
	public void changeTo(PatientPOJO to, UserDetailDTO from) {
		if(from.getFirstName() != null && !from.getFirstName().trim().equals("")) {
			to.setFirstName(from.getFirstName());
		}
		if(from.getLastName() != null && !from.getLastName().trim().equals("")) {
			to.setLastName(from.getLastName());
		}
		if(from.getAddress() != null && !from.getAddress().trim().equals("")) {
			to.setAddress(from.getAddress());
		}
		if(from.getState() != null && !from.getState().trim().equals("")) {
			to.setState(from.getState());
		}
		if(from.getCity() != null && !from.getCity().trim().equals("")) {
			to.setCity(from.getCity());
		}
		if(from.getPhoneNumber() != null && !from.getPhoneNumber().trim().equals("")) {
			to.setPhoneNumber(from.getPhoneNumber());
		}
		
	}

}
