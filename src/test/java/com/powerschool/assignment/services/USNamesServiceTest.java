package com.powerschool.assignment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import com.powerschool.assignment.entities.USName;

@SpringBootTest
public class USNamesServiceTest {

	@Autowired
	USNamesService USNamesService;

	@Test
	public void saveDuplicateTest() {

		assertThrows(UnexpectedRollbackException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName("rak");
				USNamesService.addFirstName(user);

			}

		});

	}

	@Test
	public void saveNullName() {
		
		assertThrows(UnexpectedRollbackException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName(null);
				USNamesService.addFirstName(user);

			}

		});

	}

	@Test
	public void saveEmptyStringName() {

		assertThrows(UnexpectedRollbackException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName("");
				USNamesService.addFirstName(user);

			}

		});

	}
	
	@Test
	public void findTop10ByFirstNameStartingWithTest() {

		String partialName = "ra";
		List<USName> resultList = USNamesService.getMatchingFirstNames(partialName);
		assertEquals(10, resultList.size());

	}
	
	@Test
	public void findByFirstNameTest() {

		String fullName = "rak";
		Boolean found = USNamesService.findIfNameExists(fullName);
		assertTrue(found);

	}

}
