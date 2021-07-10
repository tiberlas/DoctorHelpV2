package com.ftn.dr_help.comon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.LeaveRequestRepository;


public class DailySchedule {

	private List<Term> schedule = new ArrayList<Term> ();
	private Shift shift;
	private boolean isOnLeave = false;
	
	private LeaveRequestRepository leaveRequestRepository;
	
	private void init (Shift shift, Calendar inputDay) throws ParseException {
		int year = inputDay.get (Calendar.YEAR);
		int month = inputDay.get (Calendar.MONTH);
		int day = inputDay.get (Calendar.DAY_OF_MONTH);
		
		String firstPart = year + "-" + (month + 1) + "-" + day + " ";
		System.out.println("Formative day of month: " + day);
		List<String> terms = new ArrayList<String> ();
		
		for (int i = 0; i < 24; ++i) {
			for (int j = 0; j < 60; j += 15) {
				terms.add(firstPart + ((i < 10) ? ("0") : ("")) + i + ":" + ((j == 0) ? ("0") : ("")) + j);
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyyy-M-dd HH:mm");
		for (String s : terms) {
			Calendar key = Calendar.getInstance ();
			//System.out.println(s);
			key.setTime (sdf.parse(s));
			schedule.add (new Term (key, false));
		}
		
	}
	
	private void setIsOnLeave (Long doctorId) {
		System.out.println("");
		System.out.println("");
		System.out.println("Checking if doctor " + doctorId + " is on leave");
		
		Calendar today = schedule.get(0).getTime();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
			
		Integer count;
		if (leaveRequestRepository == null) {
			System.out.println("I fount no leave request repository :p");
			return;
		}
//		leaveRequestRepository.getDoctorApprovedLeaveRequests(doctorId);
		count = leaveRequestRepository.checkIfDoctorIsFree(doctorId, today);
		System.out.println("");
		System.out.println("");
		if (count == null) {
			System.out.println("Doctor " + doctorId + " is NOT on leave on " + today.getTime());
			isOnLeave = false;
			return;
		}
		if (count == 0) {
			System.out.println("Doctor " + doctorId + " is NOT on leave on " + today.getTime());
			isOnLeave = false;
			return;
		}
		else {
			System.out.println("Doctor " + doctorId + " IS on leave on " + today.getTime());
			isOnLeave = true;
			return;
		}
	}
	
	
	public void printSchedule () {
		System.out.println ("----------------------------------------------------------------------------------------");
		System.out.println ("Daily schedule:");
		for (Term t : schedule) {
			System.out.println (t.getTime().get (Calendar.YEAR) + "-" 
						+ t.getTime ().get (Calendar.MONTH) + "-" 
						+ t.getTime ().get (Calendar.DAY_OF_MONTH) + " " 
						+ t.getTime ().get (Calendar.HOUR_OF_DAY) + ":" 
						+ t.getTime ().get (Calendar.MINUTE) + "   \t:" 
						+ t.isTaken());
		}
	}
	
	public DailySchedule (Calendar calendar, Shift shift, Long doctorId, LeaveRequestRepository lrp) throws ParseException {
		init (shift, calendar);
		this.leaveRequestRepository = lrp;
		setIsOnLeave(doctorId);
		this.shift = shift;
	}
	
	public Term getTerm (Calendar cal) {
		for (Term t : schedule) {
//			System.out.println("Do fora je");
			if (t.getTime().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
//				System.out.println("Do godine je");
				if (t.getTime().get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
//					System.out.println("Do meseca je");
//					System.out.println("t.getTime().get(Calendar.DAY_OF_MONTH): " + t.getTime().get(Calendar.DAY_OF_MONTH));
//					System.out.println("cal.get(Calendar.DAY_OF_MONTH): " + cal.get(Calendar.DAY_OF_MONTH));
					if (t.getTime().get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
//						System.out.println("Do dana je");
						if (t.getTime().get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY)) {
//							System.out.println("Do sata je");
							if (t.getTime().get(Calendar.MINUTE) == cal.get(Calendar.MINUTE)) {
//								System.out.println("Do minuta je");
								return t;
							}
						}
					}
				}
			}
		}
//		System.out.println("No term :p");
		return null;
	}
	
	public boolean canAdd (AppointmentPOJO appointment) {
		if (this.shift == Shift.NONE) {
			return false;
		}
		
		Calendar startTime = appointment.getDate();
		Date duration = appointment.getProcedureType().getDuration();
		Calendar endTime = Calendar.getInstance();
		
		endTime.setTime(duration);
		
//		System.out.println ("Pocetni end time: " + endTime.get (Calendar.YEAR) + "-" 
//				+ endTime.get (Calendar.MONTH) + "-" 
//				+ endTime.get (Calendar.DAY_OF_MONTH) + " " 
//				+ endTime.get (Calendar.HOUR_OF_DAY) + ":" 
//				+ endTime.get (Calendar.MINUTE));		
		
		endTime.set (Calendar.YEAR, startTime.get (Calendar.YEAR));
		endTime.set (Calendar.MONTH, startTime.get (Calendar.MONTH));
		endTime.set (Calendar.DAY_OF_MONTH, startTime.get (Calendar.DAY_OF_MONTH));
		endTime.add (Calendar.HOUR_OF_DAY, startTime.get (Calendar.HOUR_OF_DAY));
		endTime.add (Calendar.MINUTE, startTime.get (Calendar.MINUTE));
		
//		System.out.println("Pokusavam da ubacim termin od " + startTime.getTime() + " do " + endTime.getTime());
		
		
//		System.out.println ("Start time: " + startTime.get (Calendar.YEAR) + "-" 
//				+ startTime.get (Calendar.MONTH) + "-" 
//				+ startTime.get (Calendar.DAY_OF_MONTH) + " " 
//				+ startTime.get (Calendar.HOUR_OF_DAY) + ":" 
//				+ startTime.get (Calendar.MINUTE));
//		System.out.println ("End time: " + endTime.get (Calendar.YEAR) + "-" 
//				+ endTime.get (Calendar.MONTH) + "-" 
//				+ endTime.get (Calendar.DAY_OF_MONTH) + " " 
//				+ endTime.get (Calendar.HOUR_OF_DAY) + ":" 
//				+ endTime.get (Calendar.MINUTE));	

		if (startTime.get (Calendar.YEAR) != endTime.get (Calendar.YEAR)) {
			//System.out.println("Predjoh u sl godinu");
			return false;
		}
		if (startTime.get (Calendar.MONTH) != endTime.get (Calendar.MONTH)) {
			//System.out.println("Predjoh u sl mesec");
			return false;
		}
		if (startTime.get (Calendar.DAY_OF_MONTH) != endTime.get (Calendar.DAY_OF_MONTH)) {
			//System.out.println("Predjoh u sl dan");
			return false;
		}
		if (this.shift == Shift.THIRD) {
			if (startTime.get(Calendar.HOUR_OF_DAY) > 8) {
				return false;
			}
			if (endTime.get(Calendar.HOUR_OF_DAY) > 8) {
				return false;
			}
			if (endTime.get(Calendar.HOUR_OF_DAY) == 8) {
				if (endTime.get(Calendar.MINUTE) != 0) {
					return false;
				}
			}
		} 
		else if (this.shift == Shift.FIRST) {
			if (startTime.get(Calendar.HOUR_OF_DAY) < 8) {
				return false;
			}
			if (startTime.get(Calendar.HOUR_OF_DAY) >= 16) {
				return false;
			}
			if (endTime.get(Calendar.HOUR_OF_DAY) > 16) {
				return false;
			}
			if (endTime.get(Calendar.HOUR_OF_DAY) == 16) {
				if (endTime.get(Calendar.MINUTE) != 0) {
					return false;
				}
			}
		} 
		else if (this.shift == Shift.SECOND) {
			if (startTime.get(Calendar.HOUR_OF_DAY) < 16) {
				return false;
			}
		}
		return rangeIsFree (startTime, endTime);
	}
	
	boolean rangeIsFree (Calendar start, Calendar end) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(start.getTime());
//		System.out.println("Petljam:");
		while ((temp.get(Calendar.HOUR_OF_DAY) < end.get(Calendar.HOUR_OF_DAY)) || (temp.get(Calendar.MINUTE) <= end.get(Calendar.MINUTE) && temp.get(Calendar.HOUR_OF_DAY) == end.get(Calendar.HOUR_OF_DAY))) {
//			System.out.println ("rangeIsFree Interval: " + temp.get (Calendar.YEAR) + "-" 
//					+ temp.get (Calendar.MONTH) + "-" 
//					+ temp.get (Calendar.DAY_OF_MONTH) + " " 
//					+ temp.get (Calendar.HOUR_OF_DAY) + ":" 
//					+ temp.get (Calendar.MINUTE));
			Term curr = getTerm (temp);
			if (curr == null) {
				return false;
			}
			if (curr.isTaken()) {
				return false;
			}
			temp.add(Calendar.MINUTE, 15);
		}
		
		return true;
	}
	
