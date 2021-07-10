package com.ftn.dr_help.comon.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;

@Service
public class CalculateFirstFreeSchedule {
	
	@Autowired
	private NiceScheduleBeginning niceBeginning;
	
	@Autowired
	private CheckShift shift;
	
	@Autowired
	private WorkScheduleAdapter workSchedule;
	
	@Autowired
	private DateConverter convert; // for debug only
	
	public Calendar checkScheduleOrFindFirstFree(MedicalStaffWorkSchedularPOJO medicalEmploie, Calendar begin, List<Date> dates, List<AbsenceInnerDTO> absenceDates) {
		/**
		 * proverava da li trazeni datum pregleda(begin) odgovara lekaru
		 * uzima u obzir dates-lista zakazanih pregleda i absenceDates-lista ne radnih dana
		 * */
		Calendar check = findFreeSchedule(medicalEmploie, begin, dates, absenceDates, true);
		if(check == null) {
			//trazeni datum je zauzet; sad ponuditi prvi sledeci slobodan
			Calendar newBegin = Calendar.getInstance();
			newBegin.setTime(begin.getTime());
			newBegin.set(Calendar.SECOND, 0);
			newBegin.set(Calendar.MILLISECOND, 0);
			if(!shift.checkShift(newBegin, medicalEmploie)) { 
				newBegin.add(Calendar.DAY_OF_YEAR, 1);
			}
			niceBeginning.setNiceScheduleBeginning(medicalEmploie, newBegin);

			//provera da novi pocetak ne pocne pre trazenog termina
			while(newBegin.compareTo(begin) < 0) {
				Calendar duration = Calendar.getInstance();
				duration.setTime(medicalEmploie.getDuration().getTime());
				int hours = duration.get(Calendar.HOUR);
				int minutes = duration.get(Calendar.MINUTE);
				
				newBegin.add(Calendar.HOUR, hours);
				newBegin.add(Calendar.MINUTE, minutes);
				System.out.println("uvecaj " + convert.dateAndTimeToString(newBegin));
			}
			
			return findFreeSchedule(medicalEmploie, newBegin, dates, absenceDates, false);
		} else {
			return check;
		}
	}
	
	/*
	 * function uses doctor and schedule(begin) to check if it is free
	 * if the schedule(begin) is not free return the first free schedule after the given schedule(begin)
	 * */
	public Calendar findFirstScheduleForDoctor(MedicalStaffWorkSchedularPOJO doctor, Calendar begin, List<Date> dates, List<AbsenceInnerDTO> absenceDates) {	
		//provera da li je begin schedule u dobroj smeni i u okruglom vremenu
		
		//vrati prvi slobodan termin
		return findFreeSchedule(doctor, begin, dates, absenceDates, false);
	}
	
	private Calendar findFreeSchedule(MedicalStaffWorkSchedularPOJO doctor, Calendar start, List<Date> dates, List<AbsenceInnerDTO> absenceDates, boolean justCheckDate) {
		System.out.println("krenuo od " + convert.dateForFrontEndString(start));
		
		Calendar begin = Calendar.getInstance();
		begin = setWorkingDay(doctor, start, absenceDates);
		
		//nadje trajanje za schedule
		Calendar duration = Calendar.getInstance();
		duration.setTime(doctor.getDuration().getTime());
		int hours = duration.get(Calendar.HOUR);
		int minutes = duration.get(Calendar.MINUTE);
		
		//najmanja jediniza za schedule je minuta 
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);

		//kraj schedula
		Calendar end = Calendar.getInstance();
		end.setTime(begin.getTime());
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		end.add(Calendar.HOUR, hours);
		end.add(Calendar.MINUTE, minutes);
		
		if(dates == null || dates.isEmpty()) {
			return begin;
		}
		
		Calendar currentBegin = Calendar.getInstance();
		Calendar currentEnd = Calendar.getInstance();
		
