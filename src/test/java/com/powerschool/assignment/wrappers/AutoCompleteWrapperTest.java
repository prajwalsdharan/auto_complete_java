package com.powerschool.assignment.wrappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.powerschool.assignment.entities.USName;

@SpringBootTest
public class AutoCompleteWrapperTest {

	@Autowired
	AutoCompleteWrapper autoCompleteWrapper;

	@Test
	public void saveDuplicateTest() {

		USName user = new USName("rak");
		Boolean status = autoCompleteWrapper.addFirstName(user);
		assertFalse(status);

	}

	@Test
	public void saveNullName() {

		USName user = new USName(null);
		Boolean status = autoCompleteWrapper.addFirstName(user);
		assertFalse(status);
		
	}

	@Test
	public void saveEmptyStringName() {

		USName user = new USName("");
		Boolean status = autoCompleteWrapper.addFirstName(user);
		assertFalse(status);

	}

	@Test
	public void findTop10ByFirstNameStartingWithString() {

		String partialName = "ra";
		List<String> resultList = autoCompleteWrapper.getListOfNameSuggestions(partialName);
		assertEquals(10, resultList.size());

	}
	
}
