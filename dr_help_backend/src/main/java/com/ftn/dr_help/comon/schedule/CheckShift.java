package com.ftn.dr_help.comon.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.enums.DayEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;

@Service
public class CheckShift {
	
	/*
	 * proverava da li je termin u dobroj smeni
	 * prva smena je od 8 do 16 sati
	 * druga smena od 16 do 24 sati
	 * treca od 00 do 8 sati
	 * */
	public boolean check(Calendar schedule, Shift shift) {
		Calendar upper = Calendar.getInstance();
		Calendar lower = Calendar.getInstance();
		
		upper.setTime(schedule.getTime());
		lower.setTime(schedule.getTime());
		
		upper.set(Calendar.MINUTE, 0);
		lower.set(Calendar.MINUTE, 0);
		upper.set(Calendar.SECOND, 0);
		lower.set(Calendar.SECOND, 0);
		upper.set(Calendar.MILLISECOND, 0);
		lower.set(Calendar.MILLISECOND, 0);
		
		switch(shift) {
		case FIRST:
			lower.set(Calendar.HOUR, 8);
			lower.set(Calendar.AM_PM, Calendar.AM);
			upper.set(Calendar.HOUR, 4);
			upper.set(Calendar.AM_PM, Calendar.PM);
			break;
		case SECOND:
			lower.set(Calendar.HOUR, 4);
			lower.set(Calendar.AM_PM, Calendar.PM);
			upper.set(Calendar.HOUR, 0);
			upper.set(Calendar.AM_PM, Calendar.AM);
			upper.add(Calendar.DAY_OF_MONTH, 1);//sledeci dan u 00:00
			break;
		case THIRD:
			lower.set(Calendar.HOUR, 0);
			lower.set(Calendar.AM_PM, Calendar.AM);
			upper.set(Calendar.HOUR, 8);
			upper.set(Calendar.AM_PM, Calendar.AM);
			//upper.add(Calendar.DAY_OF_MONTH, 1); //sledeci dan u 8 ujutru
			break;
		default:
			//slobodan dan je
			return false;
		}
		
		if(schedule.compareTo(lower) >= 0 && schedule.compareTo(upper) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkShift(Calendar schedule, MedicalStaffWorkSchedularPOJO medicalEmploie) {
		
		int day = schedule.get(Calendar.DAY_OF_WEEK);
		switch(day) {
			case Calendar.SUNDAY:
				return check(schedule, medicalEmploie.getSunday());
			case Calendar.MONDAY:
				return check(schedule, medicalEmploie.getMonday());
			case Calendar.TUESDAY:
				return check(schedule, medicalEmploie.getTuesday());
			case Calendar.WEDNESDAY:
				return check(schedule, medicalEmploie.getWednesday());
			case Calendar.THURSDAY:
				return check(schedule, medicalEmploie.getThursday());
			case Calendar.FRIDAY:
				return check(schedule, medicalEmploie.getFriday());
			case Calendar.SATURDAY:
				return check(schedule, medicalEmploie.getSaturday());	
		}
		return false;
	}

	public List<EqualDoctorShifts> FindEqualDoctorShifts(DoctorPOJO dr0, DoctorPOJO dr1, DoctorPOJO dr2) {
		/*
		 * Vraca listu radnih dana kaje su zajednicke za sve doctore
		 * u suprotnom vratu null, pa ti doctori ne mogu da rade na toj operaciji
		 * */
		List<EqualDoctorShifts> finded = new ArrayList<>();
		Shift[] shift = new Shift[7];
		DayEnum[] days = new DayEnum[] {
				DayEnum.SUNDAY, DayEnum.MONDAY, DayEnum.TUESDAY, DayEnum.WEDNESDAY, DayEnum.THURSDAY, DayEnum.FRIDAY, DayEnum.SATURDAY
		};
		
		shift[0] = intercetShift(dr0.getSunday(), dr1.getSunday(), dr2.getSunday());
		shift[1] = intercetShift(dr0.getMonday(), dr1.getMonday(), dr2.getMonday());
		shift[2] = intercetShift(dr0.getTuesday(), dr1.getTuesday(), dr2.getTuesday());
		shift[3] = intercetShift(dr0.getWednesday(), dr1.getWednesday(), dr2.getWednesday());
		shift[4] = intercetShift(dr0.getThursday(), dr1.getThursday(), dr2.getThursday());
		shift[5] = intercetShift(dr0.getFriday(), dr1.getFriday(), dr2.getFriday());
		shift[6] = intercetShift(dr0.getSaturday(), dr1.getSaturday(), dr2.getSaturday());
		
		for(int i = 0; i<7; ++i) {
			if(shift[i] != Shift.NONE) {
				finded.add(new EqualDoctorShifts(days[i] , shift[i]));
			}
		}
		
		if(finded.isEmpty()) {
			return null; //doctori nemaju zajednicke radne dane
		} else {
			return finded;
		}
	}
	
	private Shift intercetShift(Shift sh0, Shift sh1, Shift sh2) {
		
		if(sh0 == Shift.FIRST && sh1 == Shift.FIRST && sh2 == Shift.FIRST) {
			return Shift.FIRST;
		} else if(sh0 == Shift.SECOND && sh1 == Shift.SECOND && sh2 == Shift.SECOND) {
			return Shift.SECOND;
		} else if(sh0 == Shift.THIRD && sh1 == Shift.THIRD && sh2 == Shift.THIRD) {
			return Shift.THIRD;
		} else {
			return Shift.NONE;
		}
	}
	
	public boolean isInShift(Calendar time, List<EqualDoctorShifts> equalShifts) {
		
		Calendar date = Calendar.getInstance();
		date.setTime(time.getTime());
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		int day = date.get(Calendar.DAY_OF_WEEK);
		
		Calendar eight = (Calendar) date.clone();
		eight.set(Calendar.MINUTE, 0);
		eight.set(Calendar.HOUR, 8);
		eight.set(Calendar.AM_PM, Calendar.AM);
		
		Calendar four = (Calendar) eight.clone();
		four.set(Calendar.HOUR, 4);
		four.set(Calendar.AM_PM, Calendar.PM);
		
		Calendar midnight = (Calendar) four.clone();
		midnight.add(Calendar.DAY_OF_MONTH, -1);
		midnight.set(Calendar.HOUR, 0);
		midnight.set(Calendar.AM_PM, Calendar.AM);
		
		Calendar midnightPlusOne = (Calendar) four.clone();
		midnightPlusOne.add(Calendar.DAY_OF_MONTH, 1);
		midnightPlusOne.set(Calendar.HOUR, 0);
		midnightPlusOne.set(Calendar.AM_PM, Calendar.AM);
		
		for(EqualDoctorShifts equalShift : equalShifts) {
			if(day == equalShift.getDay().getValue()) {
				switch(equalShift.getShift()) {
					case FIRST:
						if(date.compareTo(eight) >= 0 && date.compareTo(four) <= 0) {
							return true;
						} else {
							return false;
						}
					case SECOND:
						if(date.compareTo(four) >= 0 && date.compareTo(midnightPlusOne) <= 0) {
							return true;
						} else {
							return false;
						}
					case THIRD:
						if(date.compareTo(midnight) >= 0 && date.compareTo(eight) <= 0) {
							return true;
						} else {
							return false;
						}
					default: break;
				}
			}
		}
		
		return false;
	} 
}
