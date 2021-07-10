
package com.ftn.dr_help.comon.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.DayEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NiceScheduleBeginningTest {

	@Autowired
	private NiceScheduleBeginning schedule;
	
	@Autowired
	private WorkScheduleAdapter adapter;
	
	private DoctorPOJO doctor = new DoctorPOJO();
	
	private Calendar cal = Calendar.getInstance();
	
	@Before
	public void setUp() {
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 0); //januar
		
		doctor.setMonday(Shift.FIRST);
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.FIRST);
		doctor.setThursday(Shift.FIRST);
		doctor.setFriday(Shift.FIRST);
		doctor.setSaturday(Shift.NONE);
		doctor.setSunday(Shift.NONE);
		
		Calendar duration = Calendar.getInstance();
		duration.set(Calendar.HOUR, 1);
		ProceduresTypePOJO procedure = new ProceduresTypePOJO();
		procedure.setDuration(duration.getTime());
		doctor.setProcedureType(procedure);
	}
	
	@Test
	public void testShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 9); //thursday
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 9 && hours == 8 && minutes == 0 && m == Calendar.AM);
	}
	
	@Test
	public void testNextWeekShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 11); //saturday
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 13 && hours == 8 && minutes == 0 && m == Calendar.AM);
	}
	
	@Test
	public void testNextDayShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 9); //thursday
		doctor.setThursday(Shift.NONE);
		doctor.setFriday(Shift.FIRST);
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 10 && hours == 8 && minutes == 0 && m == Calendar.AM);
	}
	
	@Test
	public void testSecondShiftShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 13); //monday
		doctor.setMonday(Shift.SECOND);
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 13 && hours == 4 && minutes == 0 && m == Calendar.PM);
	}

	@Test
	public void testSecondShiftAgainShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 15); //wednesday
		doctor.setWednesday(Shift.NONE);
		doctor.setThursday(Shift.SECOND);
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 16 && hours == 4 && minutes == 0 && m == Calendar.PM);
	}
	
	@Test
	public void testThirdShiftShouldPass() {
		cal.set(Calendar.DAY_OF_MONTH, 14); //tuesday
		doctor.setTuesday(Shift.THIRD);
		
		schedule.setNiceScheduleBeginning(adapter.fromDoctor(doctor), cal);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.AM_PM);
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		
		assertTrue(day == 14 && hours == 0 && minutes == 0 && m == Calendar.AM);
	}
	
	@Test
	public void testOperationFindFreeDate() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 0, 0, 0);//thusday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.MONDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.WEDNESDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 20, 8, 0, 0);//monday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationFindFreeDate2() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 0, 0, 0);//thursday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SATURDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 17, 8, 0, 0);//friday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationFindFreeDate3() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 0, 0, 0);//thursday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SUNDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 19, 8, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationRequestInDateSecondShift() {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.MONTH, Calendar.JANUARY);
		begin.set(2020, 0, 16, 0, 0, 0);//thusday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.MONDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.THURSDAY, Shift.SECOND));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SATURDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 16, 16, 0, 0);//thusday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationRequestInDateFirstShift() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 0, 0, 0);//thusday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.THURSDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SUNDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 16, 8, 0, 0);//thusday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationRequestInSchedule() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 10, 32, 0);//thusday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.THURSDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SUNDAY, Shift.FIRST));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 16, 10, 32, 0);//thusday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
	
	@Test
	public void testOperationFindInThirdShift() {
		Calendar begin = Calendar.getInstance();
		begin.set(2020, 0, 16, 0, 0, 0);//thusday
		begin.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST));
		equalShifts.add(new EqualDoctorShifts(DayEnum.SUNDAY, Shift.THIRD));
		
		Calendar finded = schedule.setNiceOperationBegin(equalShifts, begin);
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 19, 0, 0, 0);//sunday
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected, finded);
	}
}