	public boolean addAppointment (AppointmentPOJO appointment) {
		if (this.canAdd(appointment)) {
			Calendar startTime = appointment.getDate();
			Date duration = appointment.getProcedureType().getDuration();
			Calendar endTime = Calendar.getInstance();
			
			
			endTime.setTime(duration);
			
//			System.out.println ("Pocetni end time: " + endTime.get (Calendar.YEAR) + "-" 
//					+ endTime.get (Calendar.MONTH) + "-" 
//					+ endTime.get (Calendar.DAY_OF_MONTH) + " " 
//					+ endTime.get (Calendar.HOUR_OF_DAY) + ":" 
//					+ endTime.get (Calendar.MINUTE));		
			
			endTime.set (Calendar.YEAR, startTime.get (Calendar.YEAR));
			endTime.set (Calendar.MONTH, startTime.get (Calendar.MONTH));
			endTime.set (Calendar.DAY_OF_MONTH, startTime.get (Calendar.DAY_OF_MONTH));
			endTime.add (Calendar.HOUR_OF_DAY, startTime.get (Calendar.HOUR_OF_DAY));
			endTime.add (Calendar.MINUTE, startTime.get (Calendar.MINUTE));
			
			Calendar temp = startTime;
			
			while ((temp.get(Calendar.HOUR_OF_DAY) < endTime.get(Calendar.HOUR_OF_DAY)) 
						|| (temp.get(Calendar.MINUTE) < endTime.get(Calendar.MINUTE))) {
//				System.out.println ("addAppointment Interval: " + temp.get (Calendar.YEAR) + "-" 
//						+ temp.get (Calendar.MONTH) + "-" 
//						+ temp.get (Calendar.DAY_OF_MONTH) + " " 
//						+ temp.get (Calendar.HOUR_OF_DAY) + ":" 
//						+ temp.get (Calendar.MINUTE));
				Term curr = getTerm (temp);
				curr.setTaken(true);
				temp.add(Calendar.MINUTE, 15);
			}
			
			return true;
		}
		return false;
	}
	
