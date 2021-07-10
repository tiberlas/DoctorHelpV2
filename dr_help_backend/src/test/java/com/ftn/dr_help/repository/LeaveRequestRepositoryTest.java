package com.ftn.dr_help.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.enums.LeaveStatusEnum;
import com.ftn.dr_help.model.enums.LeaveTypeEnum;
import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.LeaveRequestPOJO;



@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class LeaveRequestRepositoryTest {

	@Autowired
	private TestEntityManager em;
	
	
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	
	
	private LeaveRequestPOJO leaveRequest;
	private LeaveRequestPOJO leaveRequest1;
	private LeaveRequestPOJO leaveRequest2;
	
	private DoctorPOJO doctor;
	
	private List<LeaveRequestPOJO> expectedList;
	
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
		
		
		this.em.persist(doctor);

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.set(2020, 1, 20, 0, 0, 0);
		end.set(2020, 1, 24, 0, 0, 0);
		
		leaveRequest = new LeaveRequestPOJO();
		leaveRequest.setDoctor(doctor);
		leaveRequest.setNurse(null);
		leaveRequest.setFirstDay(start);
		leaveRequest.setLastDay(end);
		leaveRequest.setLeaveStatus(LeaveStatusEnum.APPROVED);
		leaveRequest.setLeaveType(LeaveTypeEnum.PERSONAL);
		leaveRequest.setStaffRole(RoleEnum.DOCTOR);
		
		start.set(2020, 1, 24, 0, 0, 0);
		end.set(2020, 1, 27, 0, 0, 0);
		
		leaveRequest1 = new LeaveRequestPOJO();
		leaveRequest1.setDoctor(doctor);
		leaveRequest1.setNurse(null);
		leaveRequest1.setFirstDay(start);
		leaveRequest1.setLastDay(end);
		leaveRequest1.setLeaveStatus(LeaveStatusEnum.APPROVED);
		leaveRequest1.setLeaveType(LeaveTypeEnum.PERSONAL);
		leaveRequest1.setStaffRole(RoleEnum.DOCTOR);
		
		start.set(2020, 2, 10, 0, 0, 0);
		end.set(2020, 2, 15, 0, 0, 0);
		
		leaveRequest2 = new LeaveRequestPOJO();
		leaveRequest2.setDoctor(doctor);
		leaveRequest2.setNurse(null);
		leaveRequest2.setFirstDay(start);
		leaveRequest2.setLastDay(end);
		leaveRequest2.setLeaveStatus(LeaveStatusEnum.APPROVED);
		leaveRequest2.setLeaveType(LeaveTypeEnum.PERSONAL);
		leaveRequest2.setRequestNote("");
		leaveRequest2.setStaffRole(RoleEnum.DOCTOR);
		
		expectedList = new ArrayList<>();
		expectedList.add(leaveRequest);
		expectedList.add(leaveRequest1);
		expectedList.add(leaveRequest2);
		
	}
	
	@Test
	public void testLeaveRequestsForDoctorsShouldPass() throws Exception{
		
		this.em.persist(leaveRequest);
		this.em.persist(leaveRequest1);
		this.em.persist(leaveRequest2);
		
		DoctorPOJO foundDoctor = doctorRepository.findOneByEmail("per1a@gmail");
		System.out.println("DOCTOR IS " + foundDoctor.getId());
		Calendar date = Calendar.getInstance();
		date.set(2020, 1, 18, 0, 0, 0);
		
		List<LeaveRequestPOJO> finded = leaveRequestRepository.findAllForDoctor(foundDoctor.getId(), date.getTime());
		
		assertEquals(leaveRequest, finded.get(0));
		assertEquals(leaveRequest1, finded.get(1));
		assertEquals(leaveRequest2, finded.get(2));
	}

}
