package com.ftn.dr_help.comon.schedule;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkDayTest {

	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	private MedicalStaffWorkSchedularPOJO doctor;
	
	@Test
	public void test() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 13, 20, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.FIRST, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		assertEquals(schedule, actual);
	}
	
	@Test
	public void test2() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 8, 0, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.FIRST, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		assertEquals(schedule, actual);
	}
	
	@Test
	public void test3() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 16, 0, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		assertEquals(schedule, actual);
	}
	
	@Test
	public void test4() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 16, 1, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.FIRST, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 20, 8, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test5() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 16, 1, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.FIRST, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 20, 8, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test6() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 16, 10, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		assertEquals(schedule, actual);
	}
	
	@Test
	public void test7() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 15, 59, 59);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		Calendar exact = Calendar.getInstance();
		exact.set(2020, 0, 13, 16, 0, 0);
		exact.set(Calendar.MILLISECOND, 0);
		
		assertEquals(exact.getTime(), actual.getTime());
	}
	
	@Test
	public void test8() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 11, 15, 59, 59);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		Calendar exact = Calendar.getInstance();
		exact.set(2020, 0, 13, 16, 0, 0);
		exact.set(Calendar.MILLISECOND, 0);
		
		assertEquals(exact.getTime(), actual.getTime());
	}
	
	@Test
	public void test9() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 10, 1, 0, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		Calendar exact = Calendar.getInstance();
		exact.set(2020, 0, 13, 16, 0, 0);
		exact.set(Calendar.MILLISECOND, 0);
		
		assertEquals(exact.getTime(), actual.getTime());
	}
	
	@Test
	public void test10() {
		Calendar schedule = Calendar.getInstance();
		schedule.set(2020, 0, 13, 20, 0, 0);
		schedule.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 5, 2, 1, 0, 0);//1h
		doctor = new MedicalStaffWorkSchedularPOJO(
				Shift.SECOND, //MONDAY 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				Shift.NONE, 
				duration);
		
		Calendar actual = calculate.setWorkingDay(doctor, schedule, null);
		
		assertEquals(schedule.getTime(), actual.getTime());
	}

}
