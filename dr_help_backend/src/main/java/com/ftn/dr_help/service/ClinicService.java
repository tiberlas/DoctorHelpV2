package com.ftn.dr_help.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.DailySchedule;
import com.ftn.dr_help.dto.ClinicDTO;
import com.ftn.dr_help.dto.ClinicListingDTO;
import com.ftn.dr_help.dto.ClinicPreviewDTO;
import com.ftn.dr_help.dto.DatePeriodDTO;
import com.ftn.dr_help.dto.HeldAppointmentsChartDataDTO;
import com.ftn.dr_help.dto.HeldAppointmentsRequestDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.ClinicReviewPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.repository.ClinicReviewRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.LeaveRequestRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.validation.ClinicValidation;


@Service()
public class ClinicService {

	@Autowired
	private ClinicRepository repository;
	
	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	@Autowired
	private ClinicValidation clinicValidation;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private ClinicReviewRepository clinicReviewRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	public ClinicPOJO findOne(Long id) {
		if(id == null) {
			return null;
		}
		
		return repository.findById(id).orElseGet(null); 	
	}
	
	public ClinicDTO findOneDTO(Long id) {
		if(id == null) {
			return null;
		}
		
		ClinicPOJO ret = repository.findById(id).orElseGet(null);
		
		if(ret == null) {
			return null;
		}
		
		return new ClinicDTO(ret); 	
	}
	
	public List<ClinicDTO> findAdminAll() {
		List<ClinicPOJO> list =  repository.findAll();
		
		List<ClinicDTO> dDTO = new ArrayList<ClinicDTO>();
		
		for(ClinicPOJO pojo : list) {
			ClinicDTO dto = new ClinicDTO(pojo);
			Integer count = repository.findAdminOccurencesInClinic(pojo.getId());
			if(count == 0) {
				dto.setHasAdmin(false);
			} else {
				dto.setHasAdmin(true);
			}
			
			dDTO.add(dto);
		}
		
		return dDTO;
	}
	

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	
	public List<ClinicPOJO> findAll() {
		return repository.findAll();
	}


	public ClinicPOJO save(ClinicPOJO clinic) {
		if(clinic == null) {
			return null;
		}
		
		return repository.save(clinic);
	}
	
	
	
	public ClinicDTO save(ClinicDTO clinic, String email) {
		if(email == null) {
			return null;
		}
		
		if(clinicValidation.isValid(clinic)) {
			return null;
		}
		
		ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
		if(admin == null) {
			return null;
		}
		
		ClinicPOJO oldClinic = admin.getClinic();
		if(oldClinic == null || !(oldClinic.getId().equals(clinic.getId())) ) {
			return null;
		}
		
		//ClinicUpdate.update(oldClinic, clinic);
		oldClinic.setName(clinic.getName());
		oldClinic.setAddress(clinic.getAddress());
		oldClinic.setState(clinic.getState());
		oldClinic.setCity(clinic.getCity());
		oldClinic.setDescription(clinic.getDescription());
		repository.save(oldClinic);
		
		return new ClinicDTO(oldClinic);
	}
	
	public ClinicPOJO findByName(String name) {
		return repository.findByName(name);
	}
	
	public List<ClinicPOJO> filterByProcedureType (String procedureType) {
		return repository.getClinicsByProcedureType(procedureType);
	}

	public List<ClinicPOJO> filterByDate (List<ClinicPOJO> inputList, String procedureType, String dateString) throws ParseException {
		System.out.println("filter: " + procedureType);
		System.out.println("date: " + dateString);
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyyy-MM-dd hh:mm:ss");
		String dateMinString = dateString + " 00:00:00";
		String dateMaxString = dateString + " 23:59:59";
		

		Date dateMin = sdf.parse (dateMinString);
		Date dateMax = sdf.parse (dateMaxString);
		
		Calendar calendarMin = Calendar.getInstance ();
		Calendar calendarMax = Calendar.getInstance ();
		
		calendarMin.setTime(dateMin);
		calendarMax.setTime(dateMax);
		
		List<ClinicPOJO> retVal = new ArrayList<ClinicPOJO> ();
		System.out.println("Input list size: " + inputList.size());
 		for (ClinicPOJO c : inputList) {
			List<DoctorPOJO> doctors;
			if (procedureType.equals("unfiltered")) {
				doctors = doctorRepository.findAllByClinic_id(c.getId());
			}
			else {
				doctors = doctorRepository.filterByClinicAndProcedureType (c.getId(), procedureType);
			}
			System.out.println("Doctor list size: " + doctors.size());
			System.out.println("Clinic id: " + c.getId() + "; Procedure type: " + procedureType);
			for (DoctorPOJO d : doctors) {
				DailySchedule schedule;
				switch (calendarMin.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.MONDAY:
						schedule = new DailySchedule (calendarMin, d.getMonday(), d.getId(), leaveRequestRepository);
						break;
					case Calendar.TUESDAY:
						schedule = new DailySchedule (calendarMin, d.getTuesday(), d.getId(), leaveRequestRepository);
						break;
					case Calendar.WEDNESDAY:
						schedule = new DailySchedule (calendarMin, d.getWednesday(), d.getId(), leaveRequestRepository);
						break;
					case Calendar.THURSDAY:
						schedule = new DailySchedule (calendarMin, d.getThursday(), d.getId(), leaveRequestRepository);
						break;
					case Calendar.FRIDAY:
						schedule = new DailySchedule (calendarMin, d.getFriday(), d.getId(), leaveRequestRepository);
						break;
					case Calendar.SATURDAY:
						schedule = new DailySchedule (calendarMin, d.getSaturday(), d.getId(), leaveRequestRepository);
						break;
					default:
						schedule = new DailySchedule (calendarMin, d.getSunday(), d.getId(), leaveRequestRepository);
						break;
				}
				List<AppointmentPOJO> appointments = appointmentRepository.getDoctorsAppointments (d.getId(), calendarMin, calendarMax);
				for (AppointmentPOJO a : appointments) {
					schedule.addAppointment(a);
				}
				if (schedule.getAvaliableTerms(d.getProcedureType()).size() > 0) {
					if (!retVal.contains(c)) {
						retVal.add(c);
					}
				}
			}
		}
		
		return retVal;
	}
	
