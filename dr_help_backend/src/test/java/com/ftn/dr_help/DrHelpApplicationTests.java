
package com.ftn.dr_help;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.dr_help.model.pojo.NursePOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrHelpApplicationTests {

	@Test
	public void shouldNotPass() {
		NursePOJO nurse = new NursePOJO();
		nurse.setFirstName("ANA");

		assertEquals("ANA", nurse.getFirstName());
	}

	@Test
	public void shouldPass() {
		NursePOJO nurse = new NursePOJO();
		nurse.setFirstName("ANA");

		assertEquals("ANA", nurse.getFirstName());
	}
	
}
