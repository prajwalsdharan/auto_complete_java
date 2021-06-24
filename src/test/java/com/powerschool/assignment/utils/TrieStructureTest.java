package com.powerschool.assignment.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrieStructureTest {
	
	@Test
	public void insertEmptyStringIntoTrie() {

		Boolean result = TrieStructure.insertIntoTrie("");
		assertFalse(result);
		
	}
	
	@Test
	public void insertNullStringIntoTrie() {

		Boolean result = TrieStructure.insertIntoTrie(null);
		assertFalse(result);
		
	}
	
	@Test
	public void insertUpperCaseStringIntoTrie() {

		Boolean result = TrieStructure.insertIntoTrie("PRAJWAL");
		assertTrue(result);
		
	}
	
	@Test
	public void insertLowerCaseStringIntoTrie() {

		Boolean result = TrieStructure.insertIntoTrie("prajwal");
		assertTrue(result);
		
	}
	
	@Test
	public void searchTrieForEmptyString() {

		List<String> resultList = TrieStructure.searchTrieForAutoComplete("", 10);
		assertNull(resultList);
		
	}
	
	@Test
	public void searchTrieForNullString() {

		List<String> resultList = TrieStructure.searchTrieForAutoComplete(null, 10);
		assertNull(resultList);
		
	}

	@Test
	public void searchTrieForUpperCaseString() {

		Boolean result = TrieStructure.insertIntoTrie("prajwal");
		if(result)
		{
			List<String> resultList = TrieStructure.searchTrieForAutoComplete("PRAJWAL", 10);
			assertEquals(1,resultList.size());
		}
		else
			fail("Word could not be inserted due to some reason");
	}
	
	@Test
	public void searchTrieForLowerCaseString() {

		Boolean result = TrieStructure.insertIntoTrie("PRAJWAL");
		if(result)
		{
			List<String> resultList = TrieStructure.searchTrieForAutoComplete("prajwal", 10);
			assertEquals(1,resultList.size());
		}
		else
			fail("Word could not be inserted due to some reason");
	}
	
	@Test
	public void searchTrieAndCheckForRetrivalOrderOfWeights() {

		Boolean result = TrieStructure.insertIntoTrie("praj");
		if(result)
			result = TrieStructure.insertIntoTrie("prajwal");
		else
			fail("Word could not be inserted due to some reason");

		if(result)
			result = TrieStructure.insertIntoTrie("prajwal");
		else
			fail("Word could not be inserted due to some reason");

		if(result)
		{
			List<String> resultList = TrieStructure.searchTrieForAutoComplete("praj", 10);
			assertEquals("prajwal",resultList.get(0));
			assertEquals("praj",resultList.get(1));
		}
		else
			fail("Word could not be inserted due to some reason");
		
	}
	
}
