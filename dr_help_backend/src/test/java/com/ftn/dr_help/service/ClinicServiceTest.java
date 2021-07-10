package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
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

import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.AppointmentRepository;
import com.ftn.dr_help.repository.ClinicRepository;
import com.ftn.dr_help.repository.DoctorRepository;

@RunWith (SpringRunner.class)
@SpringBootTest
public class ClinicServiceTest {

	@InjectMocks
	@Autowired
	private ClinicService clinicService;
	
	@MockBean
	private ClinicRepository clinicRepository;
	
	@MockBean
	private DoctorRepository doctorRepository;
	
	@MockBean
	private AppointmentRepository appointmentRepository;
	
	private ClinicPOJO c1;
	private ClinicPOJO c2;
	private ClinicPOJO c3;
	private ClinicPOJO c4;
	private List<ClinicPOJO> clinicList;
	private List<ClinicPOJO> psychoanalyticClinics;
	
	private ProceduresTypePOJO p1;
	private ProceduresTypePOJO p2;
	
	private DoctorPOJO d1;
	private DoctorPOJO d2;
	private DoctorPOJO d3;
	List<DoctorPOJO> zdravogUmaDoctors;
	List<DoctorPOJO> zdravogUmaPsihoanaliza;
	
	@Before 
	public void setUp () {
		
		c1 = new ClinicPOJO();
		c1.setName("Klinika zdravog uma");
		c1.setAddress("7A Bulevar despota Stefana");
		c1.setCity("Novi Sad");
		c1.setState("Serbia");
		c1.setId(1L);
		
		c2 = new ClinicPOJO();
		c2.setName("Arkham");
		c2.setAddress("7 Bulevar despota Stefana");
		c2.setCity("Novi Sad");
		c2.setState("Serbia");
		c2.setId(2L);
		
		c3 = new ClinicPOJO();
		c3.setName("Princeton General Hospital");
		c3.setAddress("5A Bulevar despota Stefana");
		c3.setCity("Novi Sad");
		c3.setState("Serbia");
		c3.setId(3L);
		
		c4 = new ClinicPOJO ();
		c4.setName("Nasa mala klinika");
		c4.setAddress("2A Bulevar despota Stefana");
		c4.setCity("Podgorica");
		c4.setState("Montenegro");
		c4.setId(4L);
		
		clinicList = new ArrayList<ClinicPOJO> ();
		clinicList.add(c1);
		clinicList.add(c2);
		clinicList.add(c3);
		clinicList.add(c4);
		
		psychoanalyticClinics = new ArrayList<ClinicPOJO> ();
		psychoanalyticClinics.add(c1);
		psychoanalyticClinics.add(c3);
		
		p1 = new ProceduresTypePOJO ();
		p1.setId(1L);
		p1.setName("Psihoanaliza");
		
		p2 = new ProceduresTypePOJO ();
		p2.setId(2L);
		p2.setName("Ortorinolaringolog");
		
		d1 = new DoctorPOJO ();
		d1.setFirstName("Pera");
		d1.setLastName("Peric");
		d1.setProcedureType(p1);
		d1.setClinic(c1);
		
		d2 = new DoctorPOJO ();
		d2.setFirstName("Jovan");
		d2.setLastName("Jovic");
		d2.setProcedureType(p1);
		d2.setClinic(c1);
		
		d3 = new DoctorPOJO ();
		d3.setFirstName("Mile");
		d3.setLastName("Prodan");
		d3.setProcedureType(p2);
		d3.setClinic(c3);
		
		zdravogUmaDoctors = new ArrayList<DoctorPOJO> ();
		zdravogUmaDoctors.add(d1);
		zdravogUmaDoctors.add(d2);
		
		zdravogUmaPsihoanaliza = new ArrayList<DoctorPOJO> ();
		zdravogUmaPsihoanaliza.add(d1);
		
	}
	
