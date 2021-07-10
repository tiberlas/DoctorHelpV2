package com.ftn.dr_help.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.MedicationPOJO;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationPOJO, Long>{
	
	Optional<MedicationPOJO> findOneByMedicationName(String name);
	

//	//izvuci sve recepte iz sistema
//	@Query(value="select p.* from appointments a " +
//			"inner join examination_reportpojo er on a.examination_report_id = er.id " +
//			"inner join perscriptionpojo p on er.perscription_id = p.id " +
//			"where a.status = 'DONE' and a.deleted = false", nativeQuery=true)
//	public List<PerscriptionPOJO> findAllDonePerscriptions();
//	
	
	@Query(value="select count(m.*) from perscriptionpojo_medication_list pml inner join medicationpojo m on pml.medication_list_id = m.id where m.id = ?1", nativeQuery=true)
	public Integer findMedicationOccurencesInPerscriptions(Long medication_id);
}
