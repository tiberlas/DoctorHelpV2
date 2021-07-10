package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.controller.AppointmentController;
import com.ftn.dr_help.dto.AppointmentListDTO;
import com.ftn.dr_help.dto.PatientHistoryDTO;
import com.ftn.dr_help.model.enums.AppointmentStateEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.NursePOJO;
import com.ftn.dr_help.model.pojo.PatientPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.DoctorRepository;

@RunWith (SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {

	@InjectMocks
	@Autowired
	private AppointmentService appointmentService;
	
	@MockBean
	private AppointmentController appointmentController;
	
	@MockBean
	private AppointmentRepository appointmentRepository;
	
	@MockBean
	private DoctorRepository doctorRepository;
	
	private List<AppointmentPOJO> appointmentList;
	private AppointmentPOJO app1;
	private AppointmentPOJO app2;
	private AppointmentPOJO app3;
	
	private DoctorPOJO d1;
	
	@Before
	public void setUp () {
		app1 = new AppointmentPOJO();
		app1.setId(15L);
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, 2020);
		c1.set(Calendar.MONTH, 3);
		c1.set(Calendar.DAY_OF_MONTH, 3);
		c1.set(Calendar.HOUR_OF_DAY, 4);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		app1.setDate(c1);
		ProceduresTypePOJO pt1 = new ProceduresTypePOJO();
		pt1.setName("psihoanaliza");
		pt1.setPrice(250);
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(0L);
		temp.set(Calendar.HOUR_OF_DAY, 1);
		pt1.setDuration(temp.getTime());
		app1.setProcedureType(pt1);
		d1 = new DoctorPOJO();
		d1.setFirstName("Pera");
		d1.setLastName("Peric");
		d1.setId(1L);
		d1.setProcedureType(pt1);
		ClinicPOJO cl1 = new ClinicPOJO();
		cl1.setName("Klinika zdravog uma");
		cl1.setId(1L);
		d1.setClinic(cl1);
		app1.setDoctor(d1);
		NursePOJO n1 = new NursePOJO();
		n1.setFirstName("Ana");
		n1.setLastName("Anica");
		n1.setId(1L);
		app1.setNurse(n1);
		app1.setStatus(AppointmentStateEnum.AVAILABLE);
		RoomPOJO r1 = new RoomPOJO();
		r1.setName("Terapija");
		r1.setNumber(5);
		app1.setRoom(r1);
		app1.setDiscount(5);
		PatientPOJO pat1 = new PatientPOJO ();
		pat1.setId(1L);
		app1.setPatient(pat1);
		
		app2 = new AppointmentPOJO();
		app2.setId(19L);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, 2020);
		c2.set(Calendar.MONTH, 3);
		c2.set(Calendar.DAY_OF_MONTH, 3);
		c2.set(Calendar.HOUR_OF_DAY, 6);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		app2.setDate(c2);
		ProceduresTypePOJO pt2 = new ProceduresTypePOJO();
		pt2.setName("psihoanaliza");
		pt2.setPrice(250);
		app2.setProcedureType(pt2);
		DoctorPOJO d2 = new DoctorPOJO();
		d2.setFirstName("Đorđe");
		d2.setLastName("Bogdanovic");
		d2.setId(4L);
		ClinicPOJO cl2 = new ClinicPOJO();
		cl2.setName("Klinika zdravog uma");
		cl2.setId(1L);
		d2.setClinic(cl2);
		app2.setDoctor(d2);
		NursePOJO n2 = new NursePOJO();
		n2.setFirstName("Stojanka");
		n2.setLastName("Jovanović");
		n2.setId(2L);
		app2.setNurse(n2);
		app2.setStatus(AppointmentStateEnum.AVAILABLE);
		RoomPOJO r2 = new RoomPOJO();
		r2.setName("Terapija");
		r2.setNumber(2);
		app2.setRoom(r2);
		app2.setDiscount(10);
		
		app3 = new AppointmentPOJO();
		app3.setId(34L);
		Calendar c3 = Calendar.getInstance();
		c3.set(Calendar.YEAR, 2020);
		c3.set(Calendar.MONTH, 3);
		c3.set(Calendar.DAY_OF_MONTH, 3);
		c3.set(Calendar.HOUR_OF_DAY, 8);
		c3.set(Calendar.MINUTE, 0);
		c3.set(Calendar.SECOND, 0);
		app3.setDate(c3);
		ProceduresTypePOJO pt3 = new ProceduresTypePOJO();
		pt3.setName("psihoanaliza");
		pt3.setPrice(250);
		app3.setProcedureType(pt3);
		DoctorPOJO d3 = new DoctorPOJO();
		d3.setFirstName("Pera");
		d3.setLastName("Peric");
		d3.setId(1L);
		ClinicPOJO cl3 = new ClinicPOJO();
		cl3.setName("Klinika zdravog uma");
		cl3.setId(1L);
		d3.setClinic(cl3);
		app3.setDoctor(d3);
		NursePOJO n3 = new NursePOJO();
		n3.setFirstName("Ana");
		n3.setLastName("Anica");
		n3.setId(1L);
		app3.setNurse(n3);
		app3.setStatus(AppointmentStateEnum.AVAILABLE);
		RoomPOJO r3 = new RoomPOJO();
		r3.setName("Terapija");
		r3.setNumber(5);
		app3.setRoom(r1);
		app3.setDiscount(15);

		appointmentList = new ArrayList<AppointmentPOJO> ();
		appointmentList.add(app1);
		appointmentList.add(app2);
		appointmentList.add(app3);
		
	}
	
