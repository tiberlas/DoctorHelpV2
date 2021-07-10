package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.MedicationDisplayDTO;
import com.ftn.dr_help.dto.SignOffDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.PerscriptionPOJO;
import com.ftn.dr_help.model.pojo.TherapyPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.PerscriptionRepository;

@Service
public class PerscriptionService {

	@Autowired AppointmentRepository appointmentRepository;
	
	@Autowired
	private PerscriptionRepository perscriptionRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	
	public List<SignOffDTO> findAllPendingPerscriptions(Long nurse_id) {
		
		List<PerscriptionPOJO> perscriptions = perscriptionRepository.findAllPendingPerscriptions(nurse_id);
		
		List<SignOffDTO> list = new ArrayList<SignOffDTO>();
		
		for(PerscriptionPOJO per : perscriptions) {
			System.out.println("PERSCRIPTION: " + per.getDiagnosis().getDiagnosis() + " " + per.getDiagnosis().getPerscription());
			
			SignOffDTO dto = new SignOffDTO();
			if(per.getDiagnosis() != null) {
				dto.setDiagnosis(per.getDiagnosis().getDiagnosis());
				dto.setDescription(per.getDiagnosis().getDescription());
			} else {
				dto.setDiagnosis("");
				dto.setDescription("");
			}
			
			List<MedicationDisplayDTO> medicationList = new ArrayList<MedicationDisplayDTO>();
			if(per.getMedicationList() != null) {
			
				for (MedicationPOJO m : per.getMedicationList()) {
					MedicationDisplayDTO mdDTO = new MedicationDisplayDTO();
					mdDTO.setMedicationName(m.getMedicationName());
					mdDTO.setMedicationDescription(m.getMedDescription());
					medicationList.add(mdDTO);
				}
				
				dto.setMedicationList(medicationList);
				
				
			}
			
			if (medicationList.size() == 0) {
				medicationList.add(new MedicationDisplayDTO ("-", "-"));
			}
			
			
			TherapyPOJO therapy = per.getTherapy();
			if (therapy != null) {
				dto.setAdvice(therapy.getAdvice());
			} else {
				dto.setAdvice("");
			}
			

			System.out.println("DOCA JE: " + per.getExaminationReport().getAppointment().getDoctor().getFirstName());
			
			
			DoctorPOJO doctor = per.getExaminationReport().getAppointment().getDoctor();
			if(doctor != null) {
				dto.setDoctor(doctor.getFirstName() + " " + doctor.getLastName());
			} else {
				dto.setDoctor("idk fam");
			}
			
			PatientPOJO patient = per.getExaminationReport().getAppointment().getPatient();
			if(patient != null) {
				dto.setPatient(patient.getFirstName() + " " + patient.getLastName());
			} else {
				dto.setPatient("idk patient");
			}
			
			dto.setPerscriptionId(per.getId());
			
			list.add(dto);
			
		}
		
		
		
		return list;
	}
	

	public SignOffDTO findPerscriptionFromAppointment(Long appointment_id) {
		
		AppointmentPOJO appointment = appointmentRepository.findOneById(appointment_id);
		PerscriptionPOJO perscription = appointment.getExaminationReport().getPerscription();
		
		SignOffDTO dto = new SignOffDTO();
		
		if(perscription.getDiagnosis() != null) {
			dto.setDiagnosis(perscription.getDiagnosis().getDiagnosis());
			dto.setDescription(perscription.getDiagnosis().getDescription());
		} else {
			dto.setDiagnosis("");
			dto.setDescription("");
		}
		
		List<MedicationDisplayDTO> medicationList = new ArrayList<MedicationDisplayDTO>();
		if(perscription.getMedicationList() != null) {
		
			for (MedicationPOJO m : perscription.getMedicationList()) {
				MedicationDisplayDTO mdDTO = new MedicationDisplayDTO();
				mdDTO.setMedicationName(m.getMedicationName());
				mdDTO.setMedicationDescription(m.getMedDescription());
				medicationList.add(mdDTO);
			}
			
			dto.setMedicationList(medicationList);
			
			
		}
		
		if (medicationList.size() == 0) {
			medicationList.add(new MedicationDisplayDTO ("-", "-"));
		}
		
		
		TherapyPOJO therapy = perscription.getTherapy();
		if (therapy != null) {
			dto.setAdvice(therapy.getAdvice());
		} else {
			dto.setAdvice("");
		}
		
		if(perscription.getSigningNurse() == null) {
			dto.setSigned(false);
			System.out.println("Signing nurse is null");
		} else {
			dto.setSigned(true);
			System.out.println("Nurse already signed off the perscription");
		}
		
		return dto;
		
	}
	
	public void signAppointmentPerscription(Long appointment_id) {
		AppointmentPOJO appointment = appointmentRepository.findOneById(appointment_id);
		PerscriptionPOJO perscription = appointment.getExaminationReport().getPerscription();
		perscription.setSigningNurse(appointment.getNurse()); //nurse has to be assigned.
		perscriptionRepository.save(perscription);
		
		System.out.println("Successfully signed off perscription ID " 
		+ appointment.getExaminationReport().getPerscription().getId() + " as a nurse: " + appointment.getNurse().getFirstName());
	}
	
	
	public PerscriptionPOJO signPerscription(Long nurseId, Long perscriptionId) {
		
		PerscriptionPOJO perscription = perscriptionRepository.findOneById(perscriptionId);
		NursePOJO nurse = nurseRepository.findOneById(nurseId);
		perscription.setSigningNurse(nurse);
		
		perscriptionRepository.save(perscription);
		
		return perscription;
		
	}
	
}
