
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
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateFirstFreeOperationScheduleTest {

	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	@Autowired
	private DateConverter convertor;
	
	private DoctorPOJO dr0 = new DoctorPOJO();
	private DoctorPOJO dr1 = new DoctorPOJO();
	private DoctorPOJO dr2 = new DoctorPOJO();
	
	private List<Date> dates0;
	private List<Date> dates1;
	private List<Date> dates2;
	
	@Before
	public void setUp() {
        Calendar duration = Calendar.getInstance();
        duration.set(2000, 2, 15, 2, 0);
        
        ProceduresTypePOJO type = new ProceduresTypePOJO();
        type.setId(13l);
        type.setDuration(duration.getTime());
        type.setDeleted(false);
        type.setOperation(false);
        
        getOperations();
        
        dr0.setId(0l);
        dr0.setDeleted(false);
        dr0.setMonday(Shift.FIRST);
        dr0.setTuesday(Shift.FIRST);
        dr0.setWednesday(Shift.NONE);
        dr0.setThursday(Shift.SECOND);
        dr0.setFriday(Shift.SECOND);
        dr0.setSaturday(Shift.NONE);
        dr0.setSunday(Shift.NONE);
        dr0.setProcedureType(type);
        
        dr2.setId(2l);
        dr2.setDeleted(false);
        dr2.setMonday(Shift.NONE);
        dr2.setTuesday(Shift.THIRD);
        dr2.setWednesday(Shift.SECOND);
        dr2.setThursday(Shift.SECOND);
        dr2.setFriday(Shift.SECOND);
        dr2.setSaturday(Shift.NONE);
        dr2.setSunday(Shift.NONE);
        dr2.setProcedureType(type);
        
        dr1.setId(1l);
        dr1.setDeleted(false);
        dr1.setMonday(Shift.NONE);
        dr1.setTuesday(Shift.FIRST);
        dr1.setWednesday(Shift.NONE);
        dr1.setThursday(Shift.SECOND);
        dr1.setFriday(Shift.SECOND);
        dr1.setSaturday(Shift.NONE);
        dr1.setSunday(Shift.NONE);
        dr1.setProcedureType(type);
	}
	
	private void getOperations() {
		dates0 = new ArrayList<>();
		dates1 = new ArrayList<>();
		dates2 = new ArrayList<>();
		
		Calendar c00 = Calendar.getInstance();
		Calendar c01 = Calendar.getInstance();
		Calendar c02 = Calendar.getInstance();
		Calendar c03 = Calendar.getInstance();
		Calendar c10 = Calendar.getInstance();
		Calendar c11 = Calendar.getInstance();
		Calendar c12 = Calendar.getInstance();
		Calendar c20 = Calendar.getInstance();
		Calendar c21 = Calendar.getInstance();
		Calendar c22 = Calendar.getInstance();
		Calendar c23 = Calendar.getInstance();
		
		c00.set(2020, 0, 13, 8, 10);
		c01.set(2020, 0, 13, 12, 10);
		c02.set(2020, 0, 16, 17, 10);
		c03.set(2020, 0, 16, 20, 0); //22:00
		c10.set(2020, 0, 14, 8, 10);
		c11.set(2020, 0, 14, 13, 10);
		c12.set(2020, 0, 16, 16, 10);//18:10
		c20.set(2020, 0, 14, 2, 10);
		c21.set(2020, 0, 15, 18, 10);
		c22.set(2020, 0, 16, 16, 10);
		c23.set(2020, 0, 16, 19, 20);//21:20

		c00.clear(Calendar.SECOND);
		c00.clear(Calendar.MILLISECOND);
		c01.clear(Calendar.SECOND);
		c01.clear(Calendar.MILLISECOND);
		c02.clear(Calendar.SECOND);
		c02.clear(Calendar.MILLISECOND);
		c03.clear(Calendar.SECOND);
		c03.clear(Calendar.MILLISECOND);
		c10.clear(Calendar.SECOND);
		c10.clear(Calendar.MILLISECOND);
		c11.clear(Calendar.SECOND);
		c11.clear(Calendar.MILLISECOND);
		c12.clear(Calendar.SECOND);
		c12.clear(Calendar.MILLISECOND);
		c20.clear(Calendar.SECOND);
		c20.clear(Calendar.MILLISECOND);
		c21.clear(Calendar.SECOND);
		c21.clear(Calendar.MILLISECOND);
		c22.clear(Calendar.SECOND);
		c22.clear(Calendar.MILLISECOND);
		c23.clear(Calendar.SECOND);
		c23.clear(Calendar.MILLISECOND);
		
		dates0.add(c00.getTime());
		dates0.add(c01.getTime());
		dates0.add(c02.getTime());
		dates0.add(c03.getTime());
		dates1.add(c10.getTime());
		dates1.add(c11.getTime());
		dates1.add(c12.getTime());
		dates2.add(c20.getTime());
		dates2.add(c12.getTime());
		dates2.add(c22.getTime());
		dates2.add(c23.getTime());
		
	}
	
	@Test
	public void test1() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-17 16:35");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
			Calendar expected = convertor.stringToDate("2020-01-17 16:35");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 16:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
			Calendar expected = convertor.stringToDate("2020-01-16 22:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
			Calendar expected = convertor.stringToDate("2020-01-16 22:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 22, 0);
			
			dates0.add(cal.getTime());
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
			Calendar expected = convertor.stringToDate("2020-01-17 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 23, 0);
			
			dates0.add(cal.getTime());
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
			Calendar expected = convertor.stringToDate("2020-01-17 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testVacation1() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 23, 0);
			
			dates0.add(cal.getTime());
			
			Calendar beginning = Calendar.getInstance();
			Calendar ending = Calendar.getInstance();
			
			List<AbsenceInnerDTO> absence0 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence1 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence2 = new ArrayList<AbsenceInnerDTO>();
			
			beginning.set(2020, 0, 15);
			ending.set(2020, 0, 21);
			absence0.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));

			beginning.set(2020, 0, 16);
			ending.set(2020, 0, 23);
			absence1.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, absence0, absence1, absence2, time);
			Calendar expected = convertor.stringToDate("2020-01-24 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testVacation2() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 23, 0);
			
			dates0.add(cal.getTime());
			
			Calendar beginning = Calendar.getInstance();
			Calendar ending = Calendar.getInstance();
			
			List<AbsenceInnerDTO> absence0 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence1 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence2 = new ArrayList<AbsenceInnerDTO>();
			
			beginning.set(2020, 0, 15);
			ending.set(2020, 0, 21);
			absence2.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, absence0, absence1, absence2, time);
			Calendar expected = convertor.stringToDate("2020-01-23 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testVacation3() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 23, 0);
			
			dates0.add(cal.getTime());
			
			Calendar beginning = Calendar.getInstance();
			Calendar ending = Calendar.getInstance();
			
			List<AbsenceInnerDTO> absence0 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence1 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence2 = new ArrayList<AbsenceInnerDTO>();
			
			beginning.set(2020, 0, 15);
			ending.set(2020, 0, 21);
			absence0.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));

			beginning.set(2020, 0, 16);
			ending.set(2020, 0, 23);
			absence1.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			beginning.set(2020, 0, 20);
			ending.set(2020, 0, 23);
			absence2.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, absence0, absence1, absence2, time);
			Calendar expected = convertor.stringToDate("2020-01-24 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testVacation4() {
		try {
			
			Calendar time = convertor.stringToDate("2020-01-16 2:00");
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2020, 0, 16, 23, 0);
			
			dates0.add(cal.getTime());
			
			Calendar beginning = Calendar.getInstance();
			Calendar ending = Calendar.getInstance();
			
			List<AbsenceInnerDTO> absence0 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence1 = new ArrayList<AbsenceInnerDTO>();
			List<AbsenceInnerDTO> absence2 = new ArrayList<AbsenceInnerDTO>();
			
			beginning.set(2020, 0, 14);
			ending.set(2020, 0, 16);
			absence0.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));

			beginning.set(2020, 0, 15);
			ending.set(2020, 0, 15);
			absence1.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			beginning.set(2020, 0, 20);
			ending.set(2020, 0, 23);
			absence2.add(new AbsenceInnerDTO(beginning.getTime(), ending.getTime()));
			
			Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, absence0, absence1, absence2, time);
			Calendar expected = convertor.stringToDate("2020-01-17 16:00");
			expected.set(Calendar.SECOND, 0);
			expected.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, finded);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void bugFixTest() {
        dr0.setMonday(Shift.NONE);
        dr0.setTuesday(Shift.NONE);
        dr0.setWednesday(Shift.NONE);
        dr0.setThursday(Shift.NONE);
        dr0.setFriday(Shift.SECOND);
        dr0.setSaturday(Shift.THIRD);
        dr0.setSunday(Shift.SECOND);
        
        dr2.setMonday(Shift.NONE);
        dr2.setTuesday(Shift.NONE);
        dr2.setWednesday(Shift.NONE);
        dr2.setThursday(Shift.NONE);
        dr2.setFriday(Shift.SECOND);
        dr2.setSaturday(Shift.THIRD);
        dr2.setSunday(Shift.SECOND);
        
        dr1.setMonday(Shift.NONE);
        dr1.setTuesday(Shift.NONE);
        dr1.setWednesday(Shift.NONE);
        dr1.setThursday(Shift.NONE);
        dr1.setFriday(Shift.SECOND);
        dr1.setSaturday(Shift.THIRD);
        dr1.setSunday(Shift.SECOND);
        
        Calendar time = Calendar.getInstance();
        time.set(2020, 1, 5, 11, 0, 0);
        time.set(Calendar.MILLISECOND, 0);
        
		Calendar finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null, time);
		
		Calendar expected = Calendar.getInstance();
		expected.set(2020, 1, 7, 16, 0, 0);
		expected.set(Calendar.MILLISECOND, 0);

		System.out.println(convertor.dateForFrontEndString(finded));
		
		assertEquals(expected, finded);
	}
}
