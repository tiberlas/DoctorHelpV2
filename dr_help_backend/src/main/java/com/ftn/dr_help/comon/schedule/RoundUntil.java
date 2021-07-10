package com.ftn.dr_help.comon.schedule;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class RoundUntil {

	public Calendar roundToMonday(Calendar date) {
		
		Calendar rounded = (Calendar) date.clone();
		
		switch(date.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 7);
				break;
			case Calendar.TUESDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 6);
				break;
			case Calendar.WEDNESDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 5);
				break;
			case Calendar.THURSDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 4);
				break;
			case Calendar.FRIDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 3);
				break;
			case Calendar.SATURDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 2);
				break;
			case Calendar.SUNDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 1);
				break;
		}
		
		rounded.set(Calendar.HOUR_OF_DAY, 0);
		rounded.set(Calendar.MINUTE, 0);
		rounded.set(Calendar.SECOND, 0);
		rounded.set(Calendar.MILLISECOND, 0);
		
		return rounded;
	}
	
	public Calendar roundToSunday(Calendar date) {
		
		Calendar rounded = (Calendar) date.clone();
		
		switch(date.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 6);
				break;
			case Calendar.TUESDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 5);
				break;
			case Calendar.WEDNESDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 4);
				break;
			case Calendar.THURSDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 3);
				break;
			case Calendar.FRIDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 2);
				break;
			case Calendar.SATURDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case Calendar.SUNDAY:
				rounded.add(Calendar.DAY_OF_MONTH, 0);
				break;
		}
		
		rounded.set(Calendar.HOUR_OF_DAY, 0);
		rounded.set(Calendar.MINUTE, 0);
		rounded.set(Calendar.SECOND, 0);
		rounded.set(Calendar.MILLISECOND, 0);
		
		return rounded;
	}
}
