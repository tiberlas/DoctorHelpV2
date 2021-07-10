package com.ftn.dr_help.comon.schedule;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.enums.DayEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckShiftTest {

	@Autowired
	private CheckShift calculate;
	
	private Calendar cal = Calendar.getInstance();
	
	@Test
	public void shiftShouldPass() {
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.AM_PM, Calendar.AM);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftLowerBoundryShouldPass() {
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftCloseToUpperBoundryShouldPass() {
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftUpperBoundryShouldPass() {
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftUpperBoundryShouldFAIL() {
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 1);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftLowerBoundryShouldFAIL() {
		cal.set(Calendar.HOUR, 7);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftShouldFAIL() {
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.FIRST);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftSecondShouldFAIL() {
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 15);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.SECOND);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftSecondShouldPass() {
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 15);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.SECOND);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftSecondCloseToMidthNightShouldPass() {
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.SECOND);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftSecondMidthNightShouldFAIL() {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.SECOND);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftSecondAfterMidthNightShouldFAIL() {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 1);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.SECOND);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftThirdUpperShouldFAIL() {
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertFalse(retVal);
	}
	
	@Test
	public void shiftThirdLowerShouldFAIL() {
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 1);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertFalse(retVal);
	}

	@Test
	public void shiftThirdUpperShouldPass() {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftThirdLowerShouldPass() {
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftThirdShouldPass() {
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertTrue(retVal);
	}
	
	@Test
	public void shiftThirdAgainShouldPass() {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		boolean retVal = calculate.check(cal, Shift.THIRD);
		
		assertTrue(retVal);
	}

	@Test
	public void shiftOperation1Test() {
		DoctorPOJO dr1 = new DoctorPOJO();
		DoctorPOJO dr2 = new DoctorPOJO();
		DoctorPOJO dr3 = new DoctorPOJO();
		
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.FIRST);
		dr1.setWednesday(Shift.SECOND);
		dr1.setThursday(Shift.NONE);
		dr1.setFriday(Shift.NONE);
		dr1.setSaturday(Shift.SECOND);
		dr1.setSunday(Shift.NONE);
		
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.FIRST);
		dr2.setWednesday(Shift.SECOND);
		dr2.setThursday(Shift.NONE);
		dr2.setFriday(Shift.NONE);
		dr2.setSaturday(Shift.SECOND);
		dr2.setSunday(Shift.NONE);
		
		dr3.setMonday(Shift.NONE);
		dr3.setTuesday(Shift.FIRST);
		dr3.setWednesday(Shift.SECOND);
		dr3.setThursday(Shift.NONE);
		dr3.setFriday(Shift.NONE);
		dr3.setSaturday(Shift.SECOND);
		dr3.setSunday(Shift.NONE);
		
		List<EqualDoctorShifts> retVal = calculate.FindEqualDoctorShifts(dr1, dr2, dr3);
		EqualDoctorShifts[] expected = new EqualDoctorShifts[] {
			new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST),
			new EqualDoctorShifts(DayEnum.WEDNESDAY, Shift.SECOND),
			new EqualDoctorShifts(DayEnum.SATURDAY, Shift.SECOND)
		};
		
		if(retVal.size() != expected.length) {
			assertTrue(false);
		}
		
		for(int i=0; i<retVal.size(); ++i) {
			if(!(retVal.get(i).getDay() == expected[i].getDay()) && (retVal.get(i).getShift() == expected[i].getShift())) {
				assertTrue(false);
			}
		}
		
		assertTrue(true);
	}
	
	@Test
	public void shiftOperation2Test() {
		DoctorPOJO dr1 = new DoctorPOJO();
		DoctorPOJO dr2 = new DoctorPOJO();
		DoctorPOJO dr3 = new DoctorPOJO();
		
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.FIRST);
		dr1.setWednesday(Shift.SECOND);
		dr1.setThursday(Shift.NONE);
		dr1.setFriday(Shift.NONE);
		dr1.setSaturday(Shift.SECOND);
		dr1.setSunday(Shift.NONE);
		
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.THIRD);
		dr2.setWednesday(Shift.NONE);
		dr2.setThursday(Shift.NONE);
		dr2.setFriday(Shift.FIRST);
		dr2.setSaturday(Shift.FIRST);
		dr2.setSunday(Shift.NONE);
		
		dr3.setMonday(Shift.NONE);
		dr3.setTuesday(Shift.FIRST);
		dr3.setWednesday(Shift.THIRD);
		dr3.setThursday(Shift.NONE);
		dr3.setFriday(Shift.NONE);
		dr3.setSaturday(Shift.THIRD);
		dr3.setSunday(Shift.FIRST);
		
		List<EqualDoctorShifts> retVal = calculate.FindEqualDoctorShifts(dr1, dr2, dr3);
		
		assertEquals(null, retVal);
	}
	
	@Test
	public void shiftOperation3Test() {
		DoctorPOJO dr1 = new DoctorPOJO();
		DoctorPOJO dr2 = new DoctorPOJO();
		DoctorPOJO dr3 = new DoctorPOJO();
		
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.FIRST);
		dr1.setWednesday(Shift.SECOND);
		dr1.setThursday(Shift.NONE);
		dr1.setFriday(Shift.NONE);
		dr1.setSaturday(Shift.SECOND);
		dr1.setSunday(Shift.FIRST);
		
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.FIRST);
		dr2.setWednesday(Shift.SECOND);
		dr2.setThursday(Shift.FIRST);
		dr2.setFriday(Shift.NONE);
		dr2.setSaturday(Shift.THIRD);
		dr2.setSunday(Shift.NONE);
		
		dr3.setMonday(Shift.NONE);
		dr3.setTuesday(Shift.FIRST);
		dr3.setWednesday(Shift.SECOND);
		dr3.setThursday(Shift.NONE);
		dr3.setFriday(Shift.NONE);
		dr3.setSaturday(Shift.SECOND);
		dr3.setSunday(Shift.NONE);
		
		List<EqualDoctorShifts> retVal = calculate.FindEqualDoctorShifts(dr1, dr2, dr3);
		EqualDoctorShifts[] expected = new EqualDoctorShifts[] {
				new EqualDoctorShifts(DayEnum.TUESDAY, Shift.FIRST),
				new EqualDoctorShifts(DayEnum.WEDNESDAY, Shift.SECOND),
		};
			
		if(retVal.size() != expected.length) {
			assertTrue(false);
		}
		
		for(int i=0; i<retVal.size(); ++i) {
			if(!(retVal.get(i).getDay() == expected[i].getDay()) && (retVal.get(i).getShift() == expected[i].getShift())) {
				assertTrue(false);
			}
		}
		
		assertTrue(true);
	}
	
	@Test
	public void shiftOperation4Test() {
		DoctorPOJO dr1 = new DoctorPOJO();
		DoctorPOJO dr2 = new DoctorPOJO();
		DoctorPOJO dr3 = new DoctorPOJO();
		
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.FIRST);
		dr1.setWednesday(Shift.THIRD);
		dr1.setThursday(Shift.NONE);
		dr1.setFriday(Shift.NONE);
		dr1.setSaturday(Shift.SECOND);
		dr1.setSunday(Shift.FIRST);
		
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.FIRST);
		dr2.setWednesday(Shift.SECOND);
		dr2.setThursday(Shift.FIRST);
		dr2.setFriday(Shift.NONE);
		dr2.setSaturday(Shift.THIRD);
		dr2.setSunday(Shift.FIRST);
		
		dr3.setMonday(Shift.NONE);
		dr3.setTuesday(Shift.NONE);
		dr3.setWednesday(Shift.SECOND);
		dr3.setThursday(Shift.NONE);
		dr3.setFriday(Shift.NONE);
		dr3.setSaturday(Shift.SECOND);
		dr3.setSunday(Shift.FIRST);
		
		List<EqualDoctorShifts> retVal = calculate.FindEqualDoctorShifts(dr1, dr2, dr3);
		List<EqualDoctorShifts> expected = new ArrayList<EqualDoctorShifts>();
		expected.add(new EqualDoctorShifts(DayEnum.SUNDAY, Shift.FIRST));
			
		if(retVal.size() != expected.size()) {
			assertTrue(false);
		}
		
		for(int i=0; i<retVal.size(); ++i) {
			if(!(retVal.get(i).getDay() == expected.get(i).getDay()) && (retVal.get(i).getShift() == expected.get(i).getShift())) {
				assertTrue(false);
			}
		}
		
		assertTrue(true);
	}

	@Test
	public void isInShiftTest1() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2020);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 17);
		time.set(Calendar.HOUR, 1);
		time.set(Calendar.MINUTE, 23);
		time.set(Calendar.AM_PM, Calendar.PM);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.THURSDAY, Shift.SECOND));
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.FIRST));
		
		
		boolean ret = calculate.isInShift(time, equalShifts);
		
		assertTrue(ret);
	}
	
	@Test
	public void isInShiftTest2() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2020);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 17);
		time.set(Calendar.HOUR, 1);
		time.set(Calendar.MINUTE, 23);
		time.set(Calendar.AM_PM, Calendar.PM);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.THURSDAY, Shift.SECOND));
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.THIRD));
		
		
		boolean ret = calculate.isInShift(time, equalShifts);
		
		assertFalse(ret);
	}
	
	@Test
	public void isInShiftTest3() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2020);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 17);
		time.set(Calendar.HOUR, 1);
		time.set(Calendar.MINUTE, 23);
		time.set(Calendar.AM_PM, Calendar.PM);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.SECOND));
		
		
		boolean ret = calculate.isInShift(time, equalShifts);
		
		assertFalse(ret);
	}
	
	@Test
	public void isInShiftTest4() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2020);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 17);
		time.set(Calendar.HOUR, 8);
		time.set(Calendar.MINUTE, 23);
		time.set(Calendar.AM_PM, Calendar.PM);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.SECOND));
		
		
		boolean ret = calculate.isInShift(time, equalShifts);
		
		assertTrue(ret);
	}
	
	@Test
	public void isInShiftTest5() {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2020);
		time.set(Calendar.MONTH, 0);
		time.set(Calendar.DAY_OF_MONTH, 17);
		time.set(Calendar.HOUR, 2);
		time.set(Calendar.MINUTE, 23);
		time.set(Calendar.AM_PM, Calendar.AM);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		List<EqualDoctorShifts> equalShifts = new ArrayList<>();
		equalShifts.add(new EqualDoctorShifts(DayEnum.FRIDAY, Shift.THIRD));
		
		
		boolean ret = calculate.isInShift(time, equalShifts);
		
		assertTrue(ret);
	}
	
	@Test
	public void checkShiftBeginShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 8, 0, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 10, 23, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkShiftEndShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 16, 0, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkShiftAlmostEndShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 15, 59, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkShiftAfterEndShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 16, 0, 1);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkShiftAfterEndShiftTest2() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 16, 10, 1);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkShiftAfterEndShiftTest3() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 18, 0, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkShiftBeforeStartShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 5, 7, 59, 1);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkShiftNotTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 9, 16, 0, 1);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.FIRST, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkThirdShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 6, 0, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkAfterThirdShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 8, 2, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkBeforeThirdShiftTest() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 23, 59, 59);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertFalse(actual);
	}
	
	@Test
	public void checkThirdShiftTest1() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 1, 59, 59);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkThirdShiftTest2() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 0, 0, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
	
	@Test
	public void checkThirdShiftTest3() {
		Calendar time = Calendar.getInstance();
		time.set(2020, 1, 12, 0, 10, 1);
		time.set(Calendar.MILLISECOND, 0);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 0, 1, 1, 0, 0);//1h
		duration.set(Calendar.MILLISECOND, 0);
		MedicalStaffWorkSchedularPOJO medicalEmploie = new MedicalStaffWorkSchedularPOJO(
										Shift.NONE, 
										Shift.NONE, 
										Shift.THIRD, 
										Shift.NONE,
										Shift.NONE, 
										Shift.NONE, 
										Shift.NONE, 
										duration);
		
		boolean actual = calculate.checkShift(time, medicalEmploie);
		
		assertTrue(actual);
	}
}
