package com.ftn.dr_help.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.model.pojo.AppointmentPOJO;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentPOJO, Long>{

	AppointmentPOJO findOneByExaminationReportId (Long examinationReportId);

	
	AppointmentPOJO findOneById(Long id);
	
	@Query(value = "select distinct a.* from appointments a where a.doctor_id = ?1 and a.status != 'REQUESTED' and a.deleted = false", nativeQuery = true)
	public List<AppointmentPOJO> findDoctorAppointments(Long doctor_id);
	
	@Query(value="select a.* from appointments a where a.doctor_id = ?1 and a.patient_id = ?2 and a.status = 'APPROVED' and a.deleted=false", nativeQuery = true)
	public List<AppointmentPOJO> findApprovedDoctorAppointmentsForPatientWithId(Long doctor_id, Long patient_id);
	
	@Query(value="select distinct a.* from appointments a where a.patient_id = ?1 and a.status = 'DONE' and a.deleted=false", nativeQuery=true)
	List<AppointmentPOJO> findDoneAppointmentsForPatientWithId(Long patient_id);

	@Query(value = "select a.date from appointments a "+
					"where (a.status = 'APPROVED' "+
					"or a.status = 'BLESSED') "+
					"and a.deleted = FALSE "+
					"and a.room_id = ?1 order by a.date", nativeQuery = true)
	public List<Date> findScheduledDatesOfRoom(Long roomId);
	
	@Query(value="select a.* from appointments a where a.nurse_id = ?1 and a.status != 'REQUESTED' and a.status != 'DOCTOR_REQUESTED_APPOINTMENT' and a.deleted = false", nativeQuery = true)
	public List<AppointmentPOJO> findNurseAppointments(Long nurse_id);

	@Query (value = "select distinct a.* from ((clinic c inner join doctors d on c.id = d.clinic_id) inner join appointments a on d.id = a.doctor_id) inner join procedures_type pt on pt.id = d.procedure_type_id where c.id = ?1 and a.\"date\" between ?2 and ?3 and a.deleted = false and pt.\"name\" = ?4", nativeQuery = true)
	List<AppointmentPOJO> getClinicsAppointments (Long clinicId, Calendar calendarMin, Calendar calendarMax, String procedureName);
	
	@Query (value = "select * from appointments a where doctor_id = ?1 and deleted = false and date between ?2 and ?3", nativeQuery = true)
	List<AppointmentPOJO> getDoctorsAppointments (Long doctorId, Calendar calendarMin, Calendar calendarMax); 

	@Query (value = "select * from appointments a where patient_id = ?1 and status = 'DONE' and deleted = false and doctor_id = ?2", nativeQuery = true)
	public List<AppointmentPOJO> getPatientsPastAppointments (Long patientId, Long doctorId);
	
	@Query (value = "select a.* from appointments a inner join room r on a.room_id = r.id where a.deleted = false and status = 'DONE' and a.patient_id = ?1 and r.clinic_id = ?2", nativeQuery = true)
	public List<AppointmentPOJO> getPatientsPastAppointmentsForClinic (Long patientId, Long clinicId);
	
	@Query (value = "select * from appointments a where a.patient_id = ?1 and a.status in ('REQUESTED', 'APPROVED', 'DOCTOR_REQUESTED_APPOINTMENT', 'BLESSED') and a.deleted = false", nativeQuery = true)
	public List<AppointmentPOJO> getPatientsPendingAppointments (Long patientId);
	
	@Modifying
	@Query (value = "update appointments set deleted = true where id = ?1", nativeQuery = true)
	public void deleteAppointment (Long appointmentId);
	
	@Modifying
	@Query (value = "update appointments set patient_id = null, status = 'AVAILABLE' where id = ?1", nativeQuery = true)
	public void cancelAppointment (Long appointmentId);
	
	
	@Query( value = "select distinct a.* from doctors d inner join doctor_requested dr on (d.id = dr.doctor_id) inner join appointments a on (a.id = dr.appointment_id) where d.email= ?1 and a.id= ?2", nativeQuery = true)
	AppointmentPOJO getRequestedAppointment(String doctorEmail, Long id);

	@Query(value = "select a.* from \n" + 
			"	appointments a inner join doctors d on d.id = a.doctor_id \n" + 
			"	inner join clinic_administrator ca on ca.clinic_id = d.clinic_id\n" + 
			"	where (a.status = 'REQUESTED' or a.status = 'DOCTOR_REQUESTED_APPOINTMENT') and (ca.email = ?1) order by a.id", nativeQuery = true)
	List<AppointmentPOJO> getAllRequests(String clinicAdminMail);

	@Query(value = "select a.* from appointments a \n" +  
			"where (a.status = 'REQUESTED' or a.status = 'DOCTOR_REQUESTED_APPOINTMENT') order by a.id", nativeQuery = true)
	List<AppointmentPOJO> getAllRequests();
	
	@Query(value = "select a.* from appointments a \n" + 
			"where a.status = 'AVAILABLE' \n" + 
			"and a.deleted = false", nativeQuery = true)
	List<AppointmentPOJO> findAllPredefined();
	
	/* -------------------za leave request medicinske sestre */
	@Query(value="select a.* from appointments a where a.nurse_id = ?1 and a.status != 'DONE' and a.status != 'REQUESTED' and a.status != 'DOCTOR_REQUESTED_APPOINTMENT' and a.deleted = false", nativeQuery=true)
	public List<AppointmentPOJO> findAvailableOrApprovedNurseAppointments(Long nurse_id);
	/* -------------------za leave request doktora */
	@Query(value="select a.* from appointments a where a.doctor_id = ?1 and a.status != 'DONE' and a.status != 'REQUESTED' and a.status != 'DOCTOR_REQUESTED_APPOINTMENT' and a.deleted = false", nativeQuery=true)
	public List<AppointmentPOJO> findAvailableOrApprovedDoctorAppointments(Long doctor_id);
	

	//JEBEN POSAO DECKO MOJ
	@Query(value="select a.* from appointments a where a.nurse_id=?1 " +
		 "intersect " +
		 "select a2.* from appointments a2 where a2.status = 'APPROVED' or a2.status = 'AVAILABLE' " +
		 "intersect " +
		 "select a3.* from appointments a3 where a3.date > ?2 and a3.date <= ?3 and a3.deleted=false", nativeQuery=true)
	public List<AppointmentPOJO> getNurseAppointmentsBetweenRequestDates(Long nurse_id, Date startDate, Date endDate);
	
	@Query(value="select a.* from appointments a where a.doctor_id=?1 " +
			 "intersect " +
			 "select a2.* from appointments a2 where a2.status = 'APPROVED' or a2.status = 'AVAILABLE' " +
			 "intersect " +
			 "select a3.* from appointments a3 where a3.date > ?2 and a3.date <= ?3 and a3.deleted=false", nativeQuery=true)
		public List<AppointmentPOJO> getDoctorAppointmentsBetweenRequestDates(Long nurse_id, Date startDate, Date endDate);
	
	
	@Transactional
	@Modifying
	@Query(value="update appointments set deleted = true where (status = 'APPROVED' or status = 'AVAILABLE') and date <= ?1", nativeQuery=true)
	public void deleteAppointmentsInThePast(Date now);

	@Query (value = "select * from appointments a where a.status = 'AVAILABLE'", nativeQuery = true)
	public List<AppointmentPOJO> getAllPredefinedAppointments();
	
	@Modifying
	@Query (value = "update appointments set patient_id = ?2, status = 'APPROVED' where id = ?1", nativeQuery = true)
	public void reserveAppointment (Long appointmentId, Long patinentId);
	

	@Query(value = "select a.* from room r " + 
			"inner join appointments a " + 
			"on a.room_id = r.id " + 
			"where (a.status = 'APPROVED' or a.status = 'BLESSED') " + 
			"and r.deleted = false " + 
			"and a.deleted = false " + 
			"and r.id = ?1", nativeQuery = true)
	public List<AppointmentPOJO> findAllScheduledAppointmentsInRoom(Long roomId);

	@Modifying
	@Query (value = "update appointments set status = 'APPROVED' where id = ?1", nativeQuery = true)
	public void confirmAppointment (Long appointmentId);
	
}
