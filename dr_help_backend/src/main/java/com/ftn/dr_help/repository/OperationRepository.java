package com.ftn.dr_help.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.OperationPOJO;

@Repository
public interface OperationRepository extends JpaRepository<OperationPOJO, Long> {

	OperationPOJO findOneById(Long id);
	
	@Query(value = "select o.* from operations o inner join doctors d on (d.id = o.requested_doctor_id) where o.deleted = false and d.email= ?1 and o.status <> 'DONE'", nativeQuery = true)
	public List<OperationPOJO> getAllOperationRequests(String DoctorEmail);
	
	@Query(value = "select o.* from operations o \n" + 
			"inner join procedures_type pt \n" + 
			"on o.operation_type_id = pt.id \n" + 
			"inner join clinic_administrator ca \n" + 
			"on pt.clinic_id = ca.clinic_id \n" + 
			"where ca.email = ?1 \n" + 
			"and o.deleted = false \n" + 
			"and o.status = 'REQUESTED'", nativeQuery = true)
	public List<OperationPOJO> getAllOperationRequestsForAdmin(String ClinicAdminEmail);
	
	@Query(value="select o.* from operations o where o.first_doctor_id = ?1 or o.second_doctor_id = ?1 or o.third_doctor_id = ?1 " +
			 "intersect " +
			 "select o2.* from operations o2 where o2.status = 'APPROVED' and o2.deleted=false and o2.date > ?2 and o2.date < ?3", nativeQuery=true)
	public List<OperationPOJO> getDoctorOperationsBetweenDates(Long doctor_id, Date startDate, Date endDate);
	
	
	//for doctor work schedule
	@Query(value="select o.* from operations o " +
"where (o.first_doctor_id = ?1 or o.second_doctor_id = ?1 or o.third_doctor_id = ?1) and o.status = 'APPROVED' and o.deleted=false", nativeQuery=true)
	public List<OperationPOJO> getDoctorOperations(Long doctor_id);
	
	
	@Query(value = "select o.* from room r " + 
			"inner join operations o " + 
			"on o.room_id = r.id " + 
			"where o.status = 'APPROVED' " + 
			"and o.deleted = false " + 
			"and r.deleted = false " + 
			"and r.id = ?1", nativeQuery = true)
	public List<OperationPOJO> findAllScheduledOperationsInRoom(Long roomId);
	
	@Query(value = "select o.* from operations o where o.status = 'REQUESTED' and o.deleted = false order by o.id;", nativeQuery = true)
	public List<OperationPOJO> getAllOperationRequests();

	@Query(value="select * from operations o where status = 'APPROVED' and patient_id = ?1 and date < ?2", nativeQuery = true)
	public List<OperationPOJO> getPatientsDoneOperations (Long patientId, Calendar date);
	
}