	public List<Term> getAvaliableTerms (ProceduresTypePOJO procedureType) {
		List<Term> retVal = new ArrayList<Term> ();
		
		if (this.isOnLeave) {
			return retVal;
		}
		
		
		Calendar shiftStart = Calendar.getInstance();
		Calendar shiftEnd = Calendar.getInstance();
		
		shiftStart.set(Calendar.YEAR, schedule.get(0).getTime().get(Calendar.YEAR));
		shiftEnd.set(Calendar.YEAR, schedule.get(0).getTime().get(Calendar.YEAR));
		shiftStart.set(Calendar.MONTH, schedule.get(0).getTime().get(Calendar.MONTH));
		shiftEnd.set(Calendar.MONTH, schedule.get(0).getTime().get(Calendar.MONTH));
		shiftStart.set(Calendar.DAY_OF_MONTH, schedule.get(0).getTime().get(Calendar.DAY_OF_MONTH));
		shiftEnd.set(Calendar.DAY_OF_MONTH, schedule.get(0).getTime().get(Calendar.DAY_OF_MONTH));
		shiftStart.set (Calendar.MINUTE, 0);
		shiftEnd.set(Calendar.MINUTE, 59);
		shiftStart.set(Calendar.SECOND, 0);
		shiftEnd.set(Calendar.SECOND, 59);
		
		if (this.shift == Shift.THIRD) {
			shiftStart.set(Calendar.HOUR_OF_DAY, 0);
			shiftEnd.set(Calendar.HOUR_OF_DAY, 7);
		} else if (this.shift == Shift.FIRST) {
			shiftStart.set(Calendar.HOUR_OF_DAY, 8);
			shiftEnd.set(Calendar.HOUR_OF_DAY, 15);
		} else if (this.shift == Shift.SECOND) {
			shiftStart.set(Calendar.HOUR_OF_DAY, 16);
			shiftEnd.set(Calendar.HOUR_OF_DAY, 23);
		} else {
			System.out.println("          SIso, jedem govna!!!!1!!!");
			return retVal;
		}
		
		AppointmentPOJO fakeAppointment = new AppointmentPOJO ();
		fakeAppointment.setProcedureType(procedureType);
		fakeAppointment.setDate(shiftStart);
		
//		System.out.println("Shift start: " + shiftStart.get(Calendar.YEAR)
//					+ "-" + shiftStart.get(Calendar.MONTH)
//					+ "-" + shiftStart.get(Calendar.DAY_OF_MONTH)
//					+ " " + shiftStart.get(Calendar.HOUR_OF_DAY)
//					+ ":" + shiftStart.get(Calendar.MINUTE));
//		System.out.println("Shift end: " + shiftEnd.get(Calendar.YEAR)
//					+ "-" + shiftEnd.get(Calendar.MONTH)
//					+ "-" + shiftEnd.get(Calendar.DAY_OF_MONTH)
//					+ " " + shiftEnd.get(Calendar.HOUR_OF_DAY)
//					+ ":" + shiftEnd.get(Calendar.MINUTE));
//		System.out.println("Fake appointment: " + fakeAppointment.getDate().get(Calendar.YEAR)
//					+ "-" + fakeAppointment.getDate().get(Calendar.MONTH)
//					+ "-" + fakeAppointment.getDate().get(Calendar.DAY_OF_MONTH)
//					+ " " + fakeAppointment.getDate().get(Calendar.HOUR_OF_DAY)
//					+ ":" + fakeAppointment.getDate().get(Calendar.MINUTE));
		
		while (fakeAppointment.getDate().before(shiftEnd)) {
			
			if (this.canAdd(fakeAppointment)) {
				Calendar temp = Calendar.getInstance();
				temp.set(Calendar.YEAR, fakeAppointment.getDate().get(Calendar.YEAR));
				temp.set(Calendar.MONTH, fakeAppointment.getDate().get(Calendar.MONTH));
				temp.set(Calendar.DAY_OF_MONTH, fakeAppointment.getDate().get(Calendar.DAY_OF_MONTH));
				temp.set(Calendar.HOUR_OF_DAY, fakeAppointment.getDate().get(Calendar.HOUR_OF_DAY));
				temp.set(Calendar.MINUTE, fakeAppointment.getDate().get(Calendar.MINUTE));
				temp.set(Calendar.SECOND, fakeAppointment.getDate().get(Calendar.SECOND));
				retVal.add(new Term (temp, false));
				
//				System.out.println("Slobodan sam: " + fakeAppointment.getDate().get(Calendar.YEAR) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.MONTH) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.DAY_OF_MONTH) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.HOUR_OF_DAY) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.MINUTE) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.SECOND)
//						+ " za " + fakeAppointment.getProcedureType().getName());
			}
			else {
//				System.out.println("Nisam slobodan: " + fakeAppointment.getDate().get(Calendar.YEAR) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.MONTH) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.DAY_OF_MONTH) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.HOUR_OF_DAY) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.MINUTE) 
//						+ "-" + fakeAppointment.getDate().get(Calendar.SECOND)
//						+ " za " + fakeAppointment.getProcedureType().getName());
			}
			
			fakeAppointment.getDate().add(Calendar.MINUTE, 15);
		}
		
		return retVal;
	}
	
}
