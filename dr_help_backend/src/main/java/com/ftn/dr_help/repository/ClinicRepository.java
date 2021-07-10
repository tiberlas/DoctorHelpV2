package com.ftn.dr_help.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.ClinicPOJO;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicPOJO, Long>{

	public ClinicPOJO findByName(String name);
	
	@Query (value="select distinct c.* from (clinic c inner join doctors d on c.id = d.clinic_id) inner join procedures_type pt on d.procedure_type_id = pt.id where pt.name = ?1", nativeQuery = true)
	public List<ClinicPOJO> getClinicsByProcedureType (String procedureType);
	
	@Query (value = "select distinct c.* from clinic c inner join procedures_type pt on pt.clinic_id = c.id where pt.\"name\" = ?1", nativeQuery = true)
	public List<ClinicPOJO> filterByAppointmentType (String procedureType);

	
	
	
	@Query(value = "select SUM(pt.price) \n" + 
			"from appointments a inner join procedures_type pt \n" + 
			"on a.procedure_type_id = pt.id \n" + 
			"where a.status = 'DONE' \n" + 
			"and pt.clinic_id = ?1 \n" + 
			"and a.date >= ?2 \n" + 
			"and a.date <= ?3", nativeQuery = true)
	public Float getIncome(Long clinicId, Calendar startDate, Calendar endDate);

	@Query(value = "select a.date \n" + 
			"from appointments a \n" + 
			"where a.status='DONE' \n" + 
			"and a.date >= ?1 \n" + 
			"and a.date <= ?2 \n" + 
			"order by a.date", nativeQuery = true)
	public List<Date> findAllDoneAppointmentsInADatePeriod(Calendar beginDate, Calendar endDate);
	
	//for centre admin CRUD
	@Query(value="select count(ca.*) from clinic_administrator ca where clinic_id = ?1", nativeQuery=true)
	public Integer findAdminOccurencesInClinic(Long clinic_id);
	
}
 