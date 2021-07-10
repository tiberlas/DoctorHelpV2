package com.ftn.dr_help.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.ProcedureTypeRepository;

@RunWith (SpringRunner.class)
@SpringBootTest
public class ProcedureTypeServiceTest {

	@InjectMocks
	private ProcedureTypeService procedureTypeService;
	
	@MockBean
	private ProcedureTypeRepository procedureTypeRepository;
	
	private String s1;
	private String s2;
	private String s3;
	private List<String> procedureTypeList;
	
	@Before
	public void setUp () {
		
		MockitoAnnotations.initMocks(this);
		
		s1 = "General purpose exam";
		s2 = "Orthopedic checkup";
		s3 = "MRI scan";
		
		procedureTypeList = new ArrayList<String> ();
		procedureTypeList.add(s1);
		procedureTypeList.add(s2);
		procedureTypeList.add(s3);
	
	}
	
	@Test
	public void testGetProcedureTypes () {
		Mockito.when(this.procedureTypeRepository.getProcedureTypes()).thenReturn(procedureTypeList);
		
		List<String> actualList = procedureTypeService.getProcedureTypes();
		
		assertEquals (3, actualList.size());
		assertEquals ("General purpose exam", actualList.get(0));
		assertEquals ("Orthopedic checkup", actualList.get(1));
		assertEquals ("MRI scan", actualList.get(2));
	}
	
	public void testGetPrice () {
		Mockito.when(this.procedureTypeRepository.getPrice(1L, "General purpose exam")).thenReturn(35.0);
		Mockito.when(this.procedureTypeRepository.getPrice(1L, "Orthopedic checkup")).thenReturn(50.0);
		Mockito.when(this.procedureTypeRepository.getPrice(2L, "General purpose exam")).thenReturn(15.0);
		
		Double actual1 = procedureTypeService.getPrice(1L, "General purpose exam");
		Double actual2 = procedureTypeService.getPrice(1L, "Orthopedic checkup");
		Double actual3 = procedureTypeService.getPrice(2L, "General purpose exam");
		Double actual4 = procedureTypeService.getPrice(235L, "General purpose exam");
		
		assertEquals (35.0, actual1.doubleValue(), 0.01);
		assertEquals (50.0, actual2.doubleValue(), 0.01);
		assertEquals (15.0, actual3.doubleValue(), 0.01);
		assertEquals (null, actual4);
		
	}
	
}
