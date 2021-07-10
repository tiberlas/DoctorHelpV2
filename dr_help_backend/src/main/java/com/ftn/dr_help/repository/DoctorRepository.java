package com.ftn.dr_help.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.DoctorPOJO;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorPOJO, Long> {
	
	
	@Query (value = "select * from doctors d where d.id = ?1", nativeQuery = true)
	public DoctorPOJO findOne(Long id);
	
	public List<DoctorPOJO> findAllByClinic_id(Long id);
	
	@Query (value = "select * from doctors d where d.email = ?1 and d.deleted = false", nativeQuery = true)
	public DoctorPOJO findOneByEmail (String email);
	
	public Optional<DoctorPOJO> findById (Long id);

	@Query (value = "select d.* from doctors d inner join procedures_type pt on d.procedure_type_id = pt.id where d.clinic_id = ?1 and pt.name = ?2", nativeQuery = true)
	public List<DoctorPOJO> filterByClinicAndProcedureType (Long clinicId, String procedureType);
	

	//pronalazenje istorije pregleda kod prosledjenog lekara i prosledjenog pacijenta, 
	//sluzi za prikaz health-recorda na front endu za lekara ili medicinsku sestru
	@Query(value="select distinct count(a.*) from appointments a where a.doctor_id = ?1 and a.patient_id = ?2 and a.status = 'DONE' and deleted = false", nativeQuery=true)
	public Integer findDoneAppointmentForDoctorCount(Long doctor_id, Long patient_id);
	
	public DoctorPOJO findOneById(Long id);

	@Query(value = "select count(a.doctor_id) from appointments a where a.status <> 'DONE' and a.deleted = false and a.doctor_id = ?1 group by a.doctor_id", nativeQuery = true)
	public Long getDoctorsAppointmentsCount(Long doctorId);
	
	@Query(value = "select a.date from appointments a "+
					"where (a.status = 'APPROVED' "+
					"or a.status = 'BLESSED') "+
					"and a.deleted = FALSE "+
					"and a.doctor_id = ?1 "+
					"order by a.date", nativeQuery = true)
	public List<Date> findAllReservedAppointments(Long doctorId);
	
	@Query(value = "select o.date from operations o where (o.first_doctor_id = ?1 or o.second_doctor_id = ?1 or third_doctor_id = ?1) and o.deleted = FALSE and o.status <> 'DONE'" , nativeQuery = true)
	public List<Date> findAllReservedOperations(Long doctorId);
	
	@Query(value = "select d.* from doctors d where d.deleted <> TRUE and d.procedure_type_id = ?1", nativeQuery = true)
	public List<DoctorPOJO> findAllDoctorsWihtSpetialization(long procedureTypeId);

	@Query(value = "select ca.email from clinic_administrator ca inner join doctors d on (d.clinic_id = ca.clinic_id) where d.email = ?1", nativeQuery = true)
	public List<String> findAllClinicAdminMails(String drMail);
	
	@Query(value = "select * from doctors d where d.clinic_id = ?1 and d.procedure_type_id = ?2", nativeQuery = true)
	public List<DoctorPOJO> getAllDoctorsFromClinicWithSpecialization(Long clinicId, Long procedureId);

	@Query(value ="select avg(dr.rating) from doctor_reviewpojo dr where dr.doctor_id = ?1", nativeQuery = true)
	public Float getAverageRatingFor(Long doctorId);
}