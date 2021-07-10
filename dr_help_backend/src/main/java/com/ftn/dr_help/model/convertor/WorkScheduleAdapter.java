package com.ftn.dr_help.model.convertor;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;

@Service
public class WorkScheduleAdapter {

	public MedicalStaffWorkSchedularPOJO fromDoctor(DoctorPOJO doctor) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(doctor.getProcedureType().getDuration());
		return new MedicalStaffWorkSchedularPOJO(
				doctor.getMonday(), 
				doctor.getTuesday(), 
				doctor.getWednesday(), 
				doctor.getThursday(), 
				doctor.getFriday(), 
				doctor.getSaturday(), 
				doctor.getSunday(), 
				cal);
	}
	
	public MedicalStaffWorkSchedularPOJO fromNurse(NursePOJO nurse, Calendar duration) {
		return new MedicalStaffWorkSchedularPOJO(
				nurse.getMonday(), 
				nurse.getTuesday(), 
				nurse.getWednesday(), 
				nurse.getThursday(), 
				nurse.getFriday(), 
				nurse.getSaturday(), 
				nurse.getSunday(), 
				duration);
	}
}
