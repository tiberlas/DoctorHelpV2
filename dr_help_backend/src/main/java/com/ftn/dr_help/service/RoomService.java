package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.dto.ProcedureIdAndDateDTO;
import com.ftn.dr_help.dto.RoomCalendarDTO;
import com.ftn.dr_help.dto.RoomDTO;
import com.ftn.dr_help.dto.RoomReservingInfoDTO;
import com.ftn.dr_help.dto.RoomSearchDTO;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.repository.OperationRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;
import com.ftn.dr_help.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository repository;
	
	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Autowired
	private ProcedureTypeRepository procedureTypeRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private DateConverter dateConvertor;
	
	@Autowired
	private CurrentUser currentUser;
	
	@Transactional(readOnly = true)
	public List<RoomDTO> findAll(String email) {
		
		try {
			List<RoomPOJO> finded = adminRepository.findOneByEmail(email).getClinic().getRoomList();
			
			if(finded == null)
				return null;

			List<RoomPOJO> reservedRooms = repository.getAllReservedRooms();
			
			List<RoomDTO> ret = new ArrayList<RoomDTO>();
			for(RoomPOJO room : finded) {
				if(!room.isDeleted()) {
					RoomDTO roomDTO;
					if(reservedRooms.contains(room)) {
						roomDTO = new RoomDTO(room);
						roomDTO.setReserved(true);
					} else {
						roomDTO = new RoomDTO(room);
					}
					roomDTO.setFirstFreeSchedule(findFirstFreeSchedule(room));
					
					ret.add(roomDTO);
				}
			}
		
			if(ret.isEmpty()) {
				return null;
			}
			
			return ret;
		} catch(Exception e) {
			System.out.println("EXCEPTION "+e.getMessage() +" CAUSE:" + e.getCause());
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public RoomDTO findOne(Long roomID, String email) {
		
		Long clinicID = adminRepository.findOneByEmail(email).getClinic().getId();
		List<RoomPOJO> reservedRooms = repository.getAllReservedRooms();
		
		if(roomID == null) {
			return null;
		}
		
		RoomPOJO finded = repository.findByIdAndClinic_id(roomID, clinicID).orElse(null);
		
		if(finded == null || finded.isDeleted())
			return null;
		
		RoomDTO room = new RoomDTO(finded);
		if(reservedRooms.contains(finded)) {
			room.setReserved(true);
		}
				
		return room;
	}
	
	public RoomPOJO findOnePOJO(Long roomID, String email) {
		
		Long clinicID = adminRepository.findOneByEmail(email).getClinic().getId();
		
		if(roomID == null) {
			return null;
		}
		
		RoomPOJO finded = repository.findByIdAndClinic_id(roomID, clinicID).orElse(null);
		
		if(finded == null || finded.isDeleted())
			return null;
				
		return finded;
	}
	
	public RoomDTO save(RoomDTO newRoom, String email) {

		try {
			
			ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
			
			List<RoomPOJO> list = repository.findAllByClinic_id(admin.getClinic().getId());
			for (RoomPOJO r : list) {
				if (r.getNumber() == newRoom.getNumber()) {
					return null;
				}
			}
			
			ClinicPOJO clinic = admin.getClinic();
			RoomPOJO room = new RoomPOJO();
			room.setName(newRoom.getName());
			room.setNumber(newRoom.getNumber());
			room.setClinic(clinic);
			
			ProceduresTypePOJO procedure = procedureTypeRepository.findById(newRoom.getProcedureTypeId()).orElse(null);
			if(procedure == null) {
				return null;
			}
			room.setProcedurasTypes(procedure);
			
			repository.save(room);
			
			clinic.addRoom(room);
			clinicRepository.save(clinic);
			
			return new RoomDTO(room);
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean delete(Long id, String email) {
		try {
			if(email == null) {
				return false;
			}
			
			if(id == null) {
				return false;
			}
			
			ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
			if(admin == null) {
				return false;
			}
			
			ClinicPOJO clinic = admin.getClinic();
			RoomPOJO finded = repository.findByIdAndClinic_id(id, clinic.getId()).orElse(null);
			if(finded == null)
				return false;
			
			List<RoomPOJO> reservedRooms = repository.getAllReservedRooms();
			if(reservedRooms.contains(finded)) {
				System.out.println("reserved");
				return false;
			}
			
			clinic.deleteRoom(finded);
			clinicRepository.save(clinic);
			finded.setDeleted(true);
			repository.save(finded);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public RoomDTO change(RoomDTO room, String email) {
		
		try {
			ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
			
			ClinicPOJO clinic = admin.getClinic();
			RoomPOJO finded = repository.findByIdAndClinic_id(room.getId(), clinic.getId()).orElse(null);
			if(finded == null) {
				return null;			
			}
			
			List<RoomPOJO> reservedRooms = repository.getAllReservedRooms();
			if(reservedRooms.contains(finded)) {
				return null;
			}
			
			RoomPOJO exist = repository.findOneByNumber(room.getNumber()).orElse(null);
			if(exist!= null && exist.getNumber() != finded.getNumber()) {
				return null;
			}
			
			ProceduresTypePOJO procedurasTypes = procedureTypeRepository.findById(room.getProcedureTypeId()).orElse(null);
			if(procedurasTypes == null) {
				return null;
			}
			
			finded.setName(room.getName());
			finded.setNumber(room.getNumber());
			finded.setProcedurasTypes(procedurasTypes);
			repository.save(finded);
			
			return new RoomDTO(finded);
		} catch(Exception e) {
			return null;
		}
	}
	
	public List<RoomCalendarDTO> getSchedule(Long roomId) throws NullPointerException{
		
			try {
				String begining;
				List<RoomCalendarDTO> ret = new ArrayList<>();
				
				Calendar durationDate = Calendar.getInstance(); 
				
				List<AppointmentPOJO> reservedAppointmentsInRoom = appointmentRepository.findAllScheduledAppointmentsInRoom(roomId);
				if(reservedAppointmentsInRoom != null) {
					begining = " Appointment with dr ";
					for(AppointmentPOJO appointment : reservedAppointmentsInRoom) {
						RoomCalendarDTO scheduledAppointment = new RoomCalendarDTO();
						scheduledAppointment.setAppointmentId(appointment.getId());
						scheduledAppointment.setOperationId(null);
						scheduledAppointment.setTitle(begining+appointment.getDoctor().getFirstName()+" "+appointment.getDoctor().getLastName());
						scheduledAppointment.setDate(dateConvertor.americanDateToString(appointment.getDate()));
						scheduledAppointment.setStartTime(dateConvertor.timeToString(appointment.getDate()));
						
						durationDate.setTime(appointment.getProcedureType().getDuration());
						Calendar endTimeDate = appointment.getDate();
						endTimeDate.add(Calendar.HOUR, durationDate.get(Calendar.HOUR));
						endTimeDate.add(Calendar.MINUTE, durationDate.get(Calendar.MINUTE));
						String endTime = dateConvertor.timeToString(endTimeDate);
						scheduledAppointment.setEndTime(endTime);
						
						ret.add(scheduledAppointment);
					}
				}
				
				List<OperationPOJO> reservedOperationsInRoom = operationRepository.findAllScheduledOperationsInRoom(roomId);
				if(reservedOperationsInRoom != null) {
					begining = " Operation with dr ";
					for(OperationPOJO operation : reservedOperationsInRoom) {
						RoomCalendarDTO scheduledAppointment = new RoomCalendarDTO();
						scheduledAppointment.setAppointmentId(null);
						scheduledAppointment.setOperationId(operation.getId());
						
						scheduledAppointment.setTitle(begining+
													operation.getFirstDoctor().getFirstName()+" "+operation.getFirstDoctor().getLastName()+
													", dr "+operation.getSecondDoctor().getFirstName()+" "+operation.getSecondDoctor().getLastName()+
													" and with dr "+operation.getThirdDoctor().getFirstName()+" "+operation.getThirdDoctor().getLastName()); 
						
						scheduledAppointment.setDate(dateConvertor.americanDateToString(operation.getDate()));
						scheduledAppointment.setStartTime(dateConvertor.timeToString(operation.getDate()));
						durationDate.setTime(operation.getOperationType().getDuration());
						Calendar endTimeDate = operation.getDate();
						endTimeDate.add(Calendar.HOUR, durationDate.get(Calendar.HOUR));
						endTimeDate.add(Calendar.MINUTE, durationDate.get(Calendar.MINUTE));
						String endTime = dateConvertor.timeToString(endTimeDate);
						scheduledAppointment.setEndTime(endTime);
						
						ret.add(scheduledAppointment);
					}
				}
				
				return ret;
			} catch(Exception e) {
				return null;
			}
	}
	
	public List<RoomDTO> search(RoomSearchDTO searchParameters, String email) {
		/*
		 * searchParameter if a field is not in use for search the it must be null;
		 * searchParameter.getDate must be in format yyyy-MM-dd HH:mm; 
		 * Calendar is singleton!!! 
		 * */
		try {
			List<RoomPOJO> allRooms = adminRepository.findOneByEmail(email).getClinic().getRoomList();
			List<RoomPOJO> reservedRooms = repository.getAllReservedRooms();
			
			List<RoomDTO> retVal = new ArrayList<RoomDTO>();
			for(RoomPOJO room : allRooms) {
				RoomDTO newRoom = new RoomDTO(room);
				newRoom.setFirstFreeSchedule(findFirstFreeSchedule(room));
				if(reservedRooms.contains(room)) {
					newRoom.setReserved(true);
				}
				retVal.add(newRoom);
			}
			
			if(searchParameters.getName() == null && 
					searchParameters.getNumber() == null &&
					searchParameters.getTypeId() == null &&
					searchParameters.getDate() == null) {
				
				return retVal;
			}
			
			List<RoomDTO> roomsFilteredDate = new ArrayList<>();
			if(searchParameters.getDate() == null) {
				roomsFilteredDate = retVal;
			} else {
				Calendar searchedDate = dateConvertor.stringToDate(searchParameters.getDate());
				
				for(RoomPOJO room : allRooms) {
					Date duration = room.getProcedurasTypes().getDuration();
					boolean flagReserved = false;
				
					List<AppointmentPOJO> appointments = room.getAppointments();
					for(AppointmentPOJO appointment : appointments) {
						if(appointment.isDeleted()) {
							continue;
						}
						
						Calendar endDate = Calendar.getInstance();
						endDate.setTime(appointment.getDate().getTime());
						@SuppressWarnings("deprecation")
						int hours = duration.getHours();
						@SuppressWarnings("deprecation")
						int minutes = duration.getMinutes();
						endDate.add(Calendar.HOUR, hours);
						endDate.add(Calendar.MINUTE, minutes);
						
						//System.out.println(appointment.getId());
						//System.out.println(dateConvertor.dateAndTimeToString(searchedDate));
						//System.out.println(dateConvertor.dateAndTimeToString(appointment.getDate()));
						//System.out.println(dateConvertor.dateAndTimeToString(endDate));
						
						//System.out.println(searchedDate.after(appointment.getDate()));
						//System.out.println(searchedDate.equals(appointment.getDate()));
						//System.out.println(searchedDate.before(endDate));
						//System.out.println(searchedDate.equals(endDate));
						
						if(searchedDate.compareTo(appointment.getDate()) >= 0 && searchedDate.compareTo(endDate) <= 0) {
							flagReserved = true;
							break;
						}
					}

					if(!flagReserved) {
						RoomDTO newRoom = new RoomDTO(room);
						newRoom.setFirstFreeSchedule(findFirstFreeSchedule(room));
						if(reservedRooms.contains(room)) {
							newRoom.setReserved(true);
						}
						roomsFilteredDate.add(newRoom);						
					}
				}
			}
			
			if(roomsFilteredDate.isEmpty()) {
				return roomsFilteredDate;
			}
			
			List<RoomDTO> roomsFilteredName;
			if(searchParameters.getName() == null) {
				roomsFilteredName = roomsFilteredDate;
			} else {
				roomsFilteredName = new ArrayList<>();
				for(RoomDTO room : roomsFilteredDate) {
					if(room.getName().toLowerCase().contains(searchParameters.getName().toLowerCase())) {
						roomsFilteredName.add(room);
					}
				}
			}
			
			if(roomsFilteredName.isEmpty()) {
				return roomsFilteredName;
			}
			
			List<RoomDTO> roomsFilteredNumber;
			if(searchParameters.getNumber() == null) {
				roomsFilteredNumber = roomsFilteredName;
			} else {
				roomsFilteredNumber = new ArrayList<>();
				for(RoomDTO room : roomsFilteredName) {
					if(room.getNumber() == searchParameters.getNumber()) {
						roomsFilteredNumber.add(room);
					}
				}
			}
			
			if(roomsFilteredNumber.isEmpty()) {
				return roomsFilteredNumber;
			}
			
			List<RoomDTO> roomsFilteredType;
			if(searchParameters.getTypeId() == null) {
				roomsFilteredType = roomsFilteredNumber;
			} else {
				roomsFilteredType = new ArrayList<>();
				for(RoomDTO room : roomsFilteredNumber) {
					if(room.getProcedureTypeId() == searchParameters.getTypeId()) {
						roomsFilteredType.add(room);
					}
				}
			}
			
			return roomsFilteredType;
		} catch(Exception e) {
			return null;
		}
	}
	
	private String findFirstFreeSchedule(RoomPOJO room) {
		/*
		 * Nadje prvi slobodni termin za sobu od trenutnog vremena(vremena poziva ove funkcije)
		 * */
		
		Calendar begin = Calendar.getInstance(); //sadrzi pocetak prvog slobodnog termina; prvi je sutra u 8 
		begin.add(Calendar.DAY_OF_MONTH, 1);
		begin.set(Calendar.AM_PM, Calendar.AM);
		begin.set(Calendar.HOUR, 8);
		begin.set(Calendar.MINUTE, 0);
		begin.clear(Calendar.SECOND);
		begin.clear(Calendar.MILLISECOND);
		
		return findFirstFreeScheduleFromDate(room, begin);
	}
	
	@Transactional(readOnly = true,  propagation = Propagation.SUPPORTS)
	public String findFirstFreeScheduleFromDate(Long roomId, Calendar begin) {
		try {
			RoomPOJO room = repository.getOne(roomId);
			
			return findFirstFreeScheduleFromDate(room, begin);
		} catch(Exception e) {
			return null;
		}
		
	}
	
	@Transactional(readOnly = true,  propagation = Propagation.SUPPORTS)
	public Calendar findFirstFreeScheduleFromDateInRawformat(RoomPOJO room, Calendar begin) {
		/**
		 * nadje priv slobodan termin za sobu od trenutka (begin)
		 * ako je bas trazeni termin (begin) slobodan on se vrati u string obliku
		 * */
		List<Date> dates = appointmentRepository.findScheduledDatesOfRoom(room.getId());
		
		Calendar duration = Calendar.getInstance();
		duration.setTime(room.getProcedurasTypes().getDuration());
		int hours = duration.get(Calendar.HOUR);
		int minutes = duration.get(Calendar.MINUTE);
	
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		Calendar end = Calendar.getInstance(); //sadrzi kraj termina u odnosu na begin
		end.setTime(begin.getTime());
		end.add(Calendar.HOUR, hours);
		end.add(Calendar.MINUTE, minutes);
		
		Calendar currentBegin = Calendar.getInstance();
		Calendar currentEnd = Calendar.getInstance();
		for(Date date : dates) {
		
			//iteriramo kroz zakazane termine; termini su sortirani 
			currentBegin.setTime(date);
			currentEnd.setTime(date);
			currentEnd.add(Calendar.HOUR, hours);
			currentEnd.add(Calendar.MINUTE, minutes);
			currentBegin.clear(Calendar.SECOND);
			currentBegin.clear(Calendar.MILLISECOND);
			currentEnd.clear(Calendar.SECOND);
			currentEnd.clear(Calendar.MILLISECOND);
			
			System.out.println("----------------------------------------------------");
			System.out.println("BEGIN: " + dateConvertor.dateForFrontEndString(begin));
			System.out.println("END: " + dateConvertor.dateForFrontEndString(end));
			System.out.println("CURRENT BEGIN: " + dateConvertor.dateForFrontEndString(currentBegin));
			System.out.println("CURRENT END: " + dateConvertor.dateForFrontEndString(currentEnd));
			
			if(begin.compareTo(currentEnd) >= 0) {
				continue;
			}
			
			if(end.compareTo(currentBegin) <= 0) {
				//termin je dobar i vrati ga
				return (Calendar) begin.clone();
			} else {
				//uzmi termin posle trenutnog
				begin.setTime(currentEnd.getTime());
				end.setTime(begin.getTime());
				end.add(Calendar.HOUR, hours);
				end.add(Calendar.MINUTE, minutes);
			}
		}
		
		return (Calendar) begin.clone();
	
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String findFirstFreeScheduleFromDate(RoomPOJO room, Calendar begin) {
			
		Calendar finded = findFirstFreeScheduleFromDateInRawformat(room, begin);
		return dateConvertor.dateForFrontEndString(finded);
	}
	
	public List<RoomReservingInfoDTO> getAllWithType(String adminEmail, ProcedureIdAndDateDTO request) {
		try {
			
			List<RoomReservingInfoDTO> rooms = new ArrayList<>();
			List<RoomPOJO> finded = repository.findAllWithType(adminEmail, request.getTypeId());
			
			Calendar begin = dateConvertor.americanStringToDate(request.getDate());
			
			for(RoomPOJO room : finded) {
				rooms.add(new RoomReservingInfoDTO(
						room.getId(), 
						findFirstFreeScheduleFromDate(room, begin), 
						room.getName(), 
						room.getNumber()));
			}
			
			return rooms;
		} catch(Exception e) {
			return null;
		}
	}
	
	public List<RoomPOJO> getAllRoomFromClinicWithProcedure(Long clinicId, Long procedureId) {
		return repository.getAllRoomFromClinicWithProcedure(clinicId, procedureId);
	}
	
}
