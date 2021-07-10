package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.service.DoctorService;
import com.ftn.dr_help.service.OperationService;

@Component
public class CheckDoctors implements CheckDoctorsInterface{

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private OperationService operationService;
	
	@Override
	public FreeDoctorForAutomaticallyReserving findFreeDoctor(Calendar requestingDate, Long clinicId, Long procedureId) {
		
		List<DoctorPOJO> doctors = doctorService.getAllDoctorsFromClinicWithSpecialization(clinicId, procedureId);
		
		Calendar firstFreeDate = Calendar.getInstance();
		firstFreeDate.add(Calendar.YEAR, 1);
		DoctorPOJO firstFreeDoctor = null;
		
		for(DoctorPOJO doctor : doctors) {
			Calendar freeDate = doctorService.checkSchedue(doctor.getEmail(), requestingDate);
			
			if(freeDate.equals(requestingDate)) {
				return new FreeDoctorForAutomaticallyReserving(null, doctor);
			} else if(freeDate.before(firstFreeDate)) {
				firstFreeDate = (Calendar) freeDate.clone();
				firstFreeDoctor = doctor;
			}
		}
		
		return new FreeDoctorForAutomaticallyReserving(firstFreeDate, firstFreeDoctor);
	}

	@Override
	public FreeDoctorForAutomaticallyReserving findFreeDoctors(
			Calendar requestedDate, Long clinic_id, Long procedure_id) {
		// TODO Auto-generated method stub
		List<DoctorPOJO> doctors = doctorService.getAllDoctorsFromClinicWithSpecialization(clinic_id, procedure_id);
		
		Calendar firstFreeDate = Calendar.getInstance();
		firstFreeDate.add(Calendar.YEAR, 1);
		
		DoctorPOJO doctor1 = null;
		DoctorPOJO doctor2 = null;
		DoctorPOJO doctor3 = null;
		
		for (int i = 0; i < doctors.size() - 2; i++) {
			for (int j = i + 1; j < doctors.size() - 1; j++) {
				for (int k = j + 1; k < doctors.size(); k++) {
					Calendar freeDate = operationService.findFirstOperationSchedule(doctors.get(i).getId(), doctors.get(j).getId(), doctors.get(k).getId(), requestedDate);
					
					System.out.println("----- first free: " + freeDate.getTime());
					System.out.println("and doctor is: " + doctors.get(i).getId());
					if(freeDate.equals(requestedDate)) {
						return new FreeDoctorForAutomaticallyReserving(null, doctors.get(i), doctors.get(j), doctors.get(k));
					} else if(freeDate.before(firstFreeDate)){
						System.out.println("----- BEFORE : " + freeDate);
						firstFreeDate = (Calendar) freeDate.clone();
						doctor1 = doctors.get(i);
						doctor2 = doctors.get(j);
						doctor3 = doctors.get(k);
						
						System.out.println("----- DOC1 : " + doctor1.getId());
						System.out.println("----- DOC1 : " + doctor2.getId());
						System.out.println("----- DOC1 : " + doctor3.getId());
					}
				}
			}
		}
		
		return new FreeDoctorForAutomaticallyReserving(firstFreeDate, doctor1, doctor2, doctor3);
		
	}
	

}