	@Transactional
	public void addReview (Long patientId, Long clinicId, Integer review) {
		ClinicReviewPOJO newReview = new ClinicReviewPOJO (repository.getOne(clinicId), patientRepository.getOne (patientId), review);
		ClinicReviewPOJO oldReview = clinicReviewRepository.getClinicReview (patientId, clinicId);
		if (review == 0) {
			clinicReviewRepository.delete(oldReview);
		}
		else if (oldReview == null) {
			clinicReviewRepository.save(newReview);
		}
		else {
			clinicReviewRepository.updateReview(review, patientId, clinicId);
		}
	}

	public ClinicListingDTO doOtherFilters(ClinicListingDTO input, String state, String city, String address, String minRat, String maxRat, String minPrice, String maxPrice) {
		
		List<String> stateNames = new ArrayList<String> ();
		List<String> cityNames = new ArrayList<String> ();
		List<String> addressNames = new ArrayList<String> ();
		for (ClinicPreviewDTO cp : input.getClinicList()) {
			boolean isThere = false;
			// Set up states
			for (String s : stateNames) {
				if (s.equals(cp.getState())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				stateNames.add(cp.getState());
			}
			
			isThere = false;
			for (String s : cityNames) {
				if (s.equals(cp.getCity())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				cityNames.add(cp.getCity());
			}
			
			isThere = false; 
			for (String s : addressNames) {
				if (s.equals(cp.getAddress())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				addressNames.add(cp.getAddress());
			}
		}
		input.setStateList(stateNames);
		input.setCityList(cityNames);
		input.setAddressList(addressNames);
		
		if (!state.equals("unfiltered")) {
			List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
			for (ClinicPreviewDTO temp : input.getClinicList()) {
				if (temp.getState().equals(state)) {
					tempList.add(temp);
				}
			}
			input.setClinicList(tempList);
		}
		
		if (!city.equals("unfiltered")) {
			List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
			for (ClinicPreviewDTO temp : input.getClinicList()) {
				if (temp.getCity().equals(city)) {
					tempList.add(temp);
				}
			}
			input.setClinicList(tempList);
		}
		
		if (!address.equals("unfiltered")) {
			List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
			for (ClinicPreviewDTO temp : input.getClinicList()) {
				if (temp.getAddress().equals(address)) {
					tempList.add(temp);
				}
			}
			input.setClinicList(tempList);
		}
		
		if ((!minRat.equals("1")) && (!minRat.equals("unfiltered"))) {
			Integer mRat = Integer.parseInt(minRat);
			if (mRat != null) {
				List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
				for (ClinicPreviewDTO temp : input.getClinicList()) {
					if (temp.getRating().equals("/")) {
						continue;
					}
					Float tempInt = Float.parseFloat(temp.getRating());
					if (tempInt != null) {
						if (mRat <= tempInt) {
							tempList.add(temp);
						}
					}
				}
				input.setClinicList(tempList);
			}
		}
		
		if ((!maxRat.equals("5")) && (!maxRat.equals("unfiltered"))) {
			Integer mRat = Integer.parseInt(maxRat);
			if (mRat != null) {
				List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
				for (ClinicPreviewDTO temp : input.getClinicList()) {
					if (temp.getRating().equals("/")) {
						continue;
					}
					Float tempInt = Float.parseFloat(temp.getRating());
					if (tempInt != null) {
						if (mRat >= tempInt) {
							tempList.add(temp);
						}
					}
				}
				input.setClinicList(tempList);
			}
		}
		
		if (!minPrice.equals("unfiltered")) {
			Float price = Float.parseFloat(minPrice);
			if (price != null) {
				List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
				for (ClinicPreviewDTO temp : input.getClinicList()) {
					if (temp.getPrice().equals("/")) {
						continue;
					}
					Float tempFloat = Float.parseFloat(temp.getPrice().split(" ")[0]);
					if (tempFloat != null) {
						if (price <= tempFloat) {
							tempList.add(temp);
						}
					}
				}
				input.setClinicList(tempList);
			}
		}
		
		if (!maxPrice.equals("unfiltered")) {
			Float price = Float.parseFloat(maxPrice);
			if (price != null) {
				List<ClinicPreviewDTO> tempList = new ArrayList<ClinicPreviewDTO> ();
				for (ClinicPreviewDTO temp : input.getClinicList()) {
					if (temp.getPrice().equals("/")) {
						continue;
					}
					Float tempFloat = Float.parseFloat(temp.getPrice().split(" ")[0]);
					if (tempFloat != null) {
						if (price >= tempFloat) {
							tempList.add(temp);
						}
					}
				}
				input.setClinicList(tempList);
			}
		}
		
		return input;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Float getIncome(String email, DatePeriodDTO datePeriod) {
		try {
			Long clinicId = adminRepository.findOneByEmail(email).getClinic().getId();
			
			Calendar start = Calendar.getInstance();
			start.setTime(datePeriod.getBeginDate());
			start.set(Calendar.HOUR_OF_DAY, 0);
			start.set(Calendar.MINUTE, 0);
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);
			
			Calendar end = Calendar.getInstance();
			end.setTime(datePeriod.getEndDate());
			end.set(Calendar.HOUR_OF_DAY, 0);
			end.set(Calendar.MINUTE, 0);
			end.set(Calendar.SECOND, 0);
			end.set(Calendar.MILLISECOND, 0);
			
			return repository.getIncome(clinicId, start, end);
		} catch(Exception e) {
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<HeldAppointmentsChartDataDTO> getDataForChart(String email, HeldAppointmentsRequestDTO request) {
		try {
			List<HeldAppointmentsChartDataDTO> dataList = new ArrayList<>();
			
			Calendar referentDateBegin = Calendar.getInstance();
			referentDateBegin.setTime(request.getReferentDate());
			referentDateBegin.set(Calendar.HOUR_OF_DAY, 0);
			referentDateBegin.set(Calendar.MINUTE, 0);
			referentDateBegin.set(Calendar.SECOND, 0);
			referentDateBegin.set(Calendar.MILLISECOND, 0);
			
			switch(request.getDetailLvl()) {
				case DAILY:

					Calendar referentDateEnd = Calendar.getInstance();
					referentDateEnd.setTime(request.getReferentDate());
					referentDateEnd.set(Calendar.HOUR_OF_DAY, 23);
					referentDateEnd.set(Calendar.MINUTE, 59);
					referentDateEnd.set(Calendar.SECOND, 59);
					referentDateEnd.set(Calendar.MILLISECOND, 59);
					dataList.addAll(createPointsForDaily(referentDateBegin));
					
					List<Date> finded = repository.findAllDoneAppointmentsInADatePeriod(referentDateBegin, referentDateEnd);
					
					boolean flagAddToEnd;
					for(Date date : finded) {
						flagAddToEnd = true;
						for(HeldAppointmentsChartDataDTO point : dataList) {
							if(point.getX().equals(date)) {
								point.setY(point.getY()+1);
								flagAddToEnd = false;
								break;
							}
						}
						if(flagAddToEnd) {
							dataList.add(new HeldAppointmentsChartDataDTO(
												date,
												1));
						}
					}
					
					Collections.sort(dataList, (o1, o2) -> o1.getX().compareTo(o2.getX()));
					
					break;
				case WEEKLY:

					int dayOfWeek = referentDateBegin.get(Calendar.DAY_OF_WEEK);
					Calendar firstDayOfWeek = (Calendar) referentDateBegin;
					firstDayOfWeek.add(Calendar.DAY_OF_MONTH, -(dayOfWeek-1));
					
					Calendar lastDayOfWeek = (Calendar) firstDayOfWeek.clone();
					lastDayOfWeek.add(Calendar.DAY_OF_MONTH, 7);
					lastDayOfWeek.set(Calendar.HOUR_OF_DAY, 23);
					lastDayOfWeek.set(Calendar.MINUTE, 59);
					lastDayOfWeek.set(Calendar.SECOND, 59);
					lastDayOfWeek.set(Calendar.MILLISECOND, 59);
					
					dataList.addAll(createPointsForWeekly(firstDayOfWeek));
					
					List<Date> findedAppointmentsInSameWeek = repository.findAllDoneAppointmentsInADatePeriod(firstDayOfWeek, lastDayOfWeek);
					for(Date findedDate : findedAppointmentsInSameWeek) {
						System.out.println("petlja po "+findedDate.toString());
						for(HeldAppointmentsChartDataDTO weekDay : dataList) {
							Calendar weekDayCalendar = Calendar.getInstance();
							Calendar findedDateCalendar = Calendar.getInstance();
							weekDayCalendar.setTime(weekDay.getX());
							findedDateCalendar.setTime(findedDate);
							
							if(weekDayCalendar.get(Calendar.DAY_OF_YEAR) == findedDateCalendar.get(Calendar.DAY_OF_YEAR)) {
								weekDay.setY(weekDay.getY()+1);
								System.out.println(weekDay.getX().toString()+ " "+ weekDay.getY());
							}
						}
					}
					
					break;
				default:
					referentDateBegin.set(Calendar.DAY_OF_MONTH, 1);//prvi dan u mesecu
					referentDateEnd = (Calendar) referentDateBegin.clone();
					referentDateEnd.add(Calendar.MONTH, 1); //sledeci mesec
					referentDateEnd.add(Calendar.DAY_OF_YEAR, -1);//poslednji dan u mesecu
					referentDateEnd.set(Calendar.HOUR_OF_DAY, 23);
					referentDateEnd.set(Calendar.MINUTE, 59);
					referentDateEnd.set(Calendar.SECOND, 59);
					referentDateEnd.set(Calendar.MILLISECOND, 59);
					
					List<Date> findedDateMonth = repository.findAllDoneAppointmentsInADatePeriod(referentDateBegin, referentDateEnd);
					int numberOfWeeks = referentDateBegin.getActualMaximum(Calendar.WEEK_OF_MONTH);
					
					dataList.addAll(createPointsForMonthly(referentDateBegin, numberOfWeeks));
					
					for(Date findedDateMonthItem : findedDateMonth) {
						for(HeldAppointmentsChartDataDTO item : dataList) {
							Calendar findedDateMonthItemCal = Calendar.getInstance();
							findedDateMonthItemCal.setTime(findedDateMonthItem);
							Calendar itemCal = Calendar.getInstance();
							itemCal.setTime(item.getX());
							
							if(findedDateMonthItemCal.get(Calendar.WEEK_OF_MONTH) == itemCal.get(Calendar.WEEK_OF_MONTH)) {
								System.out.println(findedDateMonthItem.toString());
								item.setY(item.getY()+1);
							}
						}
					}
			}
			
			return dataList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<HeldAppointmentsChartDataDTO> createPointsForDaily(Calendar beginDate) {
		/**
		 * creates a list of poits for chart;
		 * creates 24 points(hours of day) and each has value of zero(no appointments);
		 * !! beginDate must be at 00:00:00 clock
		 * */
		List<HeldAppointmentsChartDataDTO> basicPoints = new ArrayList<>();
		
		Calendar datePoint = (Calendar) beginDate.clone();
		
		for(int index=0; index<24; ++index) {
			basicPoints.add(new HeldAppointmentsChartDataDTO(
									datePoint.getTime(),
									0));
			datePoint.add(Calendar.HOUR_OF_DAY, 1);
		}
		
		return basicPoints;
	}
	
	private List<HeldAppointmentsChartDataDTO> createPointsForWeekly(Calendar beginDate) {
		/**
		 * creates a list of poits for chart;
		 * creates 7 points(days of week) and each has value of zero(no appointments);
		 * !! beginDate must be SUNDAY
		 * */
		List<HeldAppointmentsChartDataDTO> basicPoints = new ArrayList<>();
		
		Calendar datePoint = (Calendar) beginDate.clone();
		
		for(int index=0; index<7; ++index) {
			basicPoints.add(new HeldAppointmentsChartDataDTO(
									datePoint.getTime(),
									0));
			datePoint.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		return basicPoints;
	}
	
	private List<HeldAppointmentsChartDataDTO> createPointsForMonthly(Calendar beginDate, int numberOfWeeks) {
		/**
		 * creates a list of poits for chart;
		 * creates numberOfWeek points and each has value of zero(no appointments);
		 * */
		List<HeldAppointmentsChartDataDTO> basicPoints = new ArrayList<>();
		
		Calendar datePoint = (Calendar) beginDate.clone();
		
		for(int index=0; index<numberOfWeeks; ++index) {
			basicPoints.add(new HeldAppointmentsChartDataDTO(
									datePoint.getTime(),
									0));
			datePoint.add(Calendar.WEEK_OF_MONTH, 1);
		}
		
		return basicPoints;
	}
	
}
