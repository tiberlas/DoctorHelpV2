package com.ftn.dr_help.repository;

import static org.junit.Assert.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.model.pojo.RoomPOJO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProcedureTypeRepositoryTest {

	@Autowired
	private ProcedureTypeRepository procedureTypeRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll() {
		List<String> before;
		List<String> after;
		
		before = procedureTypeRepository.getProcedureTypes();
		
		ProceduresTypePOJO procedure = procedureTypeRepository.getOne(2l);
		procedure.setDeleted(true);
		entityManager.merge(procedure);
		
		after = procedureTypeRepository.getProcedureTypes();
		
		assertEquals(before.size()-1, after.size());
	}

	@Test
	public void testGetPrice () {
		
		Double actualValue1 = procedureTypeRepository.getPrice(1L, "Psychotherapy");
		Double actualValue2 = procedureTypeRepository.getPrice(5L, "Psychotherapy");
		Double actualValue3 = procedureTypeRepository.getPrice(1L, "Phrenollogy");
		
		assertEquals (30, actualValue1.floatValue(), 0.001);
		assertEquals (35, actualValue2.floatValue(), 0.001);
		assertEquals (null, actualValue3);
		
	}
	
}