		for(Date date : dates) {
			//iteriramo kroz zakazane termine; termini su sortirani 
			currentBegin.setTime(date);
			currentEnd.setTime(date);
			currentEnd.add(Calendar.HOUR, hours);
			currentEnd.add(Calendar.MINUTE, minutes);
			currentBegin.set(Calendar.SECOND, 0);
			currentBegin.set(Calendar.MILLISECOND, 0);
			currentEnd.set(Calendar.SECOND, 0);
			currentEnd.set(Calendar.MILLISECOND, 0);
			
			System.out.println("----------------------------------------------------");
			System.out.println("BEGIN: " + convert.dateForFrontEndString(begin));
			System.out.println("END: " + convert.dateForFrontEndString(end));
			System.out.println("CURRENT BEGIN: " + convert.dateForFrontEndString(currentBegin));
			System.out.println("CURRENT END: " + convert.dateForFrontEndString(currentEnd));
			
			if(begin.compareTo(currentEnd) >= 0) {
				System.out.println("begin je posle current end");
				continue;
			}
			
			//provera da li je termin zauzet
			if(end.compareTo(currentBegin) <= 0) {
				//termin je pre pocetka od tekucek zakazanog termina
				System.out.println("Ovo je lepo---" + begin.getTime());
				return begin;
			} else {
				//uzima se termin posle tekuceg zakazanog ili ako je u rezimu provere termina vrati null
				if(justCheckDate) return null;
				
				//provera da li je termin posle tekuceg u radnom vremenu
				if(!checkWorkingDay(doctor, currentEnd, absenceDates)) {
					niceBeginning.setNiceScheduleBeginning(doctor, currentEnd);
					//currentEnd = setWorkingDay(doctor, currentEnd, absenceDates);
					
					if(currentEnd.compareTo(begin) <= 0) {
						//vratio je za prethodni dan
						currentEnd.add(Calendar.DAY_OF_MONTH, 1);
						currentEnd.set(Calendar.HOUR_OF_DAY, 0);
						currentEnd.set(Calendar.HOUR, 0);
						currentEnd.set(Calendar.MINUTE, 0);
						currentEnd.set(Calendar.SECOND, 0);
						currentEnd.set(Calendar.MILLISECOND, 0);
						currentEnd = setWorkingDay(doctor, currentEnd, absenceDates);
					}
				}
				
				//postavi prvi slobodan termin za proveru
				begin.setTime(currentEnd.getTime());
				end.setTime(currentEnd.getTime());
				end.add(Calendar.HOUR, hours);
				end.add(Calendar.MINUTE, minutes);
				
			}
		}
		
		//if(!shift.checkShift(begin, doctor) || !shift.checkShift(end, doctor)) {
		//	begin.add(Calendar.DAY_OF_MONTH, 1);
		//	return findFreeSchedule(doctor, begin, dates, absenceDates, false);		
		//}
			
