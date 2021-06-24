package com.powerschool.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.powerschool.assignment.rest.census.CensusController;

@SpringBootTest
class AssignmentApplicationTests {

	@Autowired
	private CensusController censusController;

	
	/**
	 * @throws Exception
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(censusController).isNotNull();
	}

}
