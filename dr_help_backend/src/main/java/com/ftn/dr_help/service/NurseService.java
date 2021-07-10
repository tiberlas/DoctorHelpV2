package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.comon.AppPasswordEncoder;
import com.ftn.dr_help.comon.EmailCheck;
import com.ftn.dr_help.comon.schedule.CalculateFirstFreeSchedule;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.ChangePasswordDTO;
import com.ftn.dr_help.dto.MedicalStaffProfileDTO;
import com.ftn.dr_help.dto.MedicalStaffSaveingDTO;
import com.ftn.dr_help.dto.NurseCheckIfAvailableInnerDTO;
import com.ftn.dr_help.dto.NurseWIthFirstFreeDateInnerDTO;
import com.ftn.dr_help.dto.UserDetailDTO;
import com.ftn.dr_help.dto.business_hours.BusinessDayHoursDTO;
import com.ftn.dr_help.model.convertor.ConcreteUserDetailInterface;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.MedicalStaffWorkSchedularPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.NurseRepository;
import com.ftn.dr_help.validation.PasswordValidate;

@Service
public class NurseService {

	@Autowired
	private NurseRepository repository;
	
	@Autowired
	private AppPasswordEncoder encoder;
	
	@Autowired
	private PasswordValidate passwordValidate;
	
	@Autowired
	private ConcreteUserDetailInterface convertor;
	
	@Autowired
	private ClinicAdministratorRepository administatorRepository;
	
	@Autowired
	private EmailCheck check;
	
	@Autowired
	private WorkScheduleAdapter workSchedule;
	
	@Autowired
	private CalculateFirstFreeSchedule calculate;
	
	@Autowired
	private LeaveRequestService leaveRequestService;
	
	public List<MedicalStaffProfileDTO> getAll(String email) {
		
		try {
			List<MedicalStaffProfileDTO> nurseList = new ArrayList<MedicalStaffProfileDTO>();
			
			List<NursePOJO> nurses = administatorRepository.findOneByEmail(email).getClinic().getNurseList();
			for(NursePOJO nurse : nurses) {
				if(!nurse.isDeleted()) {
					nurseList.add(new MedicalStaffProfileDTO(nurse));
				}
			}
			
			return nurseList;
		} catch(Exception e) {
			return null;
		}
	}
	
	public MedicalStaffProfileDTO findById(Long id) {
		
		try {
			NursePOJO finded = repository.findOneById(id);
			
			MedicalStaffProfileDTO nurse = new MedicalStaffProfileDTO(finded);
			
			return nurse;
		} catch (Exception e) {
			return null;
		}
	}
	
	public MedicalStaffProfileDTO findByEmail(String email) {
		if(email == null) {
			return null;
		}
		
		NursePOJO finded = repository.findOneByEmail(email);
		
		if(finded == null) {
			return null;
		}
		
		//logic delete
		if(finded.isDeleted()) {
			return null;
		}
		
		return new MedicalStaffProfileDTO(finded);		
	}
	
	public NursePOJO findOne(Long id) {
		if(id == null) {
			return null;
		}
		
		NursePOJO ret = repository.findById(id).orElse(null);
		
		//logic delete
		if(ret.isDeleted()) {
			return null;
		}
		
		return ret;
	}
	
	public MedicalStaffProfileDTO save(UserDetailDTO nurse, String email) {
		if(nurse == null) {
			return null;
		}
		
		NursePOJO current = repository.findOneByEmail(email);
		if(current == null)
			return null;
		
		//logic delete
		if(current.isDeleted()) {
		return null;
		}
		
		convertor.changeTo(current, nurse);
		repository.save(current);
				
		return new MedicalStaffProfileDTO(current);
	}
	
