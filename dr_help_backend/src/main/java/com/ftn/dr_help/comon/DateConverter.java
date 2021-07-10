package com.ftn.dr_help.comon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class DateConverter {
	
	public String toString (Calendar date) {
		String retVal =  String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + "." 
				+ String.valueOf(date.get(Calendar.MONTH) + 1) + "." 
				+ String.valueOf(date.get(Calendar.YEAR)) + ".";
		return retVal;
	}
	
	public String dateAndTimeToString (Calendar date) {
		String retVal =  String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + "." 
				+ String.valueOf(date.get(Calendar.MONTH) + 1) + "." 
				+ String.valueOf(date.get(Calendar.YEAR)) + ". "
				+ String.valueOf(date.get(Calendar.HOUR)) + ":"
				+ String.valueOf(date.get(Calendar.MINUTE));
		return retVal;
	}
	
	public String timeToString(Calendar time) {
		String retVal = "";
		
		if(time.get(Calendar.HOUR_OF_DAY) < 10) {
			retVal += "0"+String.valueOf(time.get(Calendar.HOUR_OF_DAY)) + ":";
		} else {
			retVal += String.valueOf(time.get(Calendar.HOUR_OF_DAY)) + ":";
		}
		
		if(time.get(Calendar.MINUTE) < 10) {
			retVal += "0" + String.valueOf(time.get(Calendar.MINUTE));
		} else {
			retVal += String.valueOf(time.get(Calendar.MINUTE));
		}
		
		return retVal;
	}
	
	public String americanDateToString(Calendar date) {
		/* YYYY-MM-DD */
		String retVal = String.valueOf(date.get(Calendar.YEAR)) + "-";
		
		if((date.get(Calendar.MONTH) + 1) < 10) {
			retVal += "0"+String.valueOf(date.get(Calendar.MONTH) + 1) + "-";
		} else {
			retVal += String.valueOf(date.get(Calendar.MONTH) + 1) + "-";
		}
		
		if(date.get(Calendar.DAY_OF_MONTH) < 10) {
			retVal += "0"+String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + "-";
		} else {
			retVal += String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		}
		
		return retVal;
	}
	
	public Calendar stringToDate(String stringDate)  throws ParseException{
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		date.setTime(sdf.parse(stringDate));
		
		return date;
	}
	
	//test
	public Calendar americanStringToDate(String stringDate)  throws ParseException{
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
		date.setTime(sdf.parse(stringDate));
		String[] parts = stringDate.split(" ");
		if(parts[2].equals("PM")) {
			date.set(Calendar.AM_PM, Calendar.PM);
		}
			
		
		return date;
	}
	
	public String dateForFrontEndString(Calendar date) {
		String retVal = "";
		
		if((date.get(Calendar.MONTH) + 1) < 10) {
			retVal += "0"+String.valueOf(date.get(Calendar.MONTH) + 1) + "/";
		} else {
			retVal += String.valueOf(date.get(Calendar.MONTH) + 1) + "/";
		}
		
		if(date.get(Calendar.DAY_OF_MONTH) < 10) {
			retVal += "0"+String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + "/";
		} else {
			retVal += String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + "/";
		}
		
		retVal += String.valueOf(date.get(Calendar.YEAR)) + " ";
		
		if(date.get(Calendar.HOUR) < 10) {
			retVal += "0"+String.valueOf(date.get(Calendar.HOUR)) + ":";
		} else {
			retVal += String.valueOf(date.get(Calendar.HOUR)) + ":";
		}
		
		if(date.get(Calendar.MINUTE) < 10) {
			retVal += "0" + String.valueOf(date.get(Calendar.MINUTE));
		} else {
			retVal += String.valueOf(date.get(Calendar.MINUTE));
		}
		
		if(date.get(Calendar.AM_PM) == Calendar.AM) {
			retVal += " AM";
		} else {
			retVal += " PM";
		}
		
		return retVal;
	}
}