	@Test
	public void findAllClinicsShouldPass () {
		
		Mockito.when(this.clinicRepository.findAll()).thenReturn(clinicList);
		
		List<ClinicPOJO> actualList = clinicService.findAll();
		
		assertEquals(4, actualList.size());
		
		assertEquals("Klinika zdravog uma", actualList.get(0).getName());
		assertEquals("7A Bulevar despota Stefana", actualList.get(0).getAddress());
		assertEquals("Novi Sad", actualList.get(0).getCity());
		assertEquals("Serbia", actualList.get(0).getState());
		
		assertEquals("Arkham", actualList.get(1).getName());
		assertEquals("7 Bulevar despota Stefana", actualList.get(1).getAddress());
		assertEquals("Novi Sad", actualList.get(1).getCity());
		assertEquals("Serbia", actualList.get(1).getState());
		
		assertEquals("Princeton General Hospital", actualList.get(2).getName());
		assertEquals("5A Bulevar despota Stefana", actualList.get(2).getAddress());
		assertEquals("Novi Sad", actualList.get(2).getCity());
		assertEquals("Serbia", actualList.get(2).getState());
		
		assertEquals("Nasa mala klinika", actualList.get(3).getName());
		assertEquals("2A Bulevar despota Stefana", actualList.get(3).getAddress());
		assertEquals("Podgorica", actualList.get(3).getCity());
		assertEquals("Montenegro", actualList.get(3).getState());
		
	}
	
	public void filterClinicsByAppointmentTypeShouldPass () {
		
		Mockito.when(this.clinicRepository.getClinicsByProcedureType("Psihoanaliza")).thenReturn(psychoanalyticClinics);
		Mockito.when(this.clinicRepository.getClinicsByProcedureType("Frenologija")).thenReturn(new ArrayList<ClinicPOJO> ());
		
		List<ClinicPOJO> actualList1 = clinicService.filterByProcedureType("Psihoanaliza");
		List<ClinicPOJO> actualList2 = clinicService.filterByProcedureType("Frenologija");
		
		assertEquals(2, actualList1.size());
		
		assertEquals("Klinika zdravog uma", actualList1.get(0).getName());
		assertEquals("7A Bulevar despota Stefana", actualList1.get(0).getAddress());
		assertEquals("Novi Sad", actualList1.get(0).getCity());
		assertEquals("Serbia", actualList1.get(0).getState());
		
		assertEquals("Princeton General Hospital", actualList1.get(1).getName());
		assertEquals("5A Bulevar despota Stefana", actualList1.get(1).getAddress());
		assertEquals("Novi Sad", actualList1.get(1).getCity());
		assertEquals("Serbia", actualList1.get(1).getState());
		
		assertEquals(0, actualList2.size());
		
	}
	
	public void filterClinicsByDateShouldPass () {
		
		Mockito.when(this.doctorRepository.findAllByClinic_id(1L)).thenReturn(zdravogUmaDoctors);
		Mockito.when(this.doctorRepository.findAllByClinic_id(2L)).thenReturn(new ArrayList<DoctorPOJO> ());
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(1L, "Psihoanaliza")).thenReturn(zdravogUmaPsihoanaliza);
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(1L, "Frenologija")).thenReturn(new ArrayList<DoctorPOJO> ());
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(2L, "Psihoanaliza")).thenReturn(new ArrayList<DoctorPOJO> ());
		Mockito.when(this.doctorRepository.filterByClinicAndProcedureType(3L, "Ortorinolaringolog")).thenReturn(new ArrayList<DoctorPOJO> ());
		
		List<ClinicPOJO> actualList1 = new ArrayList<ClinicPOJO> ();
		List<ClinicPOJO> actualList2 = new ArrayList<ClinicPOJO> ();
		List<ClinicPOJO> actualList3 = new ArrayList<ClinicPOJO> ();
		List<ClinicPOJO> actualList4 = new ArrayList<ClinicPOJO> ();
		try {
			actualList1 = clinicService.filterByDate(clinicList, "Psihoanaliza", "2020-05-12 12:00:00");
			actualList2 = clinicService.filterByDate(clinicList, "Frenologija", "2020-05-12 12:00:00");
			actualList3 = clinicService.filterByDate(psychoanalyticClinics, "Psihoanaliza", "2020-05-12 12:00:00");
			actualList4 = clinicService.filterByDate(psychoanalyticClinics, "Frenologija", "2020-05-12 12:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//psychoanalyticClinics
		assertEquals(2, actualList1.size());
		
		assertEquals("Klinika zdravog uma", actualList1.get(0).getName());
		assertEquals("7A Bulevar despota Stefana", actualList1.get(0).getAddress());
		assertEquals("Novi Sad", actualList1.get(0).getCity());
		assertEquals("Serbia", actualList1.get(0).getState());
		
		assertEquals("Princeton General Hospital", actualList1.get(1).getName());
		assertEquals("5A Bulevar despota Stefana", actualList1.get(1).getAddress());
		assertEquals("Novi Sad", actualList1.get(1).getCity());
		assertEquals("Serbia", actualList1.get(1).getState());
		
		assertEquals(0, actualList2.size());
		assertEquals(0, actualList3.size());
		assertEquals(0, actualList4.size());
	}
	
	
}
