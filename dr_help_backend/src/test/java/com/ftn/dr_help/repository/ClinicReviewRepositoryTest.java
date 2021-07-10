package com.ftn.dr_help.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClinicReviewRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ClinicReviewRepository clinicReviewRepository;
	
	@Test
	public void testGetAverageReview () {
		Float actualReview1 = clinicReviewRepository.getAverageReview(1L);
		Float actualReview2 = clinicReviewRepository.getAverageReview(2L);
		Float actualReview3 = clinicReviewRepository.getAverageReview(5764L);
		
		assertEquals (3.0, actualReview1.floatValue(), 0.01);
		assertEquals (null, actualReview2);
		assertEquals (null, actualReview3);
	}
	
}
