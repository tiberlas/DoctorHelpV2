package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.NurseCheckIfAvailableInnerDTO;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.repository.NurseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NurseServiceTest {

	@InjectMocks
	@Autowired
	private NurseService nurseService;
	
	@MockBean
	private LeaveRequestService leaveRequestService;
	
	@MockBean
	private NurseRepository nurseRepository;
	
	private Calendar duration;
	private NursePOJO nurse;
	private List<Date> reservedDates;
	
	@Before
	public void setUp() {
		duration = Calendar.getInstance();
		duration.set(2020, 0, 15, 2, 0, 0);//procedure type duration is 2 hours
		duration.set(Calendar.MILLISECOND, 0);
		
		nurse = new NursePOJO();
		nurse.setId(101L);
		nurse.setMonday(Shift.NONE);
		nurse.setTuesday(Shift.FIRST);
		nurse.setWednesday(Shift.SECOND);
		nurse.setThursday(Shift.NONE);
		nurse.setFriday(Shift.THIRD);
		nurse.setSaturday(Shift.NONE);
		nurse.setSunday(Shift.NONE);
		
		reservedDates = new ArrayList<>();
		Calendar date = Calendar.getInstance();
		date.set(2020, 1, 18, 8, 10, 0);
		reservedDates.add(date.getTime());
		date.set(2020, 1, 18, 10, 10, 0);
		reservedDates.add(date.getTime());
		date.set(2020, 1, 18, 12, 10, 0);
		reservedDates.add(date.getTime());
		date.set(2020, 1, 19, 16, 20, 0);
		reservedDates.add(date.getTime());
		date.set(2020, 1, 19, 18, 20, 0);
		reservedDates.add(date.getTime());
		date.set(2020, 1, 19, 22, 20, 0);
		reservedDates.add(date.getTime());
	}
	
	@Test
	public void baseicTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(null);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(true, requestedSchedule);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void afterSchedulesTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void beforeSchedulesTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 11, 8, 30, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 11, 8, 30, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(true, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void nextDayAndInbetweenSchedulesTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 18, 20, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void nexDayTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 19, 23, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 21, 0, 0, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void SkipNonWorkingDaysTest() {
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(null);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 15, 23, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void leaveRequestBeforeTest() {
		
		List<AbsenceInnerDTO> vacations = new ArrayList<>();
		
		Calendar vacationBegin = Calendar.getInstance();
		Calendar vacationEnd = Calendar.getInstance();
		
		vacationBegin.set(2020, 1, 10, 0, 0, 0);
		vacationEnd.set(2020, 1, 17, 0, 0, 0);
		vacations.add(new AbsenceInnerDTO(vacationBegin.getTime(), vacationEnd.getTime()));
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(vacations);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void leaveRequestBeforeTest2() {
		
		List<AbsenceInnerDTO> vacations = new ArrayList<>();
		
		Calendar vacationBegin = Calendar.getInstance();
		Calendar vacationEnd = Calendar.getInstance();
		
		vacationBegin.set(2020, 1, 10, 0, 0, 0);
		vacationEnd.set(2020, 1, 17, 0, 0, 0);
		vacations.add(new AbsenceInnerDTO(vacationBegin.getTime(), vacationEnd.getTime()));
		
		reservedDates.remove(reservedDates.size()-1);
		vacationBegin.set(2020, 1, 18, 12, 0, 0);
		vacationEnd.set(Calendar.MILLISECOND, 0);
		reservedDates.add(vacationBegin.getTime());
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(vacations);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void leaveRequestBeforeANdAfterTest() {
		
		List<AbsenceInnerDTO> vacations = new ArrayList<>();
		
		Calendar vacationBegin = Calendar.getInstance();
		Calendar vacationEnd = Calendar.getInstance();
		
		vacationBegin.set(2020, 1, 10, 0, 0, 0);
		vacationEnd.set(2020, 1, 17, 0, 0, 0);
		vacations.add(new AbsenceInnerDTO(vacationBegin.getTime(), vacationEnd.getTime()));
		
		vacationBegin.set(2020, 1, 19, 0, 0, 0);
		vacationBegin.set(2020, 1, 21, 0, 0, 0);
		vacations.add(new AbsenceInnerDTO(vacationBegin.getTime(), vacationEnd.getTime()));
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(vacations);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 19, 20, 20, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}
	
	@Test
	public void leaveRequestTest() {
		
		List<AbsenceInnerDTO> vacations = new ArrayList<>();
		
		Calendar vacationBegin = Calendar.getInstance();
		Calendar vacationEnd = Calendar.getInstance();
		
		vacationBegin.set(2020, 1, 17, 0, 0, 0);
		vacationEnd.set(2020, 1, 19, 0, 0, 0);
		vacations.add(new AbsenceInnerDTO(vacationBegin.getTime(), vacationEnd.getTime()));
		
		Mockito.when(nurseRepository.findAllReservedAppointments(101L)).thenReturn(reservedDates);
		Mockito.when(leaveRequestService.getAllNurseAbsence(101L)).thenReturn(vacations);
		
		Calendar requestedSchedule = Calendar.getInstance();
		requestedSchedule.set(2020, 1, 18, 8, 0, 0);
		requestedSchedule.set(Calendar.MILLISECOND, 0);
		
		NurseCheckIfAvailableInnerDTO actual = nurseService.checkSchedue(nurse, requestedSchedule, duration);
		
		Calendar newDate = Calendar.getInstance();
		newDate.set(2020, 1, 21, 0, 0, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		NurseCheckIfAvailableInnerDTO expected = new NurseCheckIfAvailableInnerDTO(false, newDate);
		
		assertEquals(expected.getFirstFree().getTime(), actual.getFirstFree().getTime());
		assertEquals(expected.isFree(), actual.isFree());
	}

}