//	public AppointmentListDTO getPredefinedAppointments(String doctorId, String procedureTypeId, String clinicId,
//			String date) {
	
//	@Test
//	public void getAllPredefinedAppointmentsShouldPass () {
//		
//		Mockito.when(this.appointmentRepository.getAllPredefinedAppointments()).thenReturn(appointmentList);
//		
//		List<String> expectedDates = new ArrayList<String> ();
//		expectedDates.add("unfiltered");
//		expectedDates.add("03.04.2020.");
//		List<String> expectedDoctors = new ArrayList<String> ();
//		expectedDoctors.add("unfiltered");
//		expectedDoctors.add("Pera Peric");
//		expectedDoctors.add("Đorđe Bogdanovic");
//		List<String> expectedClinics = new ArrayList<String> ();
//		expectedClinics.add("unfiltered");
//		expectedClinics.add("Klinika zdravog uma"); 
//		List<String> expectedTypes = new ArrayList<String> ();
//		expectedTypes.add("unfiltered");
//		expectedTypes.add("psihoanaliza");
//		List<PatientHistoryDTO> expectedAppointments = new ArrayList<PatientHistoryDTO> ();
//		expectedAppointments.add(new PatientHistoryDTO(app1));
//		expectedAppointments.add(new PatientHistoryDTO(app2));
//		expectedAppointments.add(new PatientHistoryDTO(app3));
//		AppointmentListDTO expected = new AppointmentListDTO ();
//		expected.setPossibleDates(expectedDates);
//		expected.setPossibleDoctors(expectedDoctors);
//		expected.setPossibleClinics(expectedClinics);
//		expected.setPossibleTypes(expectedTypes);
//		expected.setAppointmentList(expectedAppointments);
//		
//		AppointmentListDTO actual = appointmentService.getPredefinedAppointments("unfiltered", "unfiltered", "unfiltered", "unfiltered");
//		AppointmentListDTO actual1 = appointmentService.getPredefinedAppointments("Pera Peric", "unfiltered", "unfiltered", "unfiltered");
//		
//		assertEquals(3, actual.getAppointmentList().size());
//		
//		assertEquals(expected.getAppointmentList().get(0).getProcedureType(), actual.getAppointmentList().get(0).getProcedureType());
//		assertEquals(expected.getAppointmentList().get(1).getProcedureType(), actual.getAppointmentList().get(1).getProcedureType());
//		assertEquals(expected.getAppointmentList().get(2).getProcedureType(), actual.getAppointmentList().get(2).getProcedureType());
//		
//		assertEquals(expected.getAppointmentList().get(0).getAppointmentId(), actual.getAppointmentList().get(0).getAppointmentId());
//		assertEquals(expected.getAppointmentList().get(1).getAppointmentId(), actual.getAppointmentList().get(1).getAppointmentId());
//		assertEquals(expected.getAppointmentList().get(2).getAppointmentId(), actual.getAppointmentList().get(2).getAppointmentId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getClinicId(), actual.getAppointmentList().get(0).getClinicId());
//		assertEquals(expected.getAppointmentList().get(1).getClinicId(), actual.getAppointmentList().get(1).getClinicId());
//		assertEquals(expected.getAppointmentList().get(2).getClinicId(), actual.getAppointmentList().get(2).getClinicId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getClinicName(), actual.getAppointmentList().get(0).getClinicName());
//		assertEquals(expected.getAppointmentList().get(1).getClinicName(), actual.getAppointmentList().get(1).getClinicName());
//		assertEquals(expected.getAppointmentList().get(2).getClinicName(), actual.getAppointmentList().get(2).getClinicName());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDate(), actual.getAppointmentList().get(0).getDate());
//		assertEquals(expected.getAppointmentList().get(1).getDate(), actual.getAppointmentList().get(1).getDate());
//		assertEquals(expected.getAppointmentList().get(2).getDate(), actual.getAppointmentList().get(2).getDate());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDiscount(), actual.getAppointmentList().get(0).getDiscount());
//		assertEquals(expected.getAppointmentList().get(1).getDiscount(), actual.getAppointmentList().get(1).getDiscount());
//		assertEquals(expected.getAppointmentList().get(2).getDiscount(), actual.getAppointmentList().get(2).getDiscount());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDoctor(), actual.getAppointmentList().get(0).getDoctor());
//		assertEquals(expected.getAppointmentList().get(1).getDoctor(), actual.getAppointmentList().get(1).getDoctor());
//		assertEquals(expected.getAppointmentList().get(2).getDoctor(), actual.getAppointmentList().get(2).getDoctor());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDoctorId(), actual.getAppointmentList().get(0).getDoctorId());
//		assertEquals(expected.getAppointmentList().get(1).getDoctorId(), actual.getAppointmentList().get(1).getDoctorId());
//		assertEquals(expected.getAppointmentList().get(2).getDoctorId(), actual.getAppointmentList().get(2).getDoctorId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getRoom(), actual.getAppointmentList().get(0).getRoom());
//		assertEquals(expected.getAppointmentList().get(1).getRoom(), actual.getAppointmentList().get(1).getRoom());
//		assertEquals(expected.getAppointmentList().get(2).getRoom(), actual.getAppointmentList().get(2).getRoom());
//		
//		assertEquals(expected.getAppointmentList().get(0).getPrice(), actual.getAppointmentList().get(0).getPrice());
//		assertEquals(expected.getAppointmentList().get(1).getPrice(), actual.getAppointmentList().get(1).getPrice());
//		assertEquals(expected.getAppointmentList().get(2).getPrice(), actual.getAppointmentList().get(2).getPrice());
//		
//		assertEquals(2, actual.getPossibleClinics().size());
//		
//		assertEquals(expected.getPossibleClinics().get(0), actual.getPossibleClinics().get(0));
//		assertEquals(expected.getPossibleClinics().get(1), actual.getPossibleClinics().get(1));
//		
//		assertEquals(2, actual.getPossibleDates().size());
//		
//		assertEquals(expected.getPossibleDates().get(0), actual.getPossibleDates().get(0));
//		assertEquals(expected.getPossibleDates().get(1), actual.getPossibleDates().get(1));
//		
//		assertEquals(3, actual.getPossibleDoctors().size());
//		
//		assertEquals(expected.getPossibleDoctors().get(0), actual.getPossibleDoctors().get(0));
//		assertEquals(expected.getPossibleDoctors().get(1), actual.getPossibleDoctors().get(1));
//		assertEquals(expected.getPossibleDoctors().get(2), actual.getPossibleDoctors().get(2));
//		
//		assertEquals(2, actual.getPossibleTypes().size());
//		
//		assertEquals(expected.getPossibleTypes().get(0), actual.getPossibleTypes().get(0));
//		assertEquals(expected.getPossibleTypes().get(1), actual.getPossibleTypes().get(1));
//
//		assertEquals(2, actual1.getAppointmentList().size());
//		
//		assertEquals(expected.getAppointmentList().get(0).getProcedureType(), actual1.getAppointmentList().get(0).getProcedureType());
//		assertEquals(expected.getAppointmentList().get(2).getProcedureType(), actual1.getAppointmentList().get(1).getProcedureType());
//		
//		assertEquals(expected.getAppointmentList().get(0).getAppointmentId(), actual1.getAppointmentList().get(0).getAppointmentId());
//		assertEquals(expected.getAppointmentList().get(2).getAppointmentId(), actual1.getAppointmentList().get(1).getAppointmentId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getClinicId(), actual1.getAppointmentList().get(0).getClinicId());
//		assertEquals(expected.getAppointmentList().get(2).getClinicId(), actual1.getAppointmentList().get(1).getClinicId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getClinicName(), actual1.getAppointmentList().get(0).getClinicName());
//		assertEquals(expected.getAppointmentList().get(2).getClinicName(), actual1.getAppointmentList().get(1).getClinicName());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDate(), actual1.getAppointmentList().get(0).getDate());
//		assertEquals(expected.getAppointmentList().get(2).getDate(), actual1.getAppointmentList().get(1).getDate());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDiscount(), actual1.getAppointmentList().get(0).getDiscount());
//		assertEquals(expected.getAppointmentList().get(2).getDiscount(), actual1.getAppointmentList().get(1).getDiscount());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDoctor(), actual1.getAppointmentList().get(0).getDoctor());
//		assertEquals(expected.getAppointmentList().get(2).getDoctor(), actual1.getAppointmentList().get(1).getDoctor());
//		
//		assertEquals(expected.getAppointmentList().get(0).getDoctorId(), actual1.getAppointmentList().get(0).getDoctorId());
//		assertEquals(expected.getAppointmentList().get(2).getDoctorId(), actual1.getAppointmentList().get(1).getDoctorId());
//		
//		assertEquals(expected.getAppointmentList().get(0).getRoom(), actual1.getAppointmentList().get(0).getRoom());
//		assertEquals(expected.getAppointmentList().get(2).getRoom(), actual1.getAppointmentList().get(1).getRoom());
//		
//		assertEquals(expected.getAppointmentList().get(0).getPrice(), actual1.getAppointmentList().get(0).getPrice());
//		assertEquals(expected.getAppointmentList().get(2).getPrice(), actual1.getAppointmentList().get(1).getPrice());
//		
//	}
//	
//	@Test
//	public void ReserveAppointmentShouldPass () {
//
//		this.app2.setStatus(AppointmentStateEnum.BLESSED);
//		this.app3.setStatus(AppointmentStateEnum.APPROVED);
//		
//		Mockito.when(this.appointmentRepository.getOne(15L)).thenReturn(app1);
//		Mockito.when(this.appointmentRepository.getOne(16L)).thenReturn(null);
//		Mockito.when(this.appointmentRepository.getOne(19L)).thenReturn(app2);
//		Mockito.when(this.appointmentRepository.getOne(34L)).thenReturn(app3);
//		
//		Boolean expected1 = true;
//		Boolean expected2 = false;
//		Boolean expected3 = false;
//		Boolean expected4 = false;
//		
//		Boolean actual1 = appointmentService.reserveAppointment(15L, 1L);
//		Boolean actual2 = appointmentService.reserveAppointment(16L, 1L);
//		Boolean actual3 = appointmentService.reserveAppointment(19L, 1L);
//		Boolean actual4 = appointmentService.reserveAppointment(34L, 1L);
//		
//		assertEquals(expected1, actual1);
//		assertEquals(expected2, actual2);
//		assertEquals(expected3, actual3);
//		assertEquals(expected4, actual4);
//		
//	}
//	
//	
//	@Test
//	public void addPatientDefinedAppointmentShouldPass () {
//		
//		app1.setStatus(AppointmentStateEnum.REQUESTED);
//		app2.setStatus(AppointmentStateEnum.DOCTOR_REQUESTED_APPOINTMENT);
//		app3.setStatus(AppointmentStateEnum.APPROVED);
//		
//		app2.setDoctor(app1.getDoctor());
//		app3.setDoctor(app1.getDoctor());
//		
//		List<AppointmentPOJO> list1 = new ArrayList<AppointmentPOJO> ();
//		list1.add(app1);
//		List<AppointmentPOJO> list2 = new ArrayList<AppointmentPOJO> ();
//		list2.add(app1);
//		list2.add(app2);
//		list2.add(app3);
//		List<AppointmentPOJO> list3 = new ArrayList<AppointmentPOJO> ();
//		
//		Calendar calendarMin1 = Calendar.getInstance();
//		Calendar calendarMax1 = Calendar.getInstance();
//		calendarMin1.set(Calendar.YEAR, 2020);
//		calendarMin1.set(Calendar.MONTH, 3);
//		calendarMin1.set(Calendar.DAY_OF_MONTH, 3);
//		calendarMin1.set(Calendar.HOUR_OF_DAY, 4);
//		calendarMin1.set(Calendar.MINUTE, 0);
//		calendarMin1.set(Calendar.SECOND, 0);
//
//		calendarMax1.set(Calendar.YEAR, 2020);
//		calendarMax1.set(Calendar.MONTH, 3);
//		calendarMax1.set(Calendar.DAY_OF_MONTH, 3);
//		calendarMax1.set(Calendar.HOUR_OF_DAY, 5);
//		calendarMax1.set(Calendar.MINUTE, 0);
//		calendarMax1.set(Calendar.SECOND, 0);
//		
//		Mockito.when(this.doctorRepository.getOne(1L)).thenReturn(app1.getDoctor());
//		Mockito.when(this.appointmentRepository.getDoctorsAppointments(1L, calendarMin1, calendarMax1)).thenReturn(list2);
//	
//		Boolean expected1 = true;
//		
//		Boolean actual1 = false;
//		
//		try {
//			actual1 = appointmentService.addAppointment(1L, "2020-04-05 04:00:00", 1L);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		assertEquals(expected1, actual1);
//		
//	}
//	
	@Test
	public void testInsertNewAppointment () {
	
		Mockito.when(this.doctorRepository.getOne(1L)).thenReturn(d1);
		
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		String dateString = sdf.format(app1.getDate().getTime());
//				
		Boolean returnValue1 = null;
		try {
			returnValue1 = appointmentService.addAppointment(app1.getDoctor().getId(), dateString, app1.getPatient().getId());
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertEquals (true, returnValue1);
		
	}
	
	
}