	public boolean changePassword(ChangePasswordDTO password, String email) {
		if(password == null) {
			return false;
		}
		
		NursePOJO finded = repository.findOneByEmail(email);
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
	
	public boolean save(MedicalStaffSaveingDTO newNurseDTO, String email) {
		try {
			ClinicAdministratorPOJO admin = administatorRepository.findOneByEmail(email);
			ClinicPOJO clinic = admin.getClinic();
			
			if(!check.checkIfValid(newNurseDTO.getEmail())) {
				return false;
			}
			
			NursePOJO newNurse = new NursePOJO();
			newNurse.setFirstName(newNurseDTO.getFirstName());
			newNurse.setLastName(newNurseDTO.getLastName());
			newNurse.setEmail(newNurseDTO.getEmail());
			newNurse.setAddress("...");
			newNurse.setCity("...");
			newNurse.setState("...");
			newNurse.setPhoneNumber("...");
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(newNurseDTO.getBirthday());
			newNurse.setBirthday(birthday);
			newNurse.setClinic(clinic);
			newNurse.setMonday(newNurseDTO.getMonday());
			newNurse.setTuesday(newNurseDTO.getTuesday());
			newNurse.setWednesday(newNurseDTO.getWednesday());
			newNurse.setThursday(newNurseDTO.getThursday());
			newNurse.setFriday(newNurseDTO.getFriday());
			newNurse.setSaturday(newNurseDTO.getSaturday());
			newNurse.setSunday(newNurseDTO.getSunday());
			newNurse.setDeleted(false);
			newNurse.setMustChangePassword(true);
			
			String encoded = encoder.getEncoder().encode("DoctorHelp");
			newNurse.setPassword(encoded);
	
			repository.save(newNurse);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean delete(Long id) {
		try {
			
			NursePOJO nurse = repository.findById(id).orElse(null);
			
			nurse.setDeleted(true);
			repository.save(nurse);
			
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public List<BusinessDayHoursDTO> getNurseBusinessHours(Long doctor_id) { //metoda racuna smene i prikazuje ih na kalendaru u prikladnom json formatu
		//za primer formata, udji u BusinessDayHoursDTO
		NursePOJO nurse = repository.findOneById(doctor_id);
		
		List<BusinessDayHoursDTO> businessDayList = new ArrayList<BusinessDayHoursDTO>();
		
		
		if(!nurse.getMonday().toString().equals("NONE")) { //ako radi ponedeljkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(1); //1 == Monday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getMonday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getMonday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getMonday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		if(!nurse.getTuesday().toString().equals("NONE")) { //ako radi utorkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(2); //2 == Tuesday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getTuesday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getTuesday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getTuesday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!nurse.getWednesday().toString().equals("NONE")) { //ako radi sredom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(3); //3 == Wednesday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getWednesday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getWednesday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getWednesday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!nurse.getThursday().toString().equals("NONE")) { //ako radi cetvrtkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(4); //4 == Thursday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getThursday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getThursday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getThursday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		

		if(!nurse.getFriday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(5); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getFriday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getFriday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getFriday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		

		if(!nurse.getSaturday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(6); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getSaturday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getSaturday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getSaturday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		if(!nurse.getSunday().toString().equals("NONE")) { //ako radi petkom = Shift != NONE
			BusinessDayHoursDTO businessDayHoursDTO = new BusinessDayHoursDTO();
			List<Integer> day = new ArrayList<Integer>();	
			day.add(0); //5 == Friday
			businessDayHoursDTO.setDaysOfWeek(day);
			if(nurse.getSunday().toString().equals("FIRST")) { //ako radi prvu smenu
				businessDayHoursDTO.setStartTime("08:00");
				businessDayHoursDTO.setEndTime("16:00");
			} else if(nurse.getSunday().toString().equals("SECOND")) {
				businessDayHoursDTO.setStartTime("16:00");
				businessDayHoursDTO.setEndTime("24:00");
			} else if(nurse.getSunday().toString().equals("THIRD")) {
				businessDayHoursDTO.setStartTime("00:00");
				businessDayHoursDTO.setEndTime("08:00");
			}
			businessDayList.add(businessDayHoursDTO);
		}
		
		
		return businessDayList;	
	}
	
	public NursePOJO findOneByEmail(String email) {
		try {
			
			return repository.findOneByEmail(email);
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public NurseCheckIfAvailableInnerDTO checkSchedue(NursePOJO nurse, Calendar requestedSchedule, Calendar duration) {
		
		List<Date> dates = repository.findAllReservedAppointments(nurse.getId());
		List<AbsenceInnerDTO> absence = leaveRequestService.getAllNurseAbsence(nurse.getId());
		Calendar firstFree = calculate.checkScheduleOrFindFirstFree(workSchedule.fromNurse(nurse, duration), requestedSchedule, dates, absence);
		
		if(firstFree.getTime().equals(requestedSchedule.getTime())) {
			return new NurseCheckIfAvailableInnerDTO(true, requestedSchedule);
		} else {
			return new NurseCheckIfAvailableInnerDTO(false, firstFree);
		}
	}
	
	public NurseWIthFirstFreeDateInnerDTO findFreeNurse(Calendar appointmentDate, Calendar duration, Long clinicId) {
		try {
			
			List<NursePOJO> nurses = repository.findAllByClinic_id(clinicId);
			List<NurseWIthFirstFreeDateInnerDTO> nurseDates = new ArrayList<>();
			
			for(NursePOJO nurse : nurses) {
				NurseCheckIfAvailableInnerDTO check = checkSchedue(nurse, appointmentDate, duration);
				if(check.isFree()) {
					return new NurseWIthFirstFreeDateInnerDTO(appointmentDate, nurse);
				} else {
					nurseDates.add(new NurseWIthFirstFreeDateInnerDTO(check.getFirstFree(), nurse));
				}
			}
			
			//ni jedan sestra ne moze da radi za trazeni dan pa se daje prva slobodan sestra
			NurseWIthFirstFreeDateInnerDTO retVal = nurseDates.get(0);
			for(int i=0; i<nurseDates.size(); ++i) {
				for(int j=i+1; j<nurseDates.size(); ++j) {
					if(retVal.getFirstFreeDate().after(nurseDates.get(j).getFirstFreeDate())) {
						retVal = nurseDates.get(j);
					}
				}
			}
			
			return retVal;
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean hasANurseThatWorks(MedicalStaffWorkSchedularPOJO doctorsSchedule, Long clinicId) {
		List<NursePOJO> nurses = repository.findAllByClinic_id(clinicId);
		
		MedicalStaffWorkSchedularPOJO nurseSchedule = null;
		for(NursePOJO nurse : nurses) {
			nurseSchedule = workSchedule.fromNurse(nurse, null);
			
			if(!nurseSchedule.getMonday().equals(Shift.NONE) && doctorsSchedule.getMonday().equals(nurseSchedule.getMonday())) {
				return true;
			}
			if(!nurseSchedule.getTuesday().equals(Shift.NONE) && doctorsSchedule.getTuesday().equals(nurseSchedule.getTuesday())) {
				return true;
			}
			if(!nurseSchedule.getWednesday().equals(Shift.NONE) && doctorsSchedule.getWednesday().equals(nurseSchedule.getWednesday())) {
				return true;
			}
			if(!nurseSchedule.getThursday().equals(Shift.NONE) && doctorsSchedule.getThursday().equals(nurseSchedule.getThursday())) {
				return true;
			}
			if(!nurseSchedule.getFriday().equals( Shift.NONE) && doctorsSchedule.getFriday().equals(nurseSchedule.getFriday())) {
				return true;
			}
			if(!nurseSchedule.getSaturday().equals(Shift.NONE) && doctorsSchedule.getSaturday().equals(nurseSchedule.getSaturday())) {
				return true;
			}
			if(!nurseSchedule.getSunday().equals(Shift.NONE) && doctorsSchedule.getSunday().equals(nurseSchedule.getSunday())) {
				return true;
			}
		}
		
		return false;
	}
}