		System.out.println("Ovde je kraj---" + begin.getTime());
		return begin;
	}
	
	public Calendar setWorkingDay(MedicalStaffWorkSchedularPOJO doctor, Calendar schedule, List<AbsenceInnerDTO> absenceDates) {
		/*
		 * proveri da li doktor radi za dan schedule; ako ne vrati prvi radni, slobodan dan
		 * */
		
		Calendar date = (Calendar) schedule.clone();
		
		if(checkWorkingDay(doctor, date, absenceDates)) {
			//datum je dobar
			return date;
		}

		Calendar oldDate = Calendar.getInstance();
		oldDate = (Calendar) date.clone();
		
		if(!shift.checkShift(date, doctor)) {
			niceBeginning.setNiceScheduleBeginning(doctor, date);
		
			if(oldDate.compareTo(date) > 0) {
				date.add(Calendar.DAY_OF_YEAR, 1);
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.HOUR, 0);
				date.set(Calendar.AM_PM, Calendar.AM);
				date.set(Calendar.MINUTE, 0);
				date.set(Calendar.MILLISECOND, 0);
				date.set(Calendar.SECOND, 0);
				niceBeginning.setNiceScheduleBeginning(doctor, date);
			}
		}
		
		while(!checkWorkingDay(doctor, date, absenceDates)) {			
			date.add(Calendar.DAY_OF_YEAR, 1);
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.MILLISECOND, 0);
			date.set(Calendar.SECOND, 0);
			niceBeginning.setNiceScheduleBeginning(doctor, date);
		}
		
		return date;
	}
	
	private boolean checkWorkingDay(MedicalStaffWorkSchedularPOJO doctor, Calendar schedule, List<AbsenceInnerDTO> absenceDates) {
		/**
		 * provera da li doctor radi tog dana i da li je smena dobra
		 * */
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		if(absenceDates != null && !absenceDates.isEmpty()) {
			for(AbsenceInnerDTO absence : absenceDates) {
				begin.setTime(absence.getBeginning());
				begin.set(Calendar.AM_PM, Calendar.AM);
				begin.set(Calendar.HOUR, 0);
				begin.set(Calendar.MINUTE, 0);
				begin.set(Calendar.SECOND, 0);
				begin.set(Calendar.MILLISECOND, 0);
				
				end.setTime(absence.getEnding());
				end.set(Calendar.AM_PM, Calendar.PM);
				end.set(Calendar.HOUR, 11);
				end.set(Calendar.MINUTE, 59);
				end.set(Calendar.SECOND, 59);
				end.set(Calendar.MILLISECOND, 59);
				
				if(schedule.compareTo(begin) >= 0 && schedule.compareTo(end) <= 0) {
					return false;
				}
			}
		}
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(doctor.getDuration().getTime());
		int hours = duration.get(Calendar.HOUR);
		int minutes = duration.get(Calendar.MINUTE);
		
		Calendar endSchedule = Calendar.getInstance();
		endSchedule.setTime(schedule.getTime());
		endSchedule.add(Calendar.HOUR, hours);
		endSchedule.add(Calendar.MINUTE, minutes-1);
		
		int day = schedule.get(Calendar.DAY_OF_WEEK);
		
		switch(day) {
		case Calendar.MONDAY:
			return shift.check(schedule, doctor.getMonday()) && shift.check(endSchedule, doctor.getMonday());
		case Calendar.TUESDAY:
			return shift.check(schedule, doctor.getTuesday()) && shift.check(endSchedule, doctor.getTuesday());
		case Calendar.WEDNESDAY:
			return shift.check(schedule, doctor.getWednesday()) && shift.check(endSchedule, doctor.getWednesday());
		case Calendar.THURSDAY:
			return shift.check(schedule, doctor.getThursday()) && shift.check(endSchedule, doctor.getThursday());
		case Calendar.FRIDAY:
			return shift.check(schedule, doctor.getFriday()) && shift.check(endSchedule, doctor.getFriday());
		case Calendar.SATURDAY:
			return shift.check(schedule, doctor.getSaturday()) && shift.check(endSchedule, doctor.getSaturday());
		case Calendar.SUNDAY:
			return shift.check(schedule, doctor.getSunday()) && shift.check(endSchedule, doctor.getSunday());
			
		default:
			return false;
		}
	}
	
	//OPERATION
	public Calendar findFirstScheduleForOperation(DoctorPOJO dr0, DoctorPOJO dr1, DoctorPOJO dr2, List<Date> dates0, List<Date> dates1, List<Date> dates2, List<AbsenceInnerDTO> absenceDates0, List<AbsenceInnerDTO> absenceDates1, List<AbsenceInnerDTO> absenceDates2, Calendar begin) {
		/*ulaz: 3 doktora, liste zakazanih termine, liste leave-requestova, datum kada treba da se zakaze trenutna operacija
		 * izlaz: vraca prvi datum koji odgovara svim lekarima
		 * 
		 * */
		try {
						
			//lsti dana u nedelji koja su zajednicka za doktore
			List<EqualDoctorShifts> equalWorkDays = shift.FindEqualDoctorShifts(dr0, dr1, dr2);
			if(equalWorkDays == null) {
				//mora druga kombinacija doktora
				Calendar notWorking = Calendar.getInstance();
				notWorking.set(Calendar.YEAR, 1984);
				
				return notWorking;
			}
			
			//podesi pocetni dan za trazenje zajednickog pocetka
			begin.set(Calendar.SECOND, 0);
			begin.set(Calendar.MILLISECOND, 0);

			Calendar begin0 = (Calendar) begin.clone();
			
			if(!shift.isInShift(begin, equalWorkDays)) {
				//pocetak mora biti u okviru radnog dan
				begin0 = niceBeginning.setNiceOperationBegin(equalWorkDays, begin);
			}

			Calendar begin1 = Calendar.getInstance();
			begin1.setTime(begin0.getTime());
			Calendar begin2 = Calendar.getInstance();
			begin2.setTime(begin1.getTime());
			
			Calendar free0 = null;
			Calendar free1 = null;
			Calendar free2 = null;
			
			Calendar check0 = findFreeSchedule(workSchedule.fromDoctor(dr0), begin0, dates0, absenceDates0, true);
			Calendar check1 = findFreeSchedule(workSchedule.fromDoctor(dr1), begin0, dates1, absenceDates1, true);
			Calendar check2 = findFreeSchedule(workSchedule.fromDoctor(dr2), begin0, dates2, absenceDates2, true);
			
			if(check0 != null && check1 != null && check2 != null) {
				//dati termin valja
				return begin0;
			}
			
			//nadji privi slobodan termin koji odgovara svim doctorima
			do {
				if(free0 != null) {
					//znaci da je jedna iteracija prosla; sad treba samo begin da se pomeri za najveci nadjeni datum
					
					if(free0.after(free1) && free0.after(free2)) {
						begin0.setTime(free0.getTime());
					} else if(free1.after(free2)) {
						begin0.setTime(free1.getTime());
					} else {
						begin0.setTime(free2.getTime());
					}
					
					if(shift.isInShift(begin0, equalWorkDays)) {
						//ovo je najveci datim sa zajednickim smenama
						//provera da li svim lekarima odgovara ova datum
						check0 = findFreeSchedule(workSchedule.fromDoctor(dr0), begin0, dates0, absenceDates0, true);
						check1 = findFreeSchedule(workSchedule.fromDoctor(dr1), begin0, dates1, absenceDates1, true);
						check2 = findFreeSchedule(workSchedule.fromDoctor(dr2), begin0, dates2, absenceDates2, true);
						
						if(check0 != null && check1 != null && check2 != null) {
							//svim lekarima odgovara nadjeni datim
							return begin0;
						}
					}
					
					begin0.add(Calendar.DAY_OF_MONTH, 1); //da bi algoritam progresovao
					begin0.set(Calendar.HOUR, 0);
					begin0.set(Calendar.MINUTE, 0);
					begin0.set(Calendar.AM_PM, Calendar.AM);
					
					begin1.setTime(begin0.getTime());
					begin2.setTime(begin0.getTime());
				}
				
				free0 = findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin0, dates0, absenceDates0);
				free1 = findFirstScheduleForDoctor(workSchedule.fromDoctor(dr1), begin1, dates1, absenceDates1);
				free2 = findFirstScheduleForDoctor(workSchedule.fromDoctor(dr2), begin2, dates2, absenceDates2);
				
				System.out.println("DEBUG FOR OPERATION");
				System.out.println(convert.dateForFrontEndString(free0));
				System.out.println(convert.dateForFrontEndString(free1));
				System.out.println(convert.dateForFrontEndString(free2));
				
			} while(! (free0.equals(free1) && free1.equals(free2)) );
		
			return free0;
		} catch(Error e) {
			e.printStackTrace();
			return null;
		}
	} 
	
}
