package com.ftn.dr_help.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.comon.DailySchedule;
import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.EmailCheck;
import com.ftn.dr_help.comon.Term;
import com.ftn.dr_help.comon.schedule.CalculateFirstFreeSchedule;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.DoctorListingDTO;
import com.ftn.dr_help.dto.DoctorProfileDTO;
import com.ftn.dr_help.dto.DoctorProfilePreviewDTO;
import com.ftn.dr_help.dto.MedicalStaffNameDTO;
import com.ftn.dr_help.dto.MedicalStaffProfileDTO;
import com.ftn.dr_help.dto.MedicalStaffSaveingDTO;
import com.ftn.dr_help.dto.PatientHealthRecordDTO;
import com.ftn.dr_help.dto.RequestedOperationScheduleDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.dto.business_hours.BusinessDayHoursDTO;
import com.ftn.dr_help.model.convertor.ConcreteUserDetailInterface;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.pojo.AllergyPOJO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.DoctorReviewPOJO;
import com.ftn.dr_help.model.pojo.HealthRecordPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.DoctorReviewRepository;
import com.ftn.dr_help.repository.LeaveRequestRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;
import com.ftn.dr_help.validation.PasswordValidate;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository repository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Autowired
	private PasswordValidate passwordValidate;
	
	@Autowired
	private ConcreteUserDetailInterface convertor;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	@Autowired
	private ProcedureTypeRepository procedureRepository;
	
	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	@Autowired
	private DateConverter dateConvertor;
	
	@Autowired
	private EmailCheck check;
	
	@Autowired
	private WorkScheduleAdapter workSchedule;

	@Autowired
	private DoctorReviewRepository doctorReviewRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired 
	private LeaveRequestService leaveRequestsService;
	
	@Autowired
	private NurseService nurseService;
	
	public List<DoctorProfileDTO> findAll(Long clinicID) {
		if(clinicID == null) {
			return null;
		}
		
		List<DoctorPOJO> finded = repository.findAllByClinic_id(clinicID);
		if(finded == null)
			return null;
		
		List<DoctorProfileDTO> ret = new ArrayList<DoctorProfileDTO>();
		for(DoctorPOJO doctor : finded) {
			if(!doctor.isDeleted()) {
				//logic delete
				if(doctor.isDeleted()) {
					continue;
				}
				
				ret.add(new DoctorProfileDTO(doctor));				
			}
		}
		
		if(ret.isEmpty()) {
			return null;
		}
		
		return ret;
	}
	
	public DoctorProfileDTO findOne(Long clinicID, Long doctorID) {
		if(clinicID == null || doctorID == null) {
			return null;
		}
		
		DoctorPOJO finded = repository.findById(doctorID).orElse(null);
		if(finded == null || finded.isDeleted() || !finded.getClinic().getId().equals(clinicID)) {
			return null;
		}
		
		//logic delete
		if(finded.isDeleted()) {
			return null;
		}
		
		return new DoctorProfileDTO(finded);
	}
	
	public MedicalStaffProfileDTO findByEmail(String email) {
		if(email == null) {
			return null;
		}
		
		DoctorPOJO finded = repository.findOneByEmail(email);
		
		if(finded == null) {
			return null;
		}
		
		//logic delete
		if(finded.isDeleted()) {
			return null;
		}
		
		return new MedicalStaffProfileDTO(finded);		
	}
	
	public DoctorPOJO findOne(Long id) {
		try {
			DoctorPOJO ret = repository.findById(id).orElse(null);
			
			//logic delete
			if(ret.isDeleted()) {
				return null;
			}
			
			return ret;
		}catch(Exception e) {
			return null;
		}
	}
	
	public MedicalStaffProfileDTO save(UserDetailDTO doctor, String email) {
		if(doctor == null) {
			return null;
		}
		
		DoctorPOJO current = repository.findOneByEmail(email);
		if(current == null)
			return null;
		
		//ProfileValidationInterface validate = new ProfileValidation();
		//ConcreteUserDetailInterface convertsToDoctor = new ConcreteUserDetail();
		//logic delete
		if(current.isDeleted()) {
			return null;
		}
		
		
		convertor.changeTo(current, doctor);
		repository.save(current);
				
		return new MedicalStaffProfileDTO(current);
	}
	
	public boolean changePassword(ChangePasswordDTO password, String email) {
		if(password == null) {
			return false;
		}
		
		DoctorPOJO finded = repository.findOneByEmail(email);
		if(finded == null)
			return false;
		
		//logic delete
		if(finded.isDeleted()) {
			return false;
		}
		
		if(passwordValidate.isValid(password, finded.getPassword())) {
			String encoded = encoder.getEncoder().encode(password.getNewPassword());
			finded.setPassword(encoded);
			finded.setMustChangePassword(false);
			repository.save(finded);
			return true;
		}
		
		return false;
	}
	
	public List<DoctorListingDTO> filterByClinicAndProcedureType (Long clinicId, String procedureType) {
		List<DoctorListingDTO> retVal = new ArrayList<DoctorListingDTO> ();
		List<DoctorPOJO> doctors =  repository.filterByClinicAndProcedureType(clinicId, procedureType);
		for (DoctorPOJO d : doctors) {
			System.out.println("For petlja u filteru po oba");
			//logic delete
			if(d.isDeleted()) {
				continue;
			}
			Float averageReview = doctorReviewRepository.getAverageReview(d.getId());
			DoctorListingDTO dl = new DoctorListingDTO (d);
			if (averageReview != null) {
				dl.setRating(averageReview.toString());
			}
			else {
				dl.setRating("/");
			}
			retVal.add(dl);
		}
		return retVal;
	}
	
	public List<DoctorListingDTO> getAllUnfiltered () {
		List<DoctorListingDTO> retVal = new ArrayList<DoctorListingDTO> ();
		List<DoctorPOJO> doctors = repository.findAll();
		for (DoctorPOJO d : doctors) {
			//logic delete
			if(d.isDeleted()) {
				continue;
			}
			Float averageReview = doctorReviewRepository.getAverageReview(d.getId());
			DoctorListingDTO dl = new DoctorListingDTO (d);
			if (averageReview != null) {
				dl.setRating(averageReview.toString());
			}
			else {
				dl.setRating("/");
			}
			retVal.add(dl);
		}
		return retVal;
	}
	
	public List<DoctorListingDTO> filterByClinic (Long clinicId) {
		List<DoctorListingDTO> retVal = new ArrayList<DoctorListingDTO> ();
		List<DoctorPOJO> doctors =  repository.findAllByClinic_id(clinicId);
		for (DoctorPOJO d : doctors) {
			//logic delete
			if(d.isDeleted()) {
				continue;
			}
			Float averageReview = doctorReviewRepository.getAverageReview(d.getId());
			DoctorListingDTO dl = new DoctorListingDTO (d);
			if (averageReview != null) {
				dl.setRating(averageReview.toString());
			}
			else {
				dl.setRating("/");
			}
			retVal.add(dl);
		}
		return retVal;
	}
	
	public DoctorProfilePreviewDTO getProfilePreview (Long doctorId, Long patientId) {
		DoctorPOJO doctor = getIfNotDeleted(doctorId);
		if (doctor == null) {
			return null;
		}
		//logic delete
		if(doctor.isDeleted()) {
			return null;
		}
		
		DoctorProfilePreviewDTO retVal = new DoctorProfilePreviewDTO (doctor);
		List<AppointmentPOJO> appointments = appointmentRepository.getPatientsPastAppointments(patientId, doctorId);
		if (appointments.size() > 0) {
			retVal.setHaveInteracted(true);
			DoctorReviewPOJO djp = doctorReviewRepository.getPatientsReview(patientId, doctorId);
			if (djp != null) {
				retVal.setMyRating(djp.getRating().toString());
			}
		}
		Float rating = doctorReviewRepository.getAverageReview(doctorId);
		if (rating != null) {
			retVal.setRating(rating.toString());
		}

		return retVal;
	}
	
