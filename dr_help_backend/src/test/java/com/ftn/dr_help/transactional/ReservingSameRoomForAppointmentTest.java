package com.ftn.dr_help.transactional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.dto.AppointmentForSchedulingDTO;
import com.ftn.dr_help.dto.AppointmentInternalBlessedDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.RoomRepository;
import com.ftn.dr_help.service.AppointmentBlessingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ReservingSameRoomForAppointmentTest {

	@Autowired
	private AppointmentBlessingService blessingService;
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Autowired
	private RoomRepository roomRepo;
	
	private AppointmentForSchedulingDTO requested;
	
	@Before
	public void setUp() {
		requested = new AppointmentForSchedulingDTO(
							"happymeal@gmail.com", 
							"j.milinkovic@gmail", 
							2l, 
							2l, 
							"2020-02-04 15:30:00", 
							22l);
	}
	
	@Transactional()
	private void testOptimisticLock() {
		AppointmentPOJO appointment = appointmentRepo.getOne(22l);
		
		RoomPOJO room = roomRepo.getOne(3l);
		appointment.setRoom(room);
		appointmentRepo.save(appointment);
		
		AppointmentInternalBlessedDTO b1 = blessingService.blessing(requested, "admin@admin");
		AppointmentPOJO appointment2 = appointmentRepo.getOne(22l);
		System.out.println("SOBA JEafaf "+appointment2.getRoom().getId());
		//assertEquals("REFFUSED", b1.getBlessingLvl());
	}
	
	@Test
	public void test() {
		AppointmentInternalBlessedDTO b1 = blessingService.blessing(requested, "admin@admin");
		AppointmentInternalBlessedDTO b2 = blessingService.blessing(requested, "mikiveliki@yahoo.com");
	
		System.out.println("lvl1"+b1.getBlessingLvl());
		System.out.println("lvl2"+b2.getBlessingLvl());
	}
	
}
