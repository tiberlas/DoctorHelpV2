package com.ftn.dr_help.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;

@Repository
public interface ProcedureTypeRepository extends JpaRepository<ProceduresTypePOJO, Long>{

	@Query (value = "select distinct pt.name from procedures_type pt inner join doctors d on pt.id = d.procedure_type_id where is_operation = false and pt.deleted = false and pt.is_operation = false", nativeQuery = true)
	public List<String> getProcedureTypes ();
	
	@Query (value = "select price from procedures_type pt where pt.deleted <> true and clinic_id = ?1 and name = ?2", nativeQuery = true)
	public Double getPrice (Long clinicId, String procedureName);
	
	Optional<ProceduresTypePOJO> findOneByName(String name);
	
	
    Optional<ProceduresTypePOJO> findByIdAndClinic_id(Long id, Long clinic_id);
	
    @Query (value = "select pt.* from procedures_type pt where pt.deleted <> true and pt.clinic_id = ?1 and pt.is_operation = true", nativeQuery = true)
    List<ProceduresTypePOJO> findAllOperations(Long clinicId);

    @Query(value = "select pt.* from procedures_type pt \n" + 
    		"inner join clinic_administrator ca \n" + 
    		"on pt.clinic_id = ca.clinic_id \n" + 
    		"where ca.email = ?1 \n" + 
    		"and pt.deleted = false \n" + 
    		"and pt.is_operation = false", nativeQuery = true)
    List<ProceduresTypePOJO> getAllNotOperations(String adminEmail);
    
    

	@Query (value = "select * from procedures_type pt where pt.\"name\" = ?1 and pt.deleted <> true", nativeQuery = true)
	public List<ProceduresTypePOJO> findAllByName (String namew);
    
}
