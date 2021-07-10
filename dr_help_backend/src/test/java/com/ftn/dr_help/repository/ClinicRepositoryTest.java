package com.ftn.dr_help.repository;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.constants.ClinicConstants;
import com.ftn.dr_help.model.pojo.ClinicPOJO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClinicRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired 
	private ClinicRepository clinicRepository;
	
	private ClinicPOJO c1;
	private ClinicPOJO c2;
	private ClinicPOJO c3;
	private ClinicPOJO c4;
	
	@Before
	public void setUp () {

		c1 = new ClinicPOJO();
		c1.setAddress("7A Bulevar despota Stefana");
		c1.setCity("Novi Sad");
		c1.setDescription("Klinika je namenjena za kreativne opise. ");
		c1.setName("Klinika zdravog uma. ");
		c1.setState("Serbia");
		
		c2 = new ClinicPOJO();
		c2.setAddress("7 Bulevar despota Stefana");
		c2.setCity("Novi Sad");
		c2.setDescription("Assylum for the criminally insane. ");
		c2.setName("Arkham. ");
		c2.setState("Serbia");
		
		c3 = new ClinicPOJO();
		c3.setAddress("5A Bulevar despota Stefana");
		c3.setCity("Novi Sad");
		c3.setDescription("Free, publically open clinic. ");
		c3.setName("Princeton Plainsborough hospital. ");
		c3.setState("Serbia");
		
		c4 = new ClinicPOJO();
		c4.setAddress("2A Bulevar despota Stefana");
		c4.setCity("Podgorica");
		c4.setDescription("Mali svet pun radosti. ");
		c4.setName("Nasa mala klinika. ");
		c4.setState("Montenegro");
		
		this.entityManager.persist(c1);
		this.entityManager.persist(c2);
		this.entityManager.persist(c3);
		this.entityManager.persist(c4);
		
	}
	
	@Test
	public void testGetAll () {
		
		List<ClinicPOJO> actualClinicList = this.clinicRepository.findAll();
		

		System.out.println("");
		System.out.println("");
		System.out.println("Listing the clinics out: ");
		for (ClinicPOJO c : actualClinicList) {
			System.out.println(c.getName());
		}
		System.out.println("");
		System.out.println("");
		

		assertEquals((int) ClinicConstants.CLINIC_COUNT, actualClinicList.size());
	
	}
	
	@Test
	public void testGetByExistingProcedureType () {
		
		List<ClinicPOJO> actualList = this.clinicRepository.getClinicsByProcedureType("Psychotherapy");
		
		assertEquals (3, actualList.size());
	}
	
	@Test
	public void testGetByUnexistingProcedureType () {
		
		List<ClinicPOJO> actualList = this.clinicRepository.getClinicsByProcedureType("PPhrenollogy");
		
		assertEquals (0, actualList.size());
	}

}
