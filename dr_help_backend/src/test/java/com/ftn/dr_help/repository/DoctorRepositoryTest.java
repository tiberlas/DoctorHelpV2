package com.ftn.dr_help.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.ftn.dr_help.model.pojo.DoctorPOJO;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class DoctorRepositoryTest {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void test() {
		
		List<DoctorPOJO> before;
		List<DoctorPOJO> after;
		
		before = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		before.get(0).setProcedureType(null);
		entityManager.merge(before.get(0));
		
		after = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		assertEquals(before.size()-1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test2() {
		
		List<DoctorPOJO> before;
		List<DoctorPOJO> after;
		
		before = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		before.get(0).setDeleted(true);;
		entityManager.merge(before.get(0));
		
		after = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		assertEquals(before.size()-1, after.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test3() {
		
		List<DoctorPOJO> before;
		List<DoctorPOJO> after;
		
		before = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		for(DoctorPOJO doctor : before) {
			doctor.setProcedureType(null);
			entityManager.merge(doctor);
		}
		
		after = doctorRepository.findAllDoctorsWihtSpetialization(1L);
		
		assertTrue(after.isEmpty());
	}


	
	@Before
	public void setUp () {
		
	}
	
	@Test
	public void testFindAllByClinic_id () {
		
		List<DoctorPOJO> actualList = doctorRepository.findAllByClinic_id(1L);
		
		assertEquals (8, actualList.size());
	}

}
