package com.ftn.dr_help.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.HealthRecordPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppointmentRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	

	private DoctorPOJO doctor;
	private NursePOJO nurse;
	private RoomPOJO room;
	private ProceduresTypePOJO procedure;
	private PatientPOJO patient;
	
	@Before
	public void setUp() {
		doctor = new DoctorPOJO();
	
			doctor.setFirstName("PERA");
			doctor.setLastName("PERIC");
			doctor.setBirthday(Calendar.getInstance());
			doctor.setAddress("Valtera 2");
			doctor.setCity("Novi Sad");
			doctor.setDeleted(false);
			doctor.setEmail("per1a@gmail");
			doctor.setFriday(Shift.NONE);
			doctor.setSaturday(Shift.NONE);
			doctor.setSunday(Shift.NONE);
			doctor.setMonday(Shift.NONE);
			doctor.setTuesday(Shift.NONE);
			doctor.setWednesday(Shift.NONE);
			doctor.setThursday(Shift.NONE);
			doctor.setPassword("doca");
			doctor.setMustChangePassword(false);
			doctor.setPhoneNumber("0230320");
			doctor.setRole(RoleEnum.DOCTOR);
			doctor.setState("Serbia");
		
		
		nurse = new NursePOJO();
		
			nurse.setFirstName("Ana");
			nurse.setLastName("Gyd");
			nurse.setBirthday(Calendar.getInstance());
			nurse.setAddress("Valtera 2");
			nurse.setCity("Novi Sad");
			nurse.setDeleted(false);
			nurse.setEmail("gytana@gmail");
			nurse.setFriday(Shift.FIRST);
			nurse.setSaturday(Shift.SECOND);
			nurse.setSunday(Shift.NONE);
			nurse.setMonday(Shift.NONE);
			nurse.setTuesday(Shift.NONE);
			nurse.setWednesday(Shift.NONE);
			nurse.setThursday(Shift.NONE);
			nurse.setPassword("4321");
			nurse.setMustChangePassword(false);
			nurse.setPhoneNumber("0230320");
			nurse.setRole(RoleEnum.NURSE);
			nurse.setState("Serbia");
			
		
		procedure = new ProceduresTypePOJO();
			procedure.setPrice(20);
			procedure.setDuration(Calendar.getInstance().getTime());
			procedure.setOperation(false);
			procedure.setName("Procedure");
		
		patient = new PatientPOJO();
			patient.setActivated(true);
			patient.setAddress("Some address 1");
			patient.setBirthday(Calendar.getInstance());
			patient.setCity("City");
			patient.setEmail("patient@gmail");
			patient.setFirstName("P#1");
			patient.setLastName("L#1");
			patient.setInsuranceNumber(12312331L);
			patient.setPassword("3123");
			patient.setPhoneNumber("321321312");
			patient.setRole(RoleEnum.PATIENT);
			patient.setHealthRecord(new HealthRecordPOJO());
			patient.setState("Serbia");
		
		this.entityManager.persist(doctor);
		this.entityManager.persist(patient);
		this.entityManager.persist(nurse);
		this.entityManager.persist(procedure);
		
	}
	
	@Test
	public void testAddingNewAppointment() {
		
		List<AppointmentPOJO> listBeforeAdd = new ArrayList<>();
        List<AppointmentPOJO> listAfterAdd = new ArrayList<>();
        
      
		Iterable<AppointmentPOJO> appointments = appointmentRepository.findAll();
		appointments.forEach(listBeforeAdd::add);
		
		AppointmentPOJO appointment = new AppointmentPOJO();
			appointment.setDate(Calendar.getInstance());
			appointment.setDeleted(false);
			appointment.setDiscount(2);
			appointment.setDoctor(doctor);
			appointment.setNurse(nurse);
			appointment.setStatus(AppointmentStateEnum.AVAILABLE);
			appointment.setProcedureType(procedure);
		
		this.entityManager.persist(appointment);
		
		appointments = appointmentRepository.findAll();
		appointments.forEach(listAfterAdd::add);
	    assertThat(listAfterAdd).hasSize(listBeforeAdd.size() + 1);
	}
	
	@Test
	public void testReservingAPredefined() {
		
		List<AppointmentPOJO> beforeReservePredefinedList = new ArrayList<>();
		List<AppointmentPOJO> afterReservePredefinedList = new ArrayList<>();
		
		Iterable<AppointmentPOJO> predefined = appointmentRepository.findAllPredefined();
		predefined.forEach(beforeReservePredefinedList::add);
		assertThat(beforeReservePredefinedList.size() > 1); //mora biti barem 1 predefined, jer je malo pre dodat 
		
		appointmentRepository.reserveAppointment(beforeReservePredefinedList.get(0).getId(), patient.getId());
		
		predefined = appointmentRepository.findAllPredefined();
		predefined.forEach(afterReservePredefinedList::add);
		
		assertThat(afterReservePredefinedList).hasSize(beforeReservePredefinedList.size() - 1);
	}
	
	@Test
	public void testDeletingAPredefinedAppointment() { //brise predefined i proverava da li je obrisan
		List<AppointmentPOJO> beforeDeleting = new ArrayList<>();
		List<AppointmentPOJO> afterDeleting = new ArrayList<>();
		
		
		Iterable<AppointmentPOJO> appointments = appointmentRepository.findAllPredefined();
		
		appointments.forEach(beforeDeleting::add);
		
		Long appId = null;
		for (AppointmentPOJO appointment : beforeDeleting) {
				appId = appointment.getId();
		}
		
		if(appId != null) {
			System.out.println("app id is" + appId);
			AppointmentPOJO appointment = appointmentRepository.findOneById(appId);
			appointment.setDeleted(true);
			this.entityManager.merge(appointment);
		} 
		
		appointments = appointmentRepository.findAllPredefined();
		appointments.forEach(afterDeleting::add);
		
		assertThat(afterDeleting).hasSize(beforeDeleting.size() - 1);
	}
	
	@Test
	public void testGetDoctorsAppointments () {
		// 20.4.2020
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		
		startTime.set(Calendar.YEAR, 2020);
		startTime.set(Calendar.MONTH, 3);
		startTime.set(Calendar.DAY_OF_MONTH, 3);
		startTime.set(Calendar.HOUR_OF_DAY, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		
		endTime.set(Calendar.YEAR, 2020);
		endTime.set(Calendar.MONTH, 3);
		endTime.set(Calendar.DAY_OF_MONTH, 3);
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
		
		List<AppointmentPOJO> actualList1 = appointmentRepository.getDoctorsAppointments(2L, startTime, endTime);
		
		startTime.add(Calendar.DAY_OF_MONTH, 4);
		endTime.add(Calendar.DAY_OF_MONTH, 4);
		
		List<AppointmentPOJO> actualList2 = appointmentRepository.getDoctorsAppointments(2L, startTime, endTime);
		List<AppointmentPOJO> actualList3 = appointmentRepository.getDoctorsAppointments(420L, startTime, endTime);
		
		assertEquals (1, actualList1.size());
		assertEquals ("Fri Apr 03 10:00:00 CEST 2020", actualList1.get(0).getDate().getTime().toString());
		assertEquals ("General exam", actualList1.get(0).getProcedureType().getName());
		
		assertEquals (0, actualList2.size());
		
		assertEquals (0, actualList3.size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testInsertOne() {
		List<AppointmentPOJO> before;
		List<AppointmentPOJO> after;
		
		before = appointmentRepository.getAllRequests();
		
		List<DoctorPOJO> doctors = doctorRepository.findAll();

		AppointmentPOJO appointment = new AppointmentPOJO();
		appointment.setDate(Calendar.getInstance());
		appointment.setDeleted(false);
		appointment.setDoctor(doctors.get(0));
		appointment.setProcedureType(doctors.get(0).getProcedureType());
		appointment.setNurse(null);
		appointment.setStatus(AppointmentStateEnum.DOCTOR_REQUESTED_APPOINTMENT);
		appointment.setPatient(null);
		appointment.setDiscount(0);
		appointment.setRoom(null);
		appointment.setVersion(1l);
		
		entityManager.persist(appointment);
		
		after = appointmentRepository.getAllRequests();
		
		assertEquals(before.size()+1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testApproveOne() {
		List<AppointmentPOJO> before;
		List<AppointmentPOJO> after;
		
		before = appointmentRepository.getAllRequests();
		
		before.get(0).setStatus(AppointmentStateEnum.APPROVED);
		entityManager.merge(before.get(0));
		
		after = appointmentRepository.getAllRequests();
		
		assertEquals(before.size()-1, after.size());
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testOnlyOne() {
		List<AppointmentPOJO> before;
		List<AppointmentPOJO> after;
		
		before = appointmentRepository.getAllRequests();
		for(AppointmentPOJO appointment : before) {
			appointment.setStatus(AppointmentStateEnum.APPROVED);
			entityManager.merge(appointment);
		}
		
		List<DoctorPOJO> doctors = doctorRepository.findAll();

		AppointmentPOJO appointment = new AppointmentPOJO();
		appointment.setDate(Calendar.getInstance());
		appointment.setDeleted(false);
		appointment.setDoctor(doctors.get(0));
		appointment.setProcedureType(doctors.get(0).getProcedureType());
		appointment.setNurse(null);
		appointment.setStatus(AppointmentStateEnum.DOCTOR_REQUESTED_APPOINTMENT);
		appointment.setPatient(null);
		appointment.setDiscount(0);
		appointment.setRoom(null);
		appointment.setVersion(1l);
		
		entityManager.persist(appointment);
		
		after = appointmentRepository.getAllRequests();
		
		assertEquals(1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEmptyList() {
		List<AppointmentPOJO> before;
		List<AppointmentPOJO> after;
		
		before = appointmentRepository.getAllRequests();
		for(AppointmentPOJO appointment : before) {
			appointment.setStatus(AppointmentStateEnum.APPROVED);
			entityManager.merge(appointment);
		}
		
		after = appointmentRepository.getAllRequests();
		
		assertTrue(after.isEmpty());
	}
}
