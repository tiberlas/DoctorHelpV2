package com.ftn.dr_help.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ftn.dr_help.model.pojo.ClinicReviewPOJO;

public interface ClinicReviewRepository extends JpaRepository<ClinicReviewPOJO, Long> {
	
	@Query (value = "select * from clinic_rewiew where patient_id = ?1 and clinic_id = ?2", nativeQuery = true)
	public ClinicReviewPOJO getClinicReview (Long patientId, Long clinicId);
	
	@Modifying
	@Query (value = "update clinic_rewiew set rating = ?1 where clinic_id = ?3 and patient_id = ?2", nativeQuery = true)
	public int updateReview (Integer newReting, Long patientId, Long clinicId);
	
	
	@Query (value = "select avg(rating) from clinic_rewiew where clinic_id = ?1", nativeQuery = true)
	public Float getAverageReview (Long clinicId);
	
}
