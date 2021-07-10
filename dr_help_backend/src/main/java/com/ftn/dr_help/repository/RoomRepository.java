package com.ftn.dr_help.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.RoomPOJO;

@Repository
public interface RoomRepository extends JpaRepository <RoomPOJO, Long>{ 

	List<RoomPOJO> findAllByClinic_id(Long id);// nepotrebno namesti join
	
	Optional<RoomPOJO> findByIdAndClinic_id(Long id, Long clinic_id);
	Optional<RoomPOJO> findOneByName(String name);
	Optional<RoomPOJO> findOneByNumber(int number);
	
	RoomPOJO findOneById(Long roomId);
	
	@Query(value = "select distinct r.* " + 
			"from room r " + 
			"inner join appointments a " + 
			"on r.id = a.room_id " + 
			"where ((a.status = 'APPROVED' or a.status = 'BLESSED') and a.deleted = false) " + 
			"and r.deleted = false " + 
			"union " + 
			"select distinct r.* " + 
			"from room r " + 
			"inner join operations o " + 
			"on r.id = o.room_id " + 
			"where (o.status = 'APPROVED' and o.deleted = false) " + 
			"and r.deleted = false", nativeQuery = true)
	List<RoomPOJO> getAllReservedRooms();

	@Query(value = "select r.* \n" + 
			"from room r inner join clinic_administrator ca \n" + 
			"on ca.clinic_id = r.clinic_id \n" + 
			"where r.proceduras_types_id = ?2 \n" + 
			"and r.deleted = false \n" + 
			"and ca.email = ?1", nativeQuery = true)
	List<RoomPOJO> findAllWithType(String adminEmail, Long proceduretypeId);
	
	@Query(value = "select * from room r where r.clinic_id= ?1 and r.proceduras_types_id= ?2 and r.deleted = false", nativeQuery = true)
	public List<RoomPOJO> getAllRoomFromClinicWithProcedure(Long clinicId, Long procedureId);
	
	
}
