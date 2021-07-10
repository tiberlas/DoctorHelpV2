package com.ftn.dr_help.comon.automatically_reserving;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.RoomRepository;
import com.ftn.dr_help.service.RoomService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CheckRoomsTest {

	@InjectMocks
	@Autowired
	private CheckRooms checkRooms;
	
	@MockBean
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	private List<RoomPOJO> rooms;
	
	@Before
	public void setUp() {
		rooms = new ArrayList<>();
		
		RoomPOJO r1 = roomRepository.findOneById(1l);
		RoomPOJO r2 = roomRepository.findOneById(2l);
		RoomPOJO r3 = roomRepository.findOneById(3l);
				
		rooms.add(r1);
		rooms.add(r2);
		rooms.add(r3);
	}
	
	@Test
	public void testOK() {
		Calendar requestingDate = Calendar.getInstance();
		requestingDate.set(2020, 2, 8, 0, 0);
		requestingDate.set(Calendar.MILLISECOND, 0);
		
		Mockito.when(roomService.getAllRoomFromClinicWithProcedure(1l, 2l)).thenReturn(rooms);
		
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(0), requestingDate)).thenReturn(requestingDate);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(1), requestingDate)).thenReturn(requestingDate);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(2), requestingDate)).thenReturn(requestingDate);
		
		FreeRoomWithDate actual = checkRooms.findFirstFreeRoom(requestingDate, 1l, 2l);
		
		assertEquals(rooms.get(0), actual.getFreeRoom());
	}
	
	@Test
	public void testOkWithOterDates() {
		Calendar requestingDate = Calendar.getInstance();
		requestingDate.set(2020, 2, 8, 0, 0);
		requestingDate.set(Calendar.MILLISECOND, 0);
		
		Calendar date2 = (Calendar) requestingDate.clone();
		date2.add(Calendar.DAY_OF_MONTH, 1);
		Calendar date3 = (Calendar) requestingDate.clone();
		date3.add(Calendar.DAY_OF_MONTH, 2);
		
		Mockito.when(roomService.getAllRoomFromClinicWithProcedure(1l, 2l)).thenReturn(rooms);
		
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(0), requestingDate)).thenReturn(requestingDate);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(1), requestingDate)).thenReturn(date2);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(2), requestingDate)).thenReturn(date3);
		
		FreeRoomWithDate actual = checkRooms.findFirstFreeRoom(requestingDate, 1l, 2l);
		
		assertEquals(rooms.get(0), actual.getFreeRoom());
	}
	
	@Test
	public void testDifferentDate() {
		Calendar requestingDate = Calendar.getInstance();
		requestingDate.set(2020, 2, 8, 0, 0);
		requestingDate.set(Calendar.MILLISECOND, 0);
		
		Calendar date2 = (Calendar) requestingDate.clone();
		date2.add(Calendar.DAY_OF_MONTH, 1);
		Calendar date3 = (Calendar) requestingDate.clone();
		date3.add(Calendar.DAY_OF_MONTH, 2);
		
		Mockito.when(roomService.getAllRoomFromClinicWithProcedure(1l, 2l)).thenReturn(rooms);
		
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(0), requestingDate)).thenReturn(date3);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(1), requestingDate)).thenReturn(date2);
		Mockito.when(roomService.findFirstFreeScheduleFromDateInRawformat(rooms.get(2), requestingDate)).thenReturn(date3);
		
		FreeRoomWithDate actual = checkRooms.findFirstFreeRoom(requestingDate, 1l, 2l);
		
		assertEquals(rooms.get(1), actual.getFreeRoom());
		assertEquals(date2.getTime(), actual.getRecomendedDate().getTime());
	}

}
