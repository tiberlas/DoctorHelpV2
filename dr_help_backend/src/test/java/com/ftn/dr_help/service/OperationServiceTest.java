
package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.comon.Mail;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.OperationBlessingDTO;
import com.ftn.dr_help.dto.OperationBlessingInnerDTO;
import com.ftn.dr_help.model.enums.OperationBlessing;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.OperationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

	@InjectMocks
	@Autowired
	private OperationService operationSevice;
	
	@MockBean
	private DoctorRepository doctorRepository;
	
	@MockBean
	private LeaveRequestService leaveRequestService;
	
	@MockBean
	private RoomService roomService;
	
	@MockBean
	private OperationRepository operationRepository;
	
	@MockBean
	private Mail mailService;
	
	private OperationBlessingDTO operationRequestBlessing;
	private DoctorPOJO dr0;
	private DoctorPOJO dr1;
	private DoctorPOJO dr2;
	private List<Date> dr0Schedules = new ArrayList<>();
	private List<Date> dr1Schedules = new ArrayList<>();
	private List<Date> dr2Schedules = new ArrayList<>();
	private List<AbsenceInnerDTO> absence0 = new ArrayList<>();
	private List<AbsenceInnerDTO> absence1 = new ArrayList<>();
	private List<AbsenceInnerDTO> absence2 = new ArrayList<>();
	private ProceduresTypePOJO procedure;
	
	@Before
	public void setUp() {
		operationRequestBlessing = new OperationBlessingDTO(
				1l, 
				2l, 
				3l, 
				"2020-01-26 17:00", 
				1l, 
				1l);
		
		Calendar duration = Calendar.getInstance();
		duration.set(2020, 1, 20, 5, 0, 0);
		duration.set(Calendar.MILLISECOND, 0);
		procedure = new ProceduresTypePOJO();
		procedure.setDuration(duration.getTime());
		
		dr0 = new DoctorPOJO();
		dr0.setId(1l);
		dr0.setMonday(Shift.NONE);
		dr0.setTuesday(Shift.NONE);
		dr0.setWednesday(Shift.NONE);
		dr0.setThursday(Shift.NONE);
		dr0.setFriday(Shift.NONE);
		dr0.setSaturday(Shift.NONE);
		dr0.setSunday(Shift.SECOND);
		dr0.setProcedureType(procedure);
		
		dr1 = new DoctorPOJO();
		dr1.setId(2l);
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.NONE);
		dr1.setWednesday(Shift.NONE);
		dr1.setThursday(Shift.NONE);
		dr1.setFriday(Shift.NONE);
		dr1.setSaturday(Shift.NONE);
		dr1.setSunday(Shift.SECOND);
		dr1.setProcedureType(procedure);
		
		dr2 = new DoctorPOJO();
		dr2.setId(3l);
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.NONE);
		dr2.setWednesday(Shift.NONE);
		dr2.setThursday(Shift.NONE);
		dr2.setFriday(Shift.NONE);
		dr2.setSaturday(Shift.NONE);
		dr2.setSunday(Shift.SECOND);
		dr2.setProcedureType(procedure);
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		
		cal.set(2020, 0, 19, 17, 20, 0);
		dr0Schedules.add(cal.getTime());
		cal.set(2020, 0, 26, 8, 20, 0);
		dr0Schedules.add(cal.getTime());
		cal.set(2020, 0, 19, 18, 20, 0);
		dr1Schedules.add(cal.getTime());
		
		cal.set(2020, 0, 20, 17, 20, 0);
		cal1.set(2020, 0, 25, 17, 20, 0);
		absence0.add(new AbsenceInnerDTO(cal.getTime(), cal1.getTime()));
		
		cal.set(2020, 0, 20, 17, 20, 0);
		cal1.set(2020, 0, 24, 17, 20, 0);
		absence1.add(new AbsenceInnerDTO(cal.getTime(), cal1.getTime()));
		
		cal.set(2020, 0, 27, 17, 20, 0);
		cal1.set(2020, 0, 30, 17, 20, 0);
		absence2.add(new AbsenceInnerDTO(cal.getTime(), cal1.getTime()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testShouldPass() {
		
		Mockito.when(this.doctorRepository.findById(1l)).thenReturn(Optional.of(dr0));
		Mockito.when(this.doctorRepository.findById(2l)).thenReturn(Optional.of(dr1));
		Mockito.when(this.doctorRepository.findById(3l)).thenReturn(Optional.of(dr2));
		
		Mockito.when(this.doctorRepository.findAllReservedOperations(1l)).thenReturn(dr0Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(2l)).thenReturn(dr1Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(3l)).thenReturn(dr2Schedules);
		
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(1l)).thenReturn(absence0);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(2l)).thenReturn(absence1);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(3l)).thenReturn(absence2);
		
		Mockito.when(this.operationRepository.findOneById(1l)).thenReturn(new OperationPOJO());
		
		Calendar free = Calendar.getInstance();
		free.set(2020, 0, 26, 17, 0, 0);
		free.set(Calendar.MILLISECOND, 0);
		Mockito.when(this.roomService.findFirstFreeScheduleFromDate(1l, free)).thenReturn("01/26/2020 05:00 PM");
		
		//ignorisi metode za slanje mejla
		Mockito.doNothing().when(this.mailService).sendOperationApprovedToDoctorsEmail(Matchers.<DoctorPOJO>any(), Matchers.<OperationPOJO>any());
		Mockito.doNothing().when(this.mailService).sendOperationApprovedToPatientEmail(Matchers.<OperationPOJO>any());

		OperationBlessingInnerDTO actual = operationSevice.blessOperation(operationRequestBlessing);
		
		
		OperationBlessingInnerDTO expected = new OperationBlessingInnerDTO("BLESSED", OperationBlessing.BLESSED);
		
		assertEquals(expected.getBlessedLvl(), actual.getBlessedLvl());
		assertEquals(expected.getRecomendedDate(), actual.getRecomendedDate());
	}
	
	@Test
	public void testDoctorsRefusedShouldPass() {
		
		operationRequestBlessing.setDateAndTimeString("2020-01-19 16:00");
		
		Mockito.when(this.doctorRepository.findById(1l)).thenReturn(Optional.of(dr0));
		Mockito.when(this.doctorRepository.findById(2l)).thenReturn(Optional.of(dr1));
		Mockito.when(this.doctorRepository.findById(3l)).thenReturn(Optional.of(dr2));
		
		Mockito.when(this.doctorRepository.findAllReservedOperations(1l)).thenReturn(dr0Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(2l)).thenReturn(dr1Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(3l)).thenReturn(dr2Schedules);
		
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(1l)).thenReturn(absence0);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(2l)).thenReturn(absence1);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(3l)).thenReturn(absence2);
		
		Mockito.when(this.operationRepository.findOneById(1l)).thenReturn(new OperationPOJO());
		
		Calendar free = Calendar.getInstance();
		free.set(2020, 0, 26, 17, 0, 0);
		free.set(Calendar.MILLISECOND, 0);
		Mockito.when(this.roomService.findFirstFreeScheduleFromDate(1l, free)).thenReturn("01/26/2020 06:00 PM");
		
		OperationBlessingInnerDTO actual = operationSevice.blessOperation(operationRequestBlessing);
		
		//pocetak smene
		OperationBlessingInnerDTO expected = new OperationBlessingInnerDTO("01/26/2020 04:00 PM", OperationBlessing.DOCTORS_REFUSED);
		
		assertEquals(expected.getBlessedLvl(), actual.getBlessedLvl());
		assertEquals(expected.getRecomendedDate(), actual.getRecomendedDate());
	}
	
	@Test
	public void testRoomRefusedShouldPass() {
		
		Mockito.when(this.doctorRepository.findById(1l)).thenReturn(Optional.of(dr0));
		Mockito.when(this.doctorRepository.findById(2l)).thenReturn(Optional.of(dr1));
		Mockito.when(this.doctorRepository.findById(3l)).thenReturn(Optional.of(dr2));
		
		Mockito.when(this.doctorRepository.findAllReservedOperations(1l)).thenReturn(dr0Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(2l)).thenReturn(dr1Schedules);
		Mockito.when(this.doctorRepository.findAllReservedOperations(3l)).thenReturn(dr2Schedules);
		
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(1l)).thenReturn(absence0);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(2l)).thenReturn(absence1);
		Mockito.when(this.leaveRequestService.getAllDoctorAbsence(3l)).thenReturn(absence2);
		
		Mockito.when(this.operationRepository.findOneById(1l)).thenReturn(new OperationPOJO());
		
		Calendar free = Calendar.getInstance();
		free.set(2020, 0, 26, 17, 0, 0);
		free.set(Calendar.MILLISECOND, 0);
		Mockito.when(this.roomService.findFirstFreeScheduleFromDate(1l, free)).thenReturn("01/27/2020 04:00 PM");
		
		OperationBlessingInnerDTO actual = operationSevice.blessOperation(operationRequestBlessing);
		
		
		OperationBlessingInnerDTO expected = new OperationBlessingInnerDTO("01/27/2020 04:00 PM", OperationBlessing.ROOM_REFUSED);
		
		assertEquals(expected.getBlessedLvl(), actual.getBlessedLvl());
		assertEquals(expected.getRecomendedDate(), actual.getRecomendedDate());
	}

}
