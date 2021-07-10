package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.dto.DiagnosisDTO;
import com.ftn.dr_help.model.pojo.DiagnosisPOJO;
import com.ftn.dr_help.repository.DiagnosisRepository;

@Service
public class DiagnosisService {

	@Autowired
	private DiagnosisRepository diagnosisRepository;
	
	
	public List<DiagnosisDTO> findAll() {
		List<DiagnosisPOJO> list =  diagnosisRepository.findAll();
		
		List<DiagnosisDTO> dDTO = new ArrayList<DiagnosisDTO>();
		
		for(DiagnosisPOJO pojo : list) {
			DiagnosisDTO dto = new DiagnosisDTO(pojo);
			Integer count = diagnosisRepository.isDiagnosisPerscribed(pojo.getId());
			if(count == 0) {
				dto.setReserved(false);
			} else {
				dto.setReserved(true);
			}
			
			dDTO.add(dto);
		}
		
		return dDTO;
	}
	
	public void delete(Long id) {
		diagnosisRepository.deleteById(id);
	}
	
	
	public DiagnosisPOJO save(DiagnosisPOJO diag) {
		return diagnosisRepository.save(diag); 
	}
	
	
	public DiagnosisPOJO findByDiagnosis(String name) {
		return diagnosisRepository.findOneByDiagnosis(name);
	}

	
	
}