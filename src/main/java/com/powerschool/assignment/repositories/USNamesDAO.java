package com.powerschool.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.powerschool.assignment.entities.USName;

public interface USNamesDAO extends JpaRepository<USName,Integer>{

	
	/**
	 * @param firstName The partialname to search in the databse
	 * @return list of first_names
	 */
	public List<USName> findTop10ByFirstNameStartingWith(String firstName);
	
	
	/**
	 * @param fullName The fullname to store in the database
	 * @return a first_name if there is a match in DB, null otherwise
	 */
	public USName findByFirstName(String fullName);	
	
}
