package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.comon.DateConverter;
import com.ftn.dr_help.comon.schedule.CalculateFirstFreeSchedule;
import com.ftn.dr_help.dto.AbsenceInnerDTO;
import com.ftn.dr_help.dto.DoctorListingDTO;
import com.ftn.dr_help.model.convertor.WorkScheduleAdapter;
import com.ftn.dr_help.model.enums.Shift;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.DoctorReviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorServiceTest {

	@InjectMocks
	private DoctorService service;

	@MockBean
	private DoctorRepository doctorRepository;

	@Autowired
	private DateConverter dateConverter;

	@Autowired
	private CalculateFirstFreeSchedule calculate;

	@Autowired
	private WorkScheduleAdapter workSchedule;

	@MockBean
	private ClinicRepository clinicRepository;

	@MockBean
	private DoctorReviewRepository doctorReviewRepository;

	@MockBean
	AppointmentRepository appointmentRepository;

	private DoctorPOJO dr0 = new DoctorPOJO();
	private DoctorPOJO dr1 = new DoctorPOJO();
	private DoctorPOJO dr2 = new DoctorPOJO();
	private List<DoctorPOJO> doctorList0 = new ArrayList<DoctorPOJO>();
	private List<DoctorPOJO> doctorList1 = new ArrayList<DoctorPOJO>();

	private ClinicPOJO c0 = new ClinicPOJO();
	private ClinicPOJO c1 = new ClinicPOJO();
	private ClinicPOJO c2 = new ClinicPOJO();
	private List<ClinicPOJO> clinics;

	private ProceduresTypePOJO type0;
	private ProceduresTypePOJO type1;

	private List<Date> dates0;
	private List<Date> dates1;
	private List<Date> dates2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Calendar duration = Calendar.getInstance();
		duration.set(2000, 2, 15, 2, 0);

		type0 = new ProceduresTypePOJO();
		type0.setId(13l);
		type0.setDuration(duration.getTime());
		type0.setDeleted(false);
		type0.setOperation(false);
		type0.setName("Psihoanaliza");

		type1 = new ProceduresTypePOJO();
		type1.setId(14l);
		type1.setDuration(duration.getTime());
		type1.setDeleted(false);
		type1.setOperation(false);
		type1.setName("Ortorinolaingolog");

		getOperations();

		c0.setId(1L);
		c1.setId(2L);
		c2.setId(3L);

		dr0.setId(0l);
		dr0.setDeleted(false);
		dr0.setMonday(Shift.FIRST);
		dr0.setTuesday(Shift.FIRST);
		dr0.setWednesday(Shift.NONE);
		dr0.setThursday(Shift.SECOND);
		dr0.setFriday(Shift.SECOND);
		dr0.setSaturday(Shift.NONE);
		dr0.setSunday(Shift.NONE);
		dr0.setProcedureType(type0);
		dr0.setClinic(c0);
		dr0.setFirstName("Pera");
		dr0.setLastName("Peric");

		dr2.setId(2l);
		dr2.setDeleted(false);
		dr2.setMonday(Shift.NONE);
		dr2.setTuesday(Shift.THIRD);
		dr2.setWednesday(Shift.SECOND);
		dr2.setThursday(Shift.SECOND);
		dr2.setFriday(Shift.SECOND);
		dr2.setSaturday(Shift.NONE);
		dr2.setSunday(Shift.NONE);
		dr2.setProcedureType(type1);
		dr2.setClinic(c0);
		dr2.setFirstName("Jova");
		dr2.setLastName("Jovic");

		dr1.setId(1l);
		dr1.setDeleted(false);
		dr1.setMonday(Shift.NONE);
		dr1.setTuesday(Shift.FIRST);
		dr1.setWednesday(Shift.NONE);
		dr1.setThursday(Shift.SECOND);
		dr1.setFriday(Shift.SECOND);
		dr1.setSaturday(Shift.NONE);
		dr1.setSunday(Shift.NONE);
		dr1.setProcedureType(type0);
		dr1.setClinic(c1);
		dr1.setFirstName("Lorem");
		dr1.setLastName("Ipsum");

		clinics = new ArrayList<ClinicPOJO>();
		clinics.add(c0);
		clinics.add(c1);
		clinics.add(c2);

		doctorList0.add(dr0);
		doctorList0.add(dr2);

		doctorList1.add(dr0);
	}

	private void getOperations() {
		dates0 = new ArrayList<>();
		dates1 = new ArrayList<>();
		dates2 = new ArrayList<>();

		Calendar c00 = Calendar.getInstance();
		Calendar c01 = Calendar.getInstance();
		Calendar c02 = Calendar.getInstance();
		Calendar c03 = Calendar.getInstance();
		Calendar c10 = Calendar.getInstance();
		Calendar c11 = Calendar.getInstance();
		Calendar c12 = Calendar.getInstance();
		Calendar c20 = Calendar.getInstance();
		Calendar c21 = Calendar.getInstance();
		Calendar c22 = Calendar.getInstance();
		Calendar c23 = Calendar.getInstance();

		c00.set(2020, 0, 13, 8, 10);
		c01.set(2020, 0, 13, 12, 10);
		c02.set(2020, 0, 16, 17, 10);
		c03.set(2020, 0, 16, 20, 0); // 22:00
		c10.set(2020, 0, 14, 8, 10);
		c11.set(2020, 0, 14, 13, 10);
		c12.set(2020, 0, 16, 16, 10);// 18:10
		c20.set(2020, 0, 14, 2, 10);
		c21.set(2020, 0, 15, 18, 10);
		c22.set(2020, 0, 16, 16, 10);
		c23.set(2020, 0, 16, 19, 20);// 21:20

		c00.clear(Calendar.SECOND);
		c00.clear(Calendar.MILLISECOND);
		c01.clear(Calendar.SECOND);
		c01.clear(Calendar.MILLISECOND);
		c02.clear(Calendar.SECOND);
		c02.clear(Calendar.MILLISECOND);
		c03.clear(Calendar.SECOND);
		c03.clear(Calendar.MILLISECOND);
		c10.clear(Calendar.SECOND);
		c10.clear(Calendar.MILLISECOND);
		c11.clear(Calendar.SECOND);
		c11.clear(Calendar.MILLISECOND);
		c12.clear(Calendar.SECOND);
		c12.clear(Calendar.MILLISECOND);
		c20.clear(Calendar.SECOND);
		c20.clear(Calendar.MILLISECOND);
		c21.clear(Calendar.SECOND);
		c21.clear(Calendar.MILLISECOND);
		c22.clear(Calendar.SECOND);
		c22.clear(Calendar.MILLISECOND);
		c23.clear(Calendar.SECOND);
		c23.clear(Calendar.MILLISECOND);

		dates0.add(c00.getTime());
		dates0.add(c01.getTime());
		dates0.add(c02.getTime());
		dates0.add(c03.getTime());
		dates1.add(c10.getTime());
		dates1.add(c11.getTime());
		dates1.add(c12.getTime());
		dates2.add(c20.getTime());
		dates2.add(c12.getTime());
		dates2.add(c22.getTime());
		dates2.add(c23.getTime());

	}

	@Test
	public void testMocking() {
		System.out.println(1);

		DoctorPOJO doca = new DoctorPOJO();
		doca.setId(1l);
		doca.setDeleted(false);
		doca.setFirstName("TIBI");
		Mockito.when(this.doctorRepository.findById(0l)).thenReturn(Optional.of(doca));

		DoctorPOJO dr = doctorRepository.findById(0l).orElse(null);

		assertEquals("TIBI", dr.getFirstName());
	}

	@Test
	public void findScheduleTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-15 08:00");

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, null);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 22, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleJumpTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 12, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			Calendar newDate = Calendar.getInstance();
			newDate.set(2020, 0, 13, 14, 10, 0);
			newDate.set(Calendar.MILLISECOND, 0);
			dates0.add(2, newDate.getTime());

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, null);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 14, 8, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleBeforeTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 12, 12, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, null);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 13, 10, 10, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleAfterTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 18, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			dr0.setTuesday(Shift.NONE);

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, null);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 22, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleTest1() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 11, 18, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			Calendar newDate = Calendar.getInstance();
			newDate.set(2020, 0, 13, 10, 10, 0);
			newDate.set(Calendar.MILLISECOND, 0);
			dates0.add(1, newDate.getTime());

			System.out.println("LISTA DATUMA :)");
			for (Date d : dates0) {
				System.out.println(d.toString());
			}

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, null);

			Calendar expected = Calendar.getInstance();

			expected.set(2020, 0, 13, 14, 10, 0);
			expected.set(2020, 0, 14, 8, 0, 0);

			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleWithHolidayTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 7, 18, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			List<AbsenceInnerDTO> absence = new ArrayList<>();
			Calendar absenceBegin = Calendar.getInstance();
			Calendar absenceEnd = Calendar.getInstance();

			absenceBegin.set(2020, 0, 8, 0, 0, 0);
			absenceEnd.set(2020, 0, 12, 0, 0, 0);

			absence.add(new AbsenceInnerDTO(absenceBegin.getTime(), absenceEnd.getTime()));

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, absence);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 13, 10, 10, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleWithHolidayTest1() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 18, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			List<AbsenceInnerDTO> absence = new ArrayList<>();
			Calendar absenceBegin = Calendar.getInstance();
			Calendar absenceEnd = Calendar.getInstance();

			absenceBegin.set(2020, 0, 14, 0, 0, 0);
			absenceEnd.set(2020, 0, 18, 0, 0, 0);

			absence.add(new AbsenceInnerDTO(absenceBegin.getTime(), absenceEnd.getTime()));

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, absence);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 20, 8, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void findScheduleWithHolidayTest2() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 18, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			List<AbsenceInnerDTO> absence = new ArrayList<>();
			Calendar absenceBegin = Calendar.getInstance();
			Calendar absenceEnd = Calendar.getInstance();

			absenceBegin.set(2020, 0, 25, 0, 0, 0);
			absenceEnd.set(2020, 0, 28, 0, 0, 0);

			absence.add(new AbsenceInnerDTO(absenceBegin.getTime(), absenceEnd.getTime()));

			Calendar finded;
			finded = calculate.findFirstScheduleForDoctor(workSchedule.fromDoctor(dr0), begin, dates0, absence);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 14, 8, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void FindFirstScheduleOperationTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-15 08:00");

			dr2.setThursday(Shift.NONE);
			dates2.remove(3);
			dates2.remove(2);

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 17, 16, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void FindFirstEqualShiftOperation2Test() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-17 08:00");

			dr2.setFriday(Shift.NONE);
			dr1.setFriday(Shift.NONE);
			dr0.setFriday(Shift.NONE);
			dr0.setMonday(Shift.FIRST);
			dr1.setMonday(Shift.FIRST);
			dr2.setMonday(Shift.FIRST);

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 20, 8, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void FindFirstEqualShiftOperation3Test() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-17 08:00");

			dr2.setFriday(Shift.NONE);

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 23, 16, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			// fail();
		}
	}

	@Test
	public void FindFirstEqualShiftOperation4Test() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-16 08:00");

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 22, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void FindFirstEqualShiftOperation5Test() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-13 08:00");

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 22, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void FindFirstEqualShiftOperation6Test() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin = dateConverter.stringToDate("2020-01-12 08:00");

			dates0.remove(dates0.size() - 1);

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 21, 20, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void SkipDayOperationTest() throws ParseException {
		try {
			Calendar begin = Calendar.getInstance();
			begin.set(2020, 0, 13, 12, 30, 0);
			begin.set(Calendar.MILLISECOND, 0);

			dr0.setMonday(Shift.FIRST);
			dr1.setMonday(Shift.FIRST);
			dr2.setMonday(Shift.FIRST);

			Calendar newDate = Calendar.getInstance();
			newDate.set(2020, 0, 13, 14, 10, 0);
			newDate.set(Calendar.MILLISECOND, 0);
			dates0.add(2, newDate.getTime());

			Calendar finded;
			finded = calculate.findFirstScheduleForOperation(dr0, dr1, dr2, dates0, dates1, dates2, null, null, null,
					begin);

			Calendar expected = Calendar.getInstance();
			expected.set(2020, 0, 16, 22, 0, 0);
			expected.set(Calendar.MILLISECOND, 0);

			assertEquals(expected, finded);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void doctorListingShouldPass() {

		Mockito.when(this.doctorRepository.findAllByClinic_id(1L)).thenReturn(doctorList0);
		Mockito.when(this.doctorRepository.findAllByClinic_id(17L)).thenReturn(new ArrayList<DoctorPOJO>());
		Mockito.when(this.doctorReviewRepository.getAverageReview(0L)).thenReturn((float) 3.8);
		Mockito.when(this.doctorReviewRepository.getAverageReview(1L)).thenReturn((float) 2.3);
		Mockito.when(this.doctorReviewRepository.getAverageReview(2L)).thenReturn(null);
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(1L, "Psihoanaliza")).thenReturn(doctorList1);
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(1L, "Frenologija"))
				.thenReturn(new ArrayList<DoctorPOJO>());
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(13L, "Psihoanaliza"))
				.thenReturn(new ArrayList<DoctorPOJO>());
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(13L, "Frenologija"))
				.thenReturn(new ArrayList<DoctorPOJO>());

		List<DoctorListingDTO> actual1 = service.filterByClinic(1L);
		List<DoctorListingDTO> actual2 = service.filterByClinic(17L);
		List<DoctorListingDTO> actual3 = new ArrayList<DoctorListingDTO>();
		List<DoctorListingDTO> actual4 = new ArrayList<DoctorListingDTO>();
		List<DoctorListingDTO> actual5 = new ArrayList<DoctorListingDTO>();
		List<DoctorListingDTO> actual6 = new ArrayList<DoctorListingDTO>();
		try {
			actual3 = service.filterByClinicDateProcedureType(1L, "Psihoanaliza", "2020-02-17");
			actual4 = service.filterByClinicDateProcedureType(1L, "Frenologija", "2020-02-17");
			actual5 = service.filterByClinicDateProcedureType(13L, "Psihoanaliza", "2020-02-17");
			actual6 = service.filterByClinicDateProcedureType(13L, "Frenologija", "2020-02-17");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("");
		System.out.println("");
		System.out.println("A list of found doctors: ");
		for (DoctorListingDTO d : actual3) {
			System.out.println("Doctor: " + d.getFirstName() + " " + d.getLastName() + " Rating: " + d.getRating());
			for (String t : d.getTerms()) {
				System.out.println("\t" + t);
			}
		}
		System.out.println("");
		System.out.println("");

		assertEquals(2, actual1.size());

		assertEquals("Pera", actual1.get(0).getFirstName());
		assertEquals("Peric", actual1.get(0).getLastName());
		assertEquals("3.8", actual1.get(0).getRating());
		assertEquals(0, actual1.get(0).getTerms().size());

		assertEquals("Jova", actual1.get(1).getFirstName());
		assertEquals("Jovic", actual1.get(1).getLastName());
		assertEquals("/", actual1.get(1).getRating());
		assertEquals(0, actual1.get(1).getTerms().size());

		assertEquals(0, actual2.size());

		assertEquals(1, actual3.size());

		assertEquals("Pera", actual3.get(0).getFirstName());
		assertEquals("Peric", actual3.get(0).getLastName());
		assertEquals("3.8", actual3.get(0).getRating());
		assertEquals(25, actual3.get(0).getTerms().size());
		assertEquals("8:0", actual3.get(0).getTerms().get(0));
		assertEquals("8:15", actual3.get(0).getTerms().get(1));
		assertEquals("8:30", actual3.get(0).getTerms().get(2));
		assertEquals("8:45", actual3.get(0).getTerms().get(3));
		assertEquals("9:0", actual3.get(0).getTerms().get(4));
		assertEquals("9:15", actual3.get(0).getTerms().get(5));
		assertEquals("9:30", actual3.get(0).getTerms().get(6));
		assertEquals("9:45", actual3.get(0).getTerms().get(7));
		assertEquals("10:0", actual3.get(0).getTerms().get(8));
		assertEquals("10:15", actual3.get(0).getTerms().get(9));
		assertEquals("10:30", actual3.get(0).getTerms().get(10));
		assertEquals("10:45", actual3.get(0).getTerms().get(11));
		assertEquals("11:0", actual3.get(0).getTerms().get(12));
		assertEquals("11:15", actual3.get(0).getTerms().get(13));
		assertEquals("11:30", actual3.get(0).getTerms().get(14));
		assertEquals("11:45", actual3.get(0).getTerms().get(15));
		assertEquals("12:0", actual3.get(0).getTerms().get(16));
		assertEquals("12:15", actual3.get(0).getTerms().get(17));
		assertEquals("12:30", actual3.get(0).getTerms().get(18));
		assertEquals("12:45", actual3.get(0).getTerms().get(19));
		assertEquals("13:0", actual3.get(0).getTerms().get(20));
		assertEquals("13:15", actual3.get(0).getTerms().get(21));
		assertEquals("13:30", actual3.get(0).getTerms().get(22));
		assertEquals("13:45", actual3.get(0).getTerms().get(23));
		assertEquals("14:0", actual3.get(0).getTerms().get(24));

		assertEquals(0, actual4.size());

		assertEquals(0, actual5.size());

		assertEquals(0, actual6.size());

	}

}