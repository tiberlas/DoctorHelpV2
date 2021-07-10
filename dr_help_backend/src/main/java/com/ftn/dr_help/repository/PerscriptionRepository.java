package com.ftn.dr_help.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ftn.dr_help.model.pojo.PerscriptionPOJO;

public interface PerscriptionRepository extends JpaRepository<PerscriptionPOJO, Long>{
	
	
	//List<PerscriptionPOJO> findNursePerscriptions(Long nurseId);
	
	@Query(value="select p.* from appointments a " +
 "inner join examination_reportpojo er on a.examination_report_id = er.id " +
 "inner join perscriptionpojo p on er.perscription_id = p.id where (p.signing_nurse_id is null) and (a.nurse_id = ?1) and (a.deleted = false)", nativeQuery=true)
	public List<PerscriptionPOJO> findAllPendingPerscriptions(Long id);
	
	public PerscriptionPOJO findOneById(Long id);
	
	
}
