package com.ftn.dr_help.comon.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;

@Service
public class NiceScheduleBeginning {

	@Autowired
	private CheckShift checkShift;
	
	@Autowired
	private WorkScheduleAdapter workSchedule;
	
	@Autowired
	private RoundUntil round;
	
	/*
	 * stavi da pocetak bude shodno radnoj smeni
	 * npr ake je prva smena onda je pocetak u 8 ujutru;
	 * ako je druga onda je poccetak u 4 poslepodne;
	 * i treca je u ponoc
	 * */
	public void setNiceScheduleBeginning(MedicalStaffWorkSchedularPOJO doctor, Calendar begin) {
		
		Shift shift = Shift.NONE;
		
		int i = 0;
		while(i < 7) {
			int day = begin.get(Calendar.DAY_OF_WEEK);

			//nadje smenu
			switch(day) {
			case Calendar.MONDAY:
				shift = doctor.getMonday();
				break;
			case Calendar.TUESDAY:
				shift = doctor.getTuesday();
				break;
			case Calendar.WEDNESDAY:
				shift = doctor.getWednesday();
				break;
			case Calendar.THURSDAY:
				shift = doctor.getThursday();
				break;
			case Calendar.FRIDAY:
				shift = doctor.getFriday();
				break;
			case Calendar.SATURDAY:
				shift = doctor.getSaturday();
				break;
			case Calendar.SUNDAY:
				shift = doctor.getSunday();
				break;
			}
			
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			begin.set(Calendar.MILLISECOND, 0);
			
			switch(shift) {
			case FIRST:
				begin.set(Calendar.HOUR_OF_DAY, 8);
				begin.set(Calendar.HOUR, 8);
				begin.set(Calendar.AM_PM, Calendar.AM);
				return;
			case SECOND:
				begin.set(Calendar.HOUR_OF_DAY, 16);
				begin.set(Calendar.HOUR, 4);
				begin.set(Calendar.AM_PM, Calendar.PM);
				return;
			case THIRD:
				begin.set(Calendar.HOUR_OF_DAY, 0);
				begin.set(Calendar.HOUR, 0);
				begin.set(Calendar.AM_PM, Calendar.AM);
				return;
			default:
				//ovaj dan nije radan
				begin.add(Calendar.DAY_OF_MONTH, 1);
			}
		
			++i;
		}
	}
	
	public Calendar setNiceOperationBegin(List<EqualDoctorShifts> equalShifts, Calendar begin) {
		/*
		 * vrati prida dan posle trazenog dana koji je u preseku radnih smena
		 * */
		Calendar niceBegin = Calendar.getInstance();
		niceBegin.setTime(begin.getTime());
	
		for(int i=0; i<equalShifts.size(); ++i) {
			int day = niceBegin.get(Calendar.DAY_OF_WEEK);

			if(day == equalShifts.get(i).getDay().getValue()) {
				
				setNiceShift(equalShifts, niceBegin);
				return niceBegin;
			} else if(day < equalShifts.get(i).getDay().getValue()) {
				int goInFuture = equalShifts.get(i).getDay().getValue() - day;
				niceBegin.add(Calendar.DAY_OF_MONTH, goInFuture);
				
				setNiceShift(equalShifts, niceBegin);
				return niceBegin;
			} else {
				if(i >= (equalShifts.size() - 1)) {
					//pomeri se za nedelju dana
					niceBegin = round.roundToSunday(niceBegin);
				}					
			}
		}
		
		setNiceShift(equalShifts, niceBegin);
		return niceBegin;
	}
	
	private void setNiceShift(List<EqualDoctorShifts> equalShifts, Calendar begin) {
		for(EqualDoctorShifts doctorShift : equalShifts) {
			if(!checkShift.check(begin, doctorShift.getShift())) {
				DoctorPOJO doctorMock = new DoctorPOJO();
				ProceduresTypePOJO procedureMock = new ProceduresTypePOJO();
				procedureMock.setDuration(new Date());
				doctorMock.setProcedureType(procedureMock);
				doctorMock.setMonday(Shift.NONE);
				doctorMock.setTuesday(Shift.NONE);
				doctorMock.setWednesday(Shift.NONE);
				doctorMock.setThursday(Shift.NONE);
				doctorMock.setFriday(Shift.NONE);
				doctorMock.setSaturday(Shift.NONE);
				doctorMock.setSunday(Shift.NONE);
				
				for(EqualDoctorShifts forMocking : equalShifts) {
					switch(forMocking.getDay()) {
						case MONDAY:
							doctorMock.setMonday(forMocking.getShift());
							break;
						case TUESDAY:
							doctorMock.setTuesday(forMocking.getShift());
							break;
						case WEDNESDAY:
							doctorMock.setWednesday(forMocking.getShift());
							break;
						case THURSDAY:
							doctorMock.setThursday(forMocking.getShift());
							break;
						case FRIDAY:
							doctorMock.setFriday(forMocking.getShift());
							break;
						case SATURDAY:
							doctorMock.setSaturday(forMocking.getShift());
							break;
						case SUNDAY:
							doctorMock.setSunday(forMocking.getShift());
							break;
					}
				}
				
				setNiceScheduleBeginning(workSchedule.fromDoctor(doctorMock), begin);
			}
		}
	}
}
