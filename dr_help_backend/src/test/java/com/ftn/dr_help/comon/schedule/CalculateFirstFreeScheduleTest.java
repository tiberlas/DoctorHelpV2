
package com.ftn.dr_help.comon.schedule;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateFirstFreeScheduleTest {

	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	@Autowired
	private DateConverter convert;
	
	@Autowired
	private WorkScheduleAdapter adapter;
	
	private DoctorPOJO doctor = new DoctorPOJO();
	private NursePOJO nurse = new NursePOJO();
	private ProceduresTypePOJO procedure = new ProceduresTypePOJO();
	private Calendar begin = Calendar.getInstance();
	private List<Date> dates = new ArrayList<Date>();
	
	@Before
	public void setUp() {
		//ProceduresTypePOJO procedure = new ProceduresTypePOJO();
		Calendar duration = Calendar.getInstance();
		duration.set(Calendar.HOUR, 1);
		duration.set(Calendar.MINUTE, 0);
		procedure.setDuration(duration.getTime());
		
		doctor.setProcedureType(procedure);
		doctor.setMonday(Shift.FIRST);
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.FIRST);
		doctor.setThursday(Shift.FIRST);
		doctor.setFriday(Shift.FIRST);
		doctor.setSaturday(Shift.NONE);
		doctor.setSunday(Shift.NONE);
		
		nurse.setMonday(Shift.FIRST);
		nurse.setTuesday(Shift.FIRST);
		nurse.setWednesday(Shift.FIRST);
		nurse.setThursday(Shift.FIRST);
		nurse.setFriday(Shift.FIRST);
		nurse.setSaturday(Shift.NONE);
		nurse.setSunday(Shift.NONE);
		
		if(!dates.isEmpty()) dates.clear();
		
		try {
			begin = convert.stringToDate("2020-01-9 07:00");//vreme je nebitno; datum je bitan
			
			Calendar day1 = convert.stringToDate("2020-01-9 08:00");
			dates.add(day1.getTime());

			Calendar day2 = convert.stringToDate("2020-01-9 09:00");
			dates.add(day2.getTime());

			Calendar day3 = convert.stringToDate("2020-01-9 11:00");
			dates.add(day3.getTime());

			Calendar day4 = convert.stringToDate("2020-01-9 12:00");
			dates.add(day4.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInMiddleShouldPass() {
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("9.1.2020. 10:0 AM", reserved);
	}
	
	@Test
	public void testOnBeginningShouldPass() {
		try {
			begin = convert.stringToDate("2020-1-8 08:00");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("8.1.2020. 8:0 AM", reserved);
	}
	
	@Test
	public void testOnBeginningNurseShouldPass() {
		try {
			begin = convert.stringToDate("2020-1-8 08:00");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(procedure.getDuration());
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromNurse(nurse, cal), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("8.1.2020. 8:0 AM", reserved);
	}
	
	@Test
	public void testOnEndShouldPass() {
		try {
			Calendar day = convert.stringToDate("2020-01-9 10:00");
			dates.add(2, day.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("9.1.2020. 1:0 PM", reserved);
	}
	
	@Test
	public void testSecondShiftShouldPass() {
		doctor.setWednesday(Shift.SECOND);
		System.out.println("SECOND");
		
		dates.clear();
		try {
			begin = convert.stringToDate("2020-01-8 08:00");
			
			Calendar day = convert.stringToDate("2020-01-8 4:00");
			day.set(Calendar.AM_PM, Calendar.PM);
			dates.add(day.getTime());
			Calendar day1 = convert.stringToDate("2020-01-8 5:00");
			day1.set(Calendar.AM_PM, Calendar.PM);
			dates.add(day1.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("8.1.2020. 6:0 PM", reserved);
	}
	
	@Test
	public void testJumpWeekendShouldPass() {
		doctor.setFriday(Shift.NONE);
		doctor.setMonday(Shift.THIRD);
		
		System.out.println("JUMP");
		
		try {
			begin = convert.stringToDate("2020-01-10 8:0");
			
			Calendar day = convert.stringToDate("2020-01-13 0:0");
			day.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day.getTime());
			Calendar day1 = convert.stringToDate("2020-01-13 1:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("13.1.2020. 2:0 AM", reserved);
	}

	@Test
	public void testJumpShiftShouldPass() {
		doctor.setMonday(Shift.THIRD);
		doctor.setTuesday(Shift.SECOND);
		
		System.out.println("JUMP");
		
		try {
			begin = convert.stringToDate("2020-01-13 1:0");
			
			Calendar day = convert.stringToDate("2020-01-13 0:0");
			day.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day.getTime());
			Calendar day1 = convert.stringToDate("2020-01-13 1:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			Calendar day2 = convert.stringToDate("2020-01-13 2:0");
			day2.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day2.getTime());
			day1 = convert.stringToDate("2020-01-13 3:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			day1 = convert.stringToDate("2020-01-13 4:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			day = convert.stringToDate("2020-01-13 5:0");
			day.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day.getTime());
			day1 = convert.stringToDate("2020-01-13 6:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			day1 = convert.stringToDate("2020-01-13 7:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			day1 = convert.stringToDate("2020-01-13 8:0");
			day1.set(Calendar.AM_PM, Calendar.AM);
			dates.add(day1.getTime());
			day1 = convert.stringToDate("2020-01-14 4:0");
			day1.set(Calendar.AM_PM, Calendar.PM);
			dates.add(day1.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, null);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("14.1.2020. 5:0 PM", reserved);
	}
	
	@Test
	public void testJumpFirstToThirdShiftShouldPass() {
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.THIRD);
		
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		begin = Calendar.getInstance();
		begin.set(2020, 0, 7, 13, 0, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		dates.clear();
		Calendar day = Calendar.getInstance();
		day.set(Calendar.MILLISECOND, 0);
		
		day.set(2020, 0, 7, 8, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 9, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 10, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 11, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 12, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 13, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 14, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 15, 0, 0);
		dates.add(day.getTime());
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);

		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 8, 0, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected.getTime(), finded.getTime());
	}
		
	@Test
	public void testJumpFirstToThirdShiftShouldPass2() {
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.THIRD);
		
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		begin = Calendar.getInstance();
		begin.set(2020, 0, 7, 13, 0, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		dates.clear();
		Calendar day = Calendar.getInstance();
		day.set(Calendar.MILLISECOND, 0);
		
		day.set(2020, 0, 7, 8, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 9, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 11, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 12, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 13, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 14, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 15, 0, 0);
		dates.add(day.getTime());
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);
		
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 8, 0, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected.getTime(), finded.getTime());
	}
	
	@Test
	public void testJumpFirstToThirdShiftShouldPass3() {
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.THIRD);
		
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		begin = Calendar.getInstance();
		begin.set(2020, 0, 7, 13, 0, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		dates.clear();
		Calendar day = Calendar.getInstance();
		day.set(Calendar.MILLISECOND, 0);
		
		day.set(2020, 0, 7, 8, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 9, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 11, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 12, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 13, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 14, 20, 0);
		dates.add(day.getTime());
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);
		
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 8, 0, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected.getTime(), finded.getTime());
	}
	
	@Test
	public void testJumpFirstToThirdShiftVacationShouldPass() {
		doctor.setTuesday(Shift.FIRST);
		doctor.setWednesday(Shift.THIRD);
		
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		begin = Calendar.getInstance();
		begin.set(2020, 0, 7, 13, 0, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		dates.clear();
		Calendar day = Calendar.getInstance();
		day.set(Calendar.MILLISECOND, 0);
		
		day.set(2020, 0, 7, 8, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 9, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 11, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 12, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 13, 0, 0);
		dates.add(day.getTime());
		day.set(2020, 0, 7, 14, 0, 0);
		dates.add(day.getTime());
		
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		begin.set(2020, 0, 7, 0, 0, 0);
		end.set(2020, 0, 10, 23, 23, 59);
		absence.add(new AbsenceInnerDTO(begin.getTime(), end.getTime()));
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);
		
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 0, 13, 8, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected.getTime(), finded.getTime());
	}
	
	@Test
	public void testAfterAbsenceShouldPass() {
		begin.set(2020, 0, 6, 10, 30, 0);
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		Calendar bbegin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		bbegin.set(2020, 0, 6, 0, 0, 0);
		end.set(2020, 0, 8, 0, 20, 0);
		absence.add(new AbsenceInnerDTO(bbegin.getTime(), end.getTime()));
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("9.1.2020. 10:0 AM", reserved);
	}
	
	@Test
	public void testBeforeAbsenceShouldPass() {
		begin.set(2020, 0, 9, 10, 30, 0);
		List<AbsenceInnerDTO> absence = new ArrayList<AbsenceInnerDTO>();
		
		Calendar bbegin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		bbegin.set(2020, 0, 10, 0, 0, 0);
		end.set(2020, 0, 15, 0, 20, 0);
		absence.add(new AbsenceInnerDTO(begin.getTime(), end.getTime()));
		
		bbegin.set(2020, 0, 9, 10, 0, 0);
		dates.add(2, bbegin.getTime());
		bbegin.set(2020, 0, 9, 13, 0, 0);
		dates.add(bbegin.getTime());
		bbegin.set(2020, 0, 9, 14, 0, 0);
		dates.add(bbegin.getTime());
		bbegin.set(2020, 0, 9, 15, 0, 0);
		dates.add(bbegin.getTime());
		bbegin.set(2020, 0, 9, 16, 0, 0);
		dates.add(bbegin.getTime());
		
		Calendar finded = calculate.findFirstScheduleForDoctor(adapter.fromDoctor(doctor), begin, dates, absence);
		String reserved = convert.dateAndTimeToString(finded);
		if(finded.get(Calendar.AM_PM) == Calendar.AM) {
			reserved += " AM";
		} else {
			reserved += " PM";
		}
		
		assertEquals("16.1.2020. 8:0 AM", reserved);
	}
}