//	@Transactional (isolation = Isolation.READ_UNCOMMITTED)
	@Lock(LockModeType.PESSIMISTIC_READ)
	public DoctorPOJO getIfNotDeleted (Long doctorID) {
		DoctorPOJO doc = repository.getOne(doctorID);
		
		if (!doc.isDeleted()) {
			return doc;
		}
		
		return null;
	}
	
	public PatientHealthRecordDTO findPatientHealthRecord(Long appointmentId) {
		
		AppointmentPOJO app = appointmentRepository.findOneById(appointmentId);
		
		if(app == null) {
			System.out.println("Appointment with id: " + appointmentId+ " not found.");
			return null;
		}
		
		PatientPOJO patient = app.getPatient();
		
		if(patient == null) {
			System.out.println("Patient from appointment with id: " + appointmentId + " not found");
			return null;
		}
		
		HealthRecordPOJO healthRecord = patient.getHealthRecord();
		List<AllergyPOJO> allergies = new ArrayList<AllergyPOJO> ();
		if (healthRecord != null) {
			allergies= healthRecord.getAllergyList();
		}
		ArrayList<String> list = new ArrayList<String>();
		
		for (AllergyPOJO allergy : allergies) {
			list.add(allergy.getAllergy());
		}

		
		PatientHealthRecordDTO retVal = new PatientHealthRecordDTO();
		
		retVal.setBirthday(patient.getBirthday().getTime());
		if (healthRecord != null) {
			retVal.setHeight(healthRecord.getHeight());
			retVal.setBloodType(healthRecord.getBloodType());
			retVal.setDiopter(healthRecord.getDiopter());
			retVal.setWeight(healthRecord.getWeight());
		}
		retVal.setFirstName(patient.getFirstName());
		retVal.setLastName(patient.getLastName());
		retVal.setAllergyList(list);
		
		System.out.println("FIRSTNAME: " + retVal.getFirstName() + "BIRTHDAY: " + retVal.getBirthday() + " BLOODTYPE: " + retVal.getBloodType() + "ALLERGYLIST: " + retVal.getAllergyList());
		
		return retVal;
	}

	public boolean save(MedicalStaffSaveingDTO newDoctorDTO, String email) {
		try {
			ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
			ClinicPOJO clinic = admin.getClinic();
			
			if(!check.checkIfValid(newDoctorDTO.getEmail())) {
				return false;
			}
			
			ProceduresTypePOJO procedureType = procedureRepository.getOne(newDoctorDTO.getProcedureId());
			
			if(!clinic.getProcedureTypesList().contains(procedureType)) {
				return false;
			}
			
			DoctorPOJO newDoctor = new DoctorPOJO();
			newDoctor.setFirstName(newDoctorDTO.getFirstName());
			newDoctor.setLastName(newDoctorDTO.getLastName());
			newDoctor.setEmail(newDoctorDTO.getEmail());
			newDoctor.setAddress("...");
			newDoctor.setCity("...");
			newDoctor.setState("...");
			newDoctor.setPhoneNumber("...");
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(newDoctorDTO.getBirthday());
			newDoctor.setBirthday(birthday);
			newDoctor.setClinic(clinic);
			newDoctor.setProcedureType(procedureType);
			newDoctor.setMonday(newDoctorDTO.getMonday());
			newDoctor.setTuesday(newDoctorDTO.getTuesday());
			newDoctor.setWednesday(newDoctorDTO.getWednesday());
			newDoctor.setThursday(newDoctorDTO.getThursday());
			newDoctor.setFriday(newDoctorDTO.getFriday());
			newDoctor.setSaturday(newDoctorDTO.getSaturday());
			newDoctor.setSunday(newDoctorDTO.getSunday());
			newDoctor.setDeleted(false);
			newDoctor.setMustChangePassword(true);
			
			String encoded = encoder.getEncoder().encode("DoctorHelp");
			newDoctor.setPassword(encoded);
	
			repository.save(newDoctor);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean delete(Long id) {
		try {
			
			DoctorPOJO doctor = repository.findById(id).orElse(null);
			
			doctor.setDeleted(true);
			repository.save(doctor);
			
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public List<DoctorListingDTO> filterByClinicDateProcedureType (Long clinicId, String procedureType, String dateString) throws ParseException {
		List<DoctorListingDTO> retVal = new ArrayList<DoctorListingDTO> ();
		
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyyy-MM-dd hh:mm:ss");
		String dateMinString = dateString + " 00:00:00";
		String dateMaxString = dateString + " 23:59:59";
		Date dateMin = sdf.parse (dateMinString);
		Date dateMax = sdf.parse (dateMaxString);
		
		Calendar calendarMin = Calendar.getInstance ();
		Calendar calendarMax = Calendar.getInstance ();
		
		calendarMin.setTime(dateMin);
		calendarMax.setTime(dateMax);
		
		
		
		List<DoctorPOJO> doctors = repository.filterByClinicAndProcedureType(clinicId, procedureType);
		for (DoctorPOJO d : doctors) {
			if (d.isDeleted()) {
				continue;
			}
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
			List<AppointmentPOJO> appointments = appointmentRepository.getDoctorsAppointments(d.getId(), calendarMin, calendarMax);
			for (AppointmentPOJO a : appointments) {
				schedule.addAppointment(a);
			}
			DoctorListingDTO temp = new DoctorListingDTO (d);
			Float averageRating = doctorReviewRepository.getAverageReview (d.getId());
			if (averageRating != null) {
				temp.setRating(averageRating.toString());
			}
			else {
				temp.setRating ("/");
			}
			
			List<Term> terms = schedule.getAvaliableTerms(d.getProcedureType());
			List<String> times = new ArrayList<String> ();
			for (Term t : terms) {
				String tempTime = "";
				tempTime += t.getTime().get(Calendar.HOUR_OF_DAY);
				tempTime += ":";
				tempTime += t.getTime().get(Calendar.MINUTE);
				times.add(tempTime);
			}
			
			temp.setTerms(times);
			
			retVal.add(temp);
		}
		
		
		
		return retVal;
	}
	
	@Transactional
	public void addReview (Long doctorId, Long patientId, Integer review) {
		try {
			DoctorReviewPOJO newReview = new DoctorReviewPOJO(repository.getOne(doctorId), patientRepository.getOne(patientId), review);
			DoctorReviewPOJO oldReview = doctorReviewRepository.getPatientsReview(patientId, doctorId);
			if (review == 0) {
				doctorReviewRepository.delete(oldReview);
				return;
			}
			else if (oldReview == null) {
				System.out.println("Dodajem novi review");
				doctorReviewRepository.save(newReview);
			}
			else {
				doctorReviewRepository.updateReview(review, patientId, doctorId);
			}
		}catch(Exception e) {
			return;
		}
	}
	
	public String findFirstFreeSchedue(String email) {
		
		try {
			
			DoctorPOJO doctor = repository.findOneByEmail(email);
			List<Date> dates = repository.findAllReservedAppointments(doctor.getId());
			Calendar begin = Calendar.getInstance();
			begin.add(Calendar.DAY_OF_MONTH, 1);
			begin.clear(Calendar.SECOND);
			begin.clear(Calendar.MILLISECOND);
			
			List<AbsenceInnerDTO> absenceDates = leaveRequestsService.getAllDoctorAbsence(doctor.getId());
			Calendar firstFree = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(doctor), begin, dates, absenceDates);
			
			return dateConvertor.dateForFrontEndString(firstFree);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Calendar checkSchedue(String email, Calendar requestedSchedule) {
		
		DoctorPOJO doctor = repository.findOneByEmail(email);
		List<Date> reservedDates = repository.findAllReservedAppointments(doctor.getId());
		List<AbsenceInnerDTO> absenceDates = leaveRequestsService.getAllDoctorAbsence(doctor.getId());
		
		return calculate.checkScheduleOrFindFirstFree(workSchedule.fromDoctor(doctor), requestedSchedule, reservedDates, absenceDates);
	}
	
	@Transactional(readOnly = true)
	public List<MedicalStaffNameDTO> getSpecializedDoctors(Long procedureTypeId) {
		try {
			
			List<DoctorPOJO> finded = repository.findAllDoctorsWihtSpetialization(procedureTypeId);
			List<MedicalStaffNameDTO> doctors = new ArrayList<>();
			
			if(finded == null) {
				return doctors;
			}
			
			for(DoctorPOJO doctor : finded) {
				doctors.add(new MedicalStaffNameDTO(
							doctor.getId(),
							doctor.getFirstName(),
							doctor.getLastName(),
							doctor.getEmail()
						));
			}
			
			return doctors;
		} catch(Exception e) {
			System.out.println("ZASTO");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<MedicalStaffNameDTO> getSpecializedDoctorsWithoutNurse(Long procedureTypeId) {
		try {
			List<DoctorPOJO> finded = repository.findAllDoctorsWihtSpetialization(procedureTypeId);
			List<MedicalStaffNameDTO> doctors = new ArrayList<>();
			
			if(finded == null) {
				return doctors;
			}
			
			for(DoctorPOJO doctor : finded) {
				if(nurseService.hasANurseThatWorks(workSchedule.fromDoctor(doctor), doctor.getClinic().getId())) {
					doctors.add(new MedicalStaffNameDTO(
								doctor.getId(),
								doctor.getFirstName(),
								doctor.getLastName(),
								doctor.getEmail()
							));
				}
			}
			
			return doctors;
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public List<String> getAdminsMail(String drMail) {
		List<String> adminMails = repository.findAllClinicAdminMails(drMail);
		
		return adminMails;
	}
	
	public String getDoctorsFullName(String email) {
		
		DoctorPOJO dr = repository.findOneByEmail(email);
		
		return dr.getFirstName() + " " + dr.getLastName();
	}
	
	public List<RequestedOperationScheduleDTO> getOperationRequests(String email) {
		try {
			
			List<OperationPOJO> operations = operationRepository.getAllOperationRequests(email);
			System.out.println(operations.get(0).getOperationType().getName());
			
			List<RequestedOperationScheduleDTO> finded = new ArrayList<>();
			for(OperationPOJO operation : operations) {
				finded.add(new RequestedOperationScheduleDTO(
						operation.getId(),
						operation.getOperationType().getName(), 
						(operation.getPatient().getFirstName() +" "+ operation.getPatient().getLastName()), 
						dateConvertor.dateForFrontEndString(operation.getDate()), 
						("dr. "+ operation.getFirstDoctor().getFirstName() +" "+ operation.getFirstDoctor().getLastName()), 
						("dr. "+ operation.getSecondDoctor().getFirstName() +" "+ operation.getSecondDoctor().getLastName()), 
						("dr. "+ operation.getThirdDoctor().getFirstName() +" "+ operation.getThirdDoctor().getLastName()), 
						operation.getStatus().toString()));
			}
			
			return finded;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean getOperationRequestsCount(String email) {
		try {
			
			List<OperationPOJO> operations = operationRepository.getAllOperationRequests(email);
			
			if(operations == null || operations.isEmpty()) {
				return false;
			} else {
				return true;
			}
			
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<BusinessDayHoursDTO> getDoctorBusinessHours(Long doctor_id) { //metoda racuna smene i prikazuje ih na kalendaru u prikladnom json formatu
		//za primer formata, udji u BusinessDayHoursDTO
		DoctorPOJO doctor = repository.findOneById(doctor_id);
		
		List<BusinessDayHoursDTO> businessDayList = new ArrayList<BusinessDayHoursDTO>();
		
		
		if(!doctor.getMonday().toString().equals("NONE")) { //ako radi ponedeljkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(1); //1 == Monday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getMonday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getMonday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getMonday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		if(!doctor.getTuesday().toString().equals("NONE")) { //ako radi utorkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(2); //2 == Tuesday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getTuesday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getTuesday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getTuesday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!doctor.getWednesday().toString().equals("NONE")) { //ako radi sredom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(3); //3 == Wednesday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getWednesday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getWednesday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getWednesday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!doctor.getThursday().toString().equals("NONE")) { //ako radi cetvrtkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(4); //4 == Thursday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getThursday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getThursday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getThursday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		

		if(!doctor.getFriday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(5); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getFriday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getFriday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getFriday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!doctor.getSaturday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(6); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getSaturday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getSaturday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getSaturday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!doctor.getSunday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(0); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(doctor.getSunday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(doctor.getSunday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(doctor.getSunday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		return businessDayList;
		
	}
	
	public List<DoctorPOJO> getAllDoctorsFromClinicWithSpecialization(Long clinicId, Long procedureId) {
		try {
			return repository.getAllDoctorsFromClinicWithSpecialization(clinicId, procedureId);
		} catch(Exception e) {
			return null;
		}
	}
}
