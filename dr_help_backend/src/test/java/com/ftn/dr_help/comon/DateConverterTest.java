
package com.ftn.dr_help.comon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DateConverterTest {

	@Autowired
	private DateConverter convertor;
	
	@Test
	public void testPMShouldPass() {
		try {
			Calendar date = convertor.stringToDate("2020-05-13 20:30");
			Calendar expected = Calendar.getInstance();
			expected.set(Calendar.YEAR, 2020);
			expected.set(Calendar.MONTH, 4);
			expected.set(Calendar.DAY_OF_MONTH, 13);
			expected.set(Calendar.HOUR, 8);
			expected.set(Calendar.MINUTE, 30);
			expected.set(Calendar.AM_PM, Calendar.PM);
			expected.clear(Calendar.SECOND);
			expected.clear(Calendar.MILLISECOND);
			
			assertEquals(expected, date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testAMShouldPass() {
		try {
			Calendar date = convertor.stringToDate("2020-05-13 9:36");
			Calendar expected = Calendar.getInstance();
			expected.set(Calendar.YEAR, 2020);
			expected.set(Calendar.MONTH, 4);
			expected.set(Calendar.DAY_OF_MONTH, 13);
			expected.set(Calendar.HOUR, 9);
			expected.set(Calendar.MINUTE, 36);
			expected.set(Calendar.AM_PM, Calendar.AM);
			expected.clear(Calendar.SECOND);
			expected.clear(Calendar.MILLISECOND);
			
			assertEquals(expected, date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testDateToTimeAM() {
		try {
			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 20, 9, 16, 0);
			expected.set(Calendar.MILLISECOND, 0);
			Calendar actual = convertor.americanStringToDate("01/20/2020 09:16 AM");
			actual.set(Calendar.YEAR, 2020);
			actual.set(Calendar.MONTH, 0);
			actual.set(Calendar.DAY_OF_MONTH, 20);
			actual.set(Calendar.SECOND, 0);
			actual.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, actual);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testDateToTimePM() {
		try {
			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 20, 21, 16, 0);
			expected.set(Calendar.MILLISECOND, 0);
			Calendar actual = convertor.americanStringToDate("01/20/2020 09:16 PM");
			actual.set(Calendar.YEAR, 2020);
			actual.set(Calendar.MONTH, 0);
			actual.set(Calendar.DAY_OF_MONTH, 20);
			actual.set(Calendar.SECOND, 0);
			actual.set(Calendar.MILLISECOND, 0);
			
			assertEquals(expected, actual);
			
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}

