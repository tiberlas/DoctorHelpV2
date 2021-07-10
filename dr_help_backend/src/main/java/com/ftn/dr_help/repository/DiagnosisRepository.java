package com.ftn.dr_help.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.DiagnosisPOJO;

@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisPOJO, Long> {

	public DiagnosisPOJO findOneByDiagnosis(String name);
	
	
	@Query(value="select count(p.*) from perscriptionpojo p where diagnosis_id = ?1", nativeQuery=true)
	public Integer isDiagnosisPerscribed(Long id);
}
