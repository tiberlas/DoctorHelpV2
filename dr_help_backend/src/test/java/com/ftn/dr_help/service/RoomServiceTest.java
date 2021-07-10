
package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class RoomServiceTest {
	
	@InjectMocks
	@Autowired
	private RoomService roomService;
	
	@MockBean
	private AppointmentRepository appointmentRepository;
	
	private RoomPOJO room = new RoomPOJO();
	
	private List<Date> schedules = new ArrayList<>();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		Calendar duration = Calendar.getInstance();
		duration.set(Calendar.HOUR, 2);
		duration.set(Calendar.MINUTE, 0);
		ProceduresTypePOJO procedure = new ProceduresTypePOJO();
		procedure.setDuration(duration.getTime());
		
		room.setId(101l);
		room.setProcedurasTypes(procedure);
		
		setSchedulas();
	}
	
	private void setSchedulas() {
		
		Calendar c0 = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		Calendar c4 = Calendar.getInstance();
		Calendar c5 = Calendar.getInstance();
		Calendar c6 = Calendar.getInstance();
		Calendar c7 = Calendar.getInstance();
		Calendar c8 = Calendar.getInstance();
		Calendar c9 = Calendar.getInstance();
		
		c0.set(2020, 0, 13, 8, 10);
		c1.set(2020, 0, 13, 12, 10);
		c2.set(2020, 0, 14, 2, 10);
		c3.set(2020, 0, 14, 8, 10);
		c4.set(2020, 0, 14, 13, 10);
		c5.set(2020, 0, 15, 18, 10);
		c6.set(2020, 0, 16, 16, 10);
		c7.set(2020, 0, 16, 17, 10);
		c8.set(2020, 0, 16, 19, 20);
		c9.set(2020, 0, 16, 22, 00);

		c0.clear(Calendar.SECOND);
		c0.clear(Calendar.MILLISECOND);
		c1.clear(Calendar.SECOND);
		c1.clear(Calendar.MILLISECOND);
		c2.clear(Calendar.SECOND);
		c2.clear(Calendar.MILLISECOND);
		c3.clear(Calendar.SECOND);
		c3.clear(Calendar.MILLISECOND);
		c4.clear(Calendar.SECOND);
		c4.clear(Calendar.MILLISECOND);
		c5.clear(Calendar.SECOND);
		c5.clear(Calendar.MILLISECOND);
		c6.clear(Calendar.SECOND);
		c6.clear(Calendar.MILLISECOND);
		c7.clear(Calendar.SECOND);
		c7.clear(Calendar.MILLISECOND);
		c8.clear(Calendar.SECOND);
		c8.clear(Calendar.MILLISECOND);
		c9.clear(Calendar.SECOND);
		c9.clear(Calendar.MILLISECOND);
		
		schedules.add(c0.getTime());
		schedules.add(c1.getTime());
		schedules.add(c2.getTime());
		schedules.add(c3.getTime());
		schedules.add(c4.getTime());
		schedules.add(c5.getTime());
		schedules.add(c6.getTime());
		schedules.add(c7.getTime());
		schedules.add(c8.getTime());
		schedules.add(c9.getTime());
	}

	
	@Test
	@Transactional
	public void findFirstFreeInDateTest() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 7, 5, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/13/2020 10:10 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeInDateTest2() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 8, 20, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/13/2020 10:10 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeInDateTest3() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 11, 0, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/13/2020 02:10 PM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeAfterTest() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 16, 36, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/13/2020 04:36 PM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeAfterTest2() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 15, 18, 36, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/15/2020 08:10 PM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeBeforeTest() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 6, 2, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/13/2020 06:02 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void findFirstFreeBeforeTest2() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 15, 6, 2, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/15/2020 06:02 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void testNextDay() {
		try {
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 16, 22, 25, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/17/2020 00:00 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void testNextDay2() {
		try {
			Calendar next = Calendar.getInstance();
			next.set(2020, 0, 17, 1, 32, 0);
			next.set(Calendar.MILLISECOND, 0);
			schedules.add(next.getTime());
			
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 16, 22, 25, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/17/2020 03:32 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void testNextDay3() {
		try {
			Calendar next = Calendar.getInstance();
			next.set(2020, 0, 19, 1, 32, 0);
			next.set(Calendar.MILLISECOND, 0);
			schedules.add(next.getTime());
			
			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 16, 22, 25, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/17/2020 00:00 AM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	@Transactional
	public void testBefore() {
		try {

			Mockito.when(this.appointmentRepository.findScheduledDatesOfRoom(101l)).thenReturn(schedules);
			
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 2, 22, 25, 0);
			begin.set(Calendar.MILLISECOND, 0);
			String actual = roomService.findFirstFreeScheduleFromDate(room, begin);
			String expected = "01/02/2020 10:25 PM";
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
