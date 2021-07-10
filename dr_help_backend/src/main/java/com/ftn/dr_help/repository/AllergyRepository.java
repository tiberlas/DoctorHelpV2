package com.ftn.dr_help.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.AllergyPOJO;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyPOJO, Long> {

	List<AllergyPOJO> findAllByHealthRecordId (Long id);
	
	AllergyPOJO findOneByAllergy(String allergy);
	
}
