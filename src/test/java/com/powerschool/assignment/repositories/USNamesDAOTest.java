package com.powerschool.assignment.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.powerschool.assignment.entities.USName;

@SpringBootTest
public class USNamesDAOTest {

	@Autowired
	private USNamesDAO USNamesDAO;

	@Test
	public void saveDuplicateTest() {

		assertThrows(DataIntegrityViolationException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName("rak");
				USNamesDAO.save(user);

			}
			
		});

	}
	
	@Test
	public void saveNullName() {

		assertThrows(DataIntegrityViolationException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName(null);
				USNamesDAO.save(user);

			}
			
		});

	}
	
	@Test
	public void saveEmptyStringName() {

		assertThrows(DataIntegrityViolationException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				USName user = new USName("");
				USNamesDAO.save(user);

			}
			
		});

	}

	@Test
	public void findTop10ByFirstNameStartingWithTest() {

		String partialName = "ra";
		List<USName> resultList = USNamesDAO.findTop10ByFirstNameStartingWith(partialName);
		
		assertEquals(10, resultList.size());

	}
	
	@Test
	public void findByFirstNameTest() {

		String fullName = "rak";
		USName USName = USNamesDAO.findByFirstName(fullName);
		
		assertNotNull(USName);

	}


}
