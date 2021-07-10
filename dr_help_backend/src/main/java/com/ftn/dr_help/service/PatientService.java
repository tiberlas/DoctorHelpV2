package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.HealthRecordDTO;
import com.ftn.dr_help.dto.MedicationDisplayDTO;
import com.ftn.dr_help.dto.PatientDTO;
import com.ftn.dr_help.dto.PatientFilterDTO;
import com.ftn.dr_help.dto.PatientHealthRecordDTO;
import com.ftn.dr_help.dto.PatientHistoryDTO;
import com.ftn.dr_help.dto.PatientNameDTO;
import com.ftn.dr_help.dto.PatientProfileDTO;
import com.ftn.dr_help.dto.PerscriptionDisplayDTO;
import com.ftn.dr_help.model.enums.BloodTypeEnum;
import com.ftn.dr_help.model.pojo.AllergyPOJO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DiagnosisPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ExaminationReportPOJO;
import com.ftn.dr_help.model.pojo.HealthRecordPOJO;
import com.ftn.dr_help.model.pojo.MedicationPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.PerscriptionPOJO;
import com.ftn.dr_help.model.pojo.TherapyPOJO;
import com.ftn.dr_help.model.pojo.UserRequestPOJO;
import com.ftn.dr_help.repository.AllergyRepository;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.ExaminationReportRepository;
import com.ftn.dr_help.repository.HealthRecordRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.repository.PatientRepository;
import com.ftn.dr_help.repository.UserRequestRepository;
import com.ftn.dr_help.validation.PasswordValidate;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private NurseRepository nurseRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private UserRequestRepository userRequestRepository;
	
	@Autowired
	private AllergyRepository allergyRepository;
	
	@Autowired
	private ExaminationReportRepository examinationReportRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private HealthRecordRepository healthRecordRepository;
	
	@Autowired
	private PasswordValidate passwordValidate;
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Autowired
	private OperationRepository operationRepository;
	
	private DateConverter dateConverter = new DateConverter ();
	
	
	public List<PatientNameDTO> findAllNames() {
		List<PatientPOJO> finded = patientRepository.findAll();
		
		if(finded == null) {
			return null;
		}
		
		List<PatientNameDTO> ret = new ArrayList<PatientNameDTO>();
		for(PatientPOJO patient : finded) {
			ret.add(new PatientNameDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getInsuranceNumber()));
		}
		
		return ret;
	}
	
	public PatientDTO findById(Long id) {
		if(id == null) {
			return null;
		}
		
		PatientPOJO finded = patientRepository.findById(id).orElse(null);
		
		if(finded == null) {
			return null;
		}
		
		return new PatientDTO(finded);
	}
	
	public List<UserRequestPOJO> findAllRequests() {
		return userRequestRepository.findAll();
	}
	
	public UserRequestPOJO findByEmail(String email) {
		return userRequestRepository.findByEmail(email);
	}
	
	
	public PatientPOJO findPatientByEmail(String email) {
		PatientPOJO retVal = patientRepository.findOneByEmail(email);
		
		return retVal;
	}
	
	
	public PatientPOJO findByInsuranceNumber (Long insuranceNumber) {
		return patientRepository.findByInsuranceNumber(insuranceNumber);
	}
	
	
	public void remove(UserRequestPOJO user) {
		userRequestRepository.deleteById(user.getId());
	}
	
	public PatientPOJO save(PatientPOJO patient) {
		return patientRepository.save(patient);
	}
	
	
	public List<PatientPOJO> findAll() {
		return patientRepository.findAll();
	}
	

	
	public PatientProfileDTO getPatientProfile (Long id) {
		PatientProfileDTO retVal = new PatientProfileDTO ();
		PatientPOJO pojo = patientRepository.getOne(id);
		
		if (pojo == null) {
			return null;
		}
		
		retVal.setId(pojo.getId());
		retVal.setEmail(pojo.getEmail());
		retVal.setFirstName(pojo.getFirstName());
		retVal.setLastName(pojo.getLastName());
		retVal.setAddress(pojo.getAddress());
		retVal.setCity(pojo.getCity());
		retVal.setState(pojo.getState());
		retVal.setPhoneNumber(pojo.getPhoneNumber());
		retVal.setBirthday(pojo.getBirthday());
		retVal.setInsuranceNumber(pojo.getInsuranceNumber());
		
		return retVal;
	}

	public PatientProfileDTO save(PatientProfileDTO profileUpdate, String email) {
		if (profileUpdate == null) {
			System.out.println("PRVI NULL");
			return null;
		}
		
		PatientPOJO current = patientRepository.findOneByEmail (email);
		if (current == null) {
			System.out.println("DRUGI NULL");
			return null;
		}
		
		current.setFirstName(profileUpdate.getFirstName ());
		current.setLastName(profileUpdate.getLastName ());
		current.setAddress (profileUpdate.getAddress());
		current.setCity(profileUpdate.getCity());
		current.setState(profileUpdate.getState());
		current.setPhoneNumber(profileUpdate.getPhoneNumber());
		
		patientRepository.save(current);
		return profileUpdate;
	}
	

	public List<PatientPOJO> singleFilterPatients(String filter) {
		List<PatientPOJO> patientList = patientRepository.findAll();
		
		//masan filter algorithm incoming
		ArrayList<PatientPOJO> filteredPatients = new ArrayList<PatientPOJO>();
		if (filter.matches("[0-9]+")) { //IS THE FILTER A NUMBER ONLY STRING ----> insurance search
			for (PatientPOJO patientPOJO : patientList) {
				if(patientPOJO.getInsuranceNumber().toString().contains(filter)) {
					filteredPatients.add(patientPOJO);				
				}
			}
			
			return filteredPatients;
			
		} else {
			String search = "";
			for (PatientPOJO patientPOJO : patientList) {
				search = patientPOJO.getFirstName().toLowerCase() + patientPOJO.getLastName().toLowerCase() + patientPOJO.getEmail().toLowerCase();
				if(search.contains(filter.toLowerCase())) {
					System.out.println("SEARCH IS: " + search);
					filteredPatients.add(patientPOJO);				
				}
			}
			
			return filteredPatients;
		}
	}
	

	public HealthRecordDTO getHealthRecord (String email) {
		PatientPOJO patient = patientRepository.findOneByEmail (email);
		if (patient == null) {
			System.out.println("Nisam pronasao pleba");
			return null;
		}
		
		HealthRecordPOJO healthRecord = patient.getHealthRecord();
		if (healthRecord == null) {
			System.out.println("Pleb nema karton");
			return null;
		}
		
		String allergyList = "";
		List<AllergyPOJO> allergies= allergyRepository.findAllByHealthRecordId(healthRecord.getId());
		int i = 0;
		for (; i < allergies.size() - 1; ++i) {
			allergyList += allergies.get(i).getAllergy() + ", ";
		}
		if (i > 0) {
			allergyList += allergies.get(i).getAllergy();
		} else if ((i == 0) && (allergies.size() == 1)) {
			allergyList = allergies.get(i).getAllergy();
		}
		if (allergyList.equals ("")) {
			allergyList = "/";
		}
		
		
		HealthRecordDTO retVal = new HealthRecordDTO ();
		
		retVal.setBirthday(dateConverter.toString(patient.getBirthday()));
		retVal.setBloodType(healthRecord.getBloodType());
		retVal.setDiopter(healthRecord.getDiopter());
		retVal.setFirstName(patient.getFirstName());
		retVal.setHeight(healthRecord.getHeight());
		retVal.setLastName(patient.getLastName());
		retVal.setWeight(healthRecord.getWeight());
		retVal.setAllergyList(allergyList);
		
		return retVal;
	}
	
	public AppointmentListDTO getHistory (String email, String dateFilter, String doctorId, String clinicId, String procedureTypeId) {
		PatientPOJO patient = patientRepository.findOneByEmail(email);
		HealthRecordPOJO healthRecord = patient.getHealthRecord();
		if (healthRecord == null) {
			return null;
		}
		List<ExaminationReportPOJO> examinationReports = examinationReportRepository.findAllByHealthRecordId(patient.getHealthRecord().getId());
		
		AppointmentListDTO retVal = new AppointmentListDTO();
		List<PatientHistoryDTO> appointments= new ArrayList<PatientHistoryDTO>();
		for (ExaminationReportPOJO report : examinationReports) {
			PatientHistoryDTO retValItem = new PatientHistoryDTO ();
			AppointmentPOJO appointment = report.getAppointment();
			retValItem.setExaminationReportId(report.getId());
			
			String date = "" + appointment.getDate().get(Calendar.DAY_OF_MONTH) + ".";
			date += (appointment.getDate().get(Calendar.MONTH) + 1) + ".";
			date += appointment.getDate().get(Calendar.YEAR) + ". ";
			date += appointment.getDate().get(Calendar.HOUR_OF_DAY) + ":";
			date += appointment.getDate().get(Calendar.MINUTE);
			
			retValItem.setDate(date);
			retValItem.setProcedureType(appointment.getProcedureType().getName());
			retValItem.setDoctor(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
			retValItem.setNurse(appointment.getNurse().getFirstName() + " " + appointment.getNurse().getLastName());
			retValItem.setClinicName(report.getClinic().getName());
			retValItem.setClinicId(report.getClinic().getId());
			retValItem.setDoctorId(appointment.getDoctor().getId());
			appointments.add(retValItem);
		}
		
		List<OperationPOJO> operations;
		operations = operationRepository.getPatientsDoneOperations(patient.getId(), Calendar.getInstance());
		
		for (OperationPOJO o : operations) {
			PatientHistoryDTO retValItem = new PatientHistoryDTO();
			
			retValItem.setExaminationReportId(o.getId());
			
			String date = "" + o.getDate().get(Calendar.DAY_OF_MONTH) + ".";
			date += (o.getDate().get(Calendar.MONTH) + 1) + ".";
			date += o.getDate().get(Calendar.YEAR) + ". ";
			date += o.getDate().get(Calendar.HOUR_OF_DAY) + ":";
			date += o.getDate().get(Calendar.MINUTE);
			
			retValItem.setDate(date);
			retValItem.setProcedureType(o.getOperationType().getName());
			retValItem.setDoctor(o.getFirstDoctor().getFirstName() + " " + o.getFirstDoctor().getLastName());
			retValItem.setClinicName(o.getOperationType().getClinic().getName());
			retValItem.setClinicId(o.getOperationType().getClinic().getId());
			retValItem.setDoctorId(o.getFirstDoctor().getId());
			appointments.add(retValItem);
		}
		
		if (appointments.size() > 0) {
			retVal.setAppointmentList(appointments);
			
			List<String> dateList = new ArrayList<String> ();
			dateList.add("unfiltered");
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				boolean isThere = false;
				for (String str : dateList) {
					if (str.split(" ")[0].equals(p.getDate().split(" ")[0])) {
						isThere = true;
						break;
					}
				}
				if (!isThere) {
					dateList.add(p.getDate().split(" ")[0]);
				}
			}
			
			System.out.println("");
			System.out.println("");
			for (String date : dateList) {
				System.out.println("Date: " + date);
			}
			System.out.println("");
			System.out.println("");
			
			retVal.setPossibleDates (dateList);
			
			List<String> doctorList = new ArrayList<String> ();
			doctorList.add("unfiltered");
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				boolean isThere = false;
				for (String str : doctorList) {
					if (str.equals(p.getDoctor())) {
						isThere = true;
						break;
					}
				}
				if (!isThere) {
					doctorList.add(p.getDoctor ());
				}
			}
			retVal.setPossibleDoctors(doctorList);
			
			List<String> clinicList = new ArrayList<String> ();
			clinicList.add("unfiltered");
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				boolean isThere = false;
				for (String str : clinicList) {
					if (str.equals (p.getClinicName())) {
						isThere = true;
						break;
					}
				}
				if (!isThere) {
					clinicList.add(p.getClinicName());
				}
			}
			retVal.setPossibleClinics(clinicList);
			
			List<String> typeList = new ArrayList<String> ();
			typeList.add("unfiltered");
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				boolean isThere = false;
				for (String str : typeList) {
					if (str.equals(p.getProcedureType())) {
						isThere = true;
						break;
					}
				}
				if (!isThere) {
					typeList.add(p.getProcedureType());
				}
			}
			retVal.setPossibleTypes(typeList);
			
			if (!dateFilter.equals("unfiltered")) {
				List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
				for (PatientHistoryDTO p : retVal.getAppointmentList()) {
					if (p.getDate().split(" ")[0].equals(dateFilter + ".")) {
						tempList.add(p);
					}
				}
				retVal.setAppointmentList(tempList);
			}
			
			if (!doctorId.equals("unfiltered")) {
				List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
				for (PatientHistoryDTO p : retVal.getAppointmentList()) {
					if (p.getDoctor().equals(doctorId)) {
						tempList.add(p);
					}
				}
				retVal.setAppointmentList(tempList);
			}
			
			if (!clinicId.contentEquals(("unfiltered"))) {
				List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
				for (PatientHistoryDTO p : retVal.getAppointmentList()) {
					if (p.getClinicName().equals(clinicId)) {
						tempList.add(p);
					}
				}
				retVal.setAppointmentList(tempList);
			}
			
			if (!procedureTypeId.equals("unfiltered")) {
				List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
				for (PatientHistoryDTO p : retVal.getAppointmentList()) {
					if (p.getProcedureType().equals(procedureTypeId)) {
						tempList.add(p);
					}
				}
				retVal.setAppointmentList(tempList);
			}
			
			return retVal;
		}
		retVal.setAppointmentList(null);
		return retVal;
	}
	
	public PerscriptionDisplayDTO getPerscription (Long examinationReportId) {
		PerscriptionDisplayDTO retVal = new PerscriptionDisplayDTO ();
		ExaminationReportPOJO examinationReport = examinationReportRepository.getOne(examinationReportId);
		if (examinationReport == null) {
			return null;
		}
		
		PerscriptionPOJO perscription = examinationReport.getPerscription();
		if (perscription == null) {
			return null;
		}
		
		DiagnosisPOJO diagnosis = perscription.getDiagnosis();
		if (diagnosis != null) {
			retVal.setDiagnosis(diagnosis.getDiagnosis());
			retVal.setDescription(diagnosis.getDescription());
		} else {
			retVal.setDiagnosis("");
			retVal.setDescription("");
		}
		
		TherapyPOJO therapy = perscription.getTherapy();
		if (therapy != null) {
			retVal.setAdvice(therapy.getAdvice());
		} else {
			retVal.setAdvice("");
		}
		
		ClinicPOJO clinic = examinationReport.getClinic();
		if (clinic != null) {
			retVal.setClinicId(clinic.getId());
		}
		
		List<MedicationPOJO> medicationList = perscription.getMedicationList(); //izdebagovao sam ti liste lekova, zamenio si u modelu mappedBy redosled
			//perscription je owner veza, a ne medicationPOJO, pa je obrnut mappedBy u manytomany vezi. sad ti nece trenutno raditi
			//ovaj tvoj sql za prvi examination report
		
		List<MedicationDisplayDTO> medicationDto = new ArrayList<MedicationDisplayDTO> ();
		for (MedicationPOJO temp : medicationList) {
			MedicationDisplayDTO currentMedication = new MedicationDisplayDTO ();
			currentMedication.setMedicationName(temp.getMedicationName());
			currentMedication.setMedicationDescription(temp.getMedDescription());
			medicationDto.add(currentMedication);
		}
		if (medicationDto.size() == 0) {
			medicationDto.add(new MedicationDisplayDTO ("-", "-"));
		}
		retVal.setMedicationList(medicationDto);
		
		return retVal;
	}

	public boolean changePassword(ChangePasswordDTO password, String email) {
		if(password == null) {
			return false;
		}
		
		PatientPOJO finded = patientRepository.findOneByEmail(email);
		if(finded == null)
			return false;
		
		if(passwordValidate.isValid(password, finded.getPassword())) {

			String encoded = encoder.getEncoder().encode(password.getNewPassword());

			finded.setPassword(encoded);
			patientRepository.save(finded);
			return true;
		}
		
		return false;
	}
	
	//update health record
	public PatientHealthRecordDTO findHealthRecordByInsuranceNumber(Long insuranceNumber, PatientHealthRecordDTO dto) {
		PatientPOJO patient = patientRepository.findByInsuranceNumber(insuranceNumber);
		HealthRecordPOJO recordPOJO = patient.getHealthRecord();
		
		System.out.println("karton:" + patient.getHealthRecord().getBloodType());
		System.out.println("dto:" + dto.getAllergyList());
		
		List<AllergyPOJO> existingAllergies = allergyRepository.findAllByHealthRecordId(recordPOJO.getId());
		if(!existingAllergies.isEmpty()) //ako su vec postojale alergije za tog pacijenta, obrisi ih
			allergyRepository.deleteInBatch(existingAllergies);
		
		ArrayList<AllergyPOJO> list = new ArrayList<AllergyPOJO>(); //dodaj nove alergije, koje je doca prosledio
		for(String a : dto.getAllergyList()) {
				AllergyPOJO new_allergy = new AllergyPOJO();
				new_allergy.setAllergy(a.trim());
				new_allergy.setHealthRecord(recordPOJO);
				allergyRepository.save(new_allergy);
				System.out.println("saved allergy" + new_allergy.getAllergy());
				list.add(new_allergy);
		}
		
		recordPOJO.setAlergyList(list);
		
		System.out.println("blood type:"+ dto.getBloodType());
		recordPOJO.setBloodType(dto.getBloodType());
		recordPOJO.setDiopter(dto.getDiopter());
		recordPOJO.setHeight(dto.getHeight());
		recordPOJO.setWeight(dto.getWeight());
		
		patient.setHealthRecord(recordPOJO);
		healthRecordRepository.save(recordPOJO);
		patientRepository.save(patient);
		
		return dto;
	}
	
	

	public List<PatientDTO> findAllfilter(PatientFilterDTO filter) {
		
		try {
			
			List<PatientPOJO> findedPOJO = findAll();
			List<PatientDTO> finded = new ArrayList<>();
			for(PatientPOJO patient : findedPOJO) {
				finded.add(new PatientDTO(patient));
			}
			
			if(filter.getFilterResults().isEmpty()) {
				return finded;
			}
			
			List<PatientDTO> ret = new ArrayList<PatientDTO>();
			
			String search = "";
			for(PatientDTO patient : finded) {
				search = patient.getFirstName().toLowerCase() + patient.getLastName().toLowerCase() + patient.getEmail().toLowerCase() + String.valueOf(patient.getInsuranceNumber());
				if(search.contains(filter.getFilterResults().toLowerCase())) {
					ret.add(patient);
				}
			}
			
			return ret;
		} catch(Exception e) {
			return null;
		}
	}

	public AppointmentListDTO getPending(String email, String date, String doctorId, String clinicId, String procedureTypeId, String appointmentStatus) {
		
		AppointmentListDTO retVal = new AppointmentListDTO ();
		List<AppointmentPOJO> appointments = appointmentRepository.getPatientsPendingAppointments(patientRepository.findOneByEmail(email).getId());
		List<PatientHistoryDTO> historyList = new ArrayList<PatientHistoryDTO> ();
		for (AppointmentPOJO a : appointments) {
			PatientHistoryDTO temp = new PatientHistoryDTO (a);
			historyList.add(temp);
		}
		retVal.setAppointmentList(historyList);
		
		List<String> dateList = new ArrayList<String> ();
		dateList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : dateList) {
				if (str.split(" ")[0].equals(p.getDate().split(" ")[0])) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				dateList.add(p.getDate().split(" ")[0]);
			}
		}
		retVal.setPossibleDates(dateList);
		
		List<String> doctorList = new ArrayList<String> ();
		doctorList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : doctorList) {
				if (str.equals(p.getDoctor())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				doctorList.add(p.getDoctor());
			}
		}
		retVal.setPossibleDoctors(doctorList);
		
		List<String> clinicList = new ArrayList<String> ();
		clinicList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : clinicList) {
				if (str.equals (p.getClinicName())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				clinicList.add(p.getClinicName());
			}
		}
		retVal.setPossibleClinics(clinicList);
		
		List<String> typeList = new ArrayList<String> ();
		typeList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : typeList) {
				if (str.equals (p.getProcedureType())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				typeList.add(p.getProcedureType());
			}
		}
		retVal.setPossibleTypes(typeList);
		
		List<String> stateList = new ArrayList<String> ();
		stateList.add("unfiltered");
		for (PatientHistoryDTO p : retVal.getAppointmentList()) {
			boolean isThere = false;
			for (String str : stateList) {
				if (str.equals (p.getStatus())) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				stateList.add(p.getStatus());
			}
		}
		retVal.setPossibleStatuses(stateList);
		
		if (!date.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getDate().split(" ")[0].equals(date)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!doctorId.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getDoctor().equals(doctorId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!clinicId.contentEquals(("unfiltered"))) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getClinicName().equals(clinicId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!procedureTypeId.equals("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getProcedureType().equals(procedureTypeId)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		if (!appointmentStatus.equals ("unfiltered")) {
			List<PatientHistoryDTO> tempList = new ArrayList<PatientHistoryDTO> ();
			for (PatientHistoryDTO p : retVal.getAppointmentList()) {
				if (p.getStatus().equals(appointmentStatus)) {
					tempList.add(p);
				}
			}
			retVal.setAppointmentList(tempList);
		}
		
		return retVal;
	}
	
	
	public boolean showHealthRecord(String medicalStaffEmail, Long insuranceNumber) {
		
		DoctorPOJO doctor = doctorRepository.findOneByEmail(medicalStaffEmail); //protrci kroz tabele za docu i sestru po mejlu
		if(doctor != null) { //doca je
			PatientPOJO patient = patientRepository.findByInsuranceNumber(insuranceNumber);
			Integer count = doctorRepository.findDoneAppointmentForDoctorCount(doctor.getId(), patient.getId());
			if(count > 0) {
				System.out.println("Doctor count for health record display is larger than 0, can display health record");
				return true;
			}
		}
		
		NursePOJO nurse = nurseRepository.findOneByEmail(medicalStaffEmail);

		if(nurse != null) {
			PatientPOJO patient = patientRepository.findByInsuranceNumber(insuranceNumber);
			Integer count = nurseRepository.findDoneAppointmentForNurseCount(nurse.getId(), patient.getId());
			if(count > 0) {
				System.out.println("Nurse count for health record display is larger than 0, can display health record");
				return true;
			}
		}
		
		System.out.println("Can't display health record");
		return false;
	}
	
	
	public PatientHealthRecordDTO getPatientHealthRecordForMedicalStaff(Long insurance) {
		
		PatientPOJO patient = patientRepository.findByInsuranceNumber(insurance);
		
		if(patient == null) {
			System.out.println("Patient not found");
			return null;
		}
		
		HealthRecordPOJO healthRecord = patient.getHealthRecord();
		List<AllergyPOJO> allergies;
		ArrayList<String> list = new ArrayList<String>();
		
		
		if (healthRecord != null) {
			allergies = healthRecord.getAllergyList();
			for (AllergyPOJO allergy : allergies) {
				list.add(allergy.getAllergy());
			}
		}
		else {
			allergies = null;
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
		return retVal;
	}
	
	
	public PatientPOJO confirmAccount(String email){
		
		PatientPOJO p = patientRepository.findOneByEmail(email);
		
		if(p == null) {
			
			return null;
		}
		
		HealthRecordPOJO healthRecord = new HealthRecordPOJO();
		healthRecord.setDiopter(0); //generic health record
		healthRecord.setHeight(0);
		healthRecord.setWeight(0);
		healthRecord.setBloodType(BloodTypeEnum.O_POSITIVE);
		healthRecord.setAlergyList(new ArrayList<AllergyPOJO>());
		
		p.setActivated(true);
		p.setHealthRecord(healthRecord);
		
		patientRepository.save(p);
//		healthRecord.setPatient(p);
//		healthRecordRepository.save(healthRecord);
		
		System.out.println("Health record created.");
		return p;
	}
	
	
	
	
	
}
