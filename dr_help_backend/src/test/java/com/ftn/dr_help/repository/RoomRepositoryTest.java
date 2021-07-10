package com.ftn.dr_help.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;



@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class RoomRepositoryTest {

	@Autowired
	private RoomRepository RoomRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleted() {
		List<RoomPOJO> before;
		List<RoomPOJO> after;
		
		before = RoomRepository.getAllReservedRooms();
		
		before.get(0).setDeleted(true);
		entityManager.merge(before.get(0));
		
		after = RoomRepository.getAllReservedRooms();
		
		assertEquals(before.size()-1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testStatus() {
		List<RoomPOJO> before;
		List<RoomPOJO> after;
		
		before = RoomRepository.getAllReservedRooms();
		
		for(AppointmentPOJO appointment : before.get(0).getAppointments()) {
			appointment.setStatus(AppointmentStateEnum.DONE);
		}
		
		entityManager.merge(before.get(0));
		
		after = RoomRepository.getAllReservedRooms();
		
		assertEquals(before.size()-1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllWithType() {
		List<RoomPOJO> before;
		List<RoomPOJO> after;
		
		before = RoomRepository.findAllWithType("admin@admin", 2l);
		
		before.get(0).setDeleted(true);
		entityManager.merge(before.get(0));
		
		after = RoomRepository.findAllWithType("admin@admin", 2l);
		
		assertEquals(before.size()-1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllWithTypeFromClinic() {
		List<RoomPOJO> before;
		List<RoomPOJO> after;
		
		before = RoomRepository.getAllRoomFromClinicWithProcedure(1l, 2l);
		
		before.get(0).setDeleted(true);
		entityManager.merge(before.get(0));
		
		after = RoomRepository.getAllRoomFromClinicWithProcedure(1l, 2l);
		
		assertEquals(before.size()-1, after.size());
	}
	
	

}
