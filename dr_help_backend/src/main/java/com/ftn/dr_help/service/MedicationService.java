package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.MedicationDTO;
import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired 
	private MedicationRepository medicationRepository;
	
	
	
	public List<MedicationDTO> findAll() {
		
		List<MedicationPOJO> list = medicationRepository.findAll();
		List<MedicationDTO> dtoList = new ArrayList<MedicationDTO>();
		
		for (MedicationPOJO medicationPOJO : list) {
			MedicationDTO dto = new MedicationDTO(medicationPOJO);
			Integer count = medicationRepository.findMedicationOccurencesInPerscriptions(medicationPOJO.getId());
			if(count == 0) {
				dto.setReserved(false);
			} else {
				dto.setReserved(true);
			}
			
			dto.setId(medicationPOJO.getId());
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	public void delete(Long id) {
		medicationRepository.deleteById(id);
	}
	
	
	
	public MedicationPOJO save(MedicationPOJO med) {
		MedicationPOJO finded = medicationRepository.findOneByMedicationName(med.getMedicationName()).orElse(null);
		if(finded == null)
			return medicationRepository.save(med);
		return null;
	}
	
	public MedicationPOJO findByName(String name) {
		
		return medicationRepository.findOneByMedicationName(name).orElse(null);
	}

	
}
