package com.ftn.dr_help.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.pojo.HealthRecordPOJO;
import com.ftn.dr_help.repository.HealthRecordRepository;

@Service
public class HealthRecordService {
	
	@Autowired
	private HealthRecordRepository healthRecordRepository;
	
	
	public HealthRecordPOJO save(HealthRecordPOJO healthRecord) {
		return healthRecordRepository.save(healthRecord);
	}
	
	public List<HealthRecordPOJO> findAll() {
		return healthRecordRepository.findAll();
	}
	
	public HealthRecordPOJO findById(Long id) {
		return healthRecordRepository.findById(id).orElse(null);
	}

}
