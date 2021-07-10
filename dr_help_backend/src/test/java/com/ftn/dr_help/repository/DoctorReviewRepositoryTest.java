package com.ftn.dr_help.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DoctorReviewRepositoryTest {

	@Autowired 
	TestEntityManager entityManager;
	
	@Autowired
	private DoctorReviewRepository doctorReviewRepository;
	
	@Before
	public void setUp () {}

	@Test
	public void testGetAverageReview () {
		Float actual1;
		Float actual2;
		Float actual3;
		
		actual1 = doctorReviewRepository.getAverageReview(1L);
		actual2 = doctorReviewRepository.getAverageReview(2L);
		actual3 = doctorReviewRepository.getAverageReview(2456L);
		
		System.out.println("");
		System.out.println("");
		System.out.println("Review1: " + actual1);
		System.out.println("Review2: " + actual2);
		System.out.println("Review3: " + actual3);
		System.out.println("");
		System.out.println("");
		
		assertEquals (null, actual1);
		assertEquals (5.0, actual2.floatValue(), 0.01);
		assertEquals (null, actual3);
	}
	
	
	
}
