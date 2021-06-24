package com.powerschool.assignment.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powerschool.assignment.entities.USName;
import com.powerschool.assignment.repositories.USNamesDAO;

@Service
public class USNamesService {

	private static final Logger logger = LoggerFactory.getLogger(USNamesService.class);

	@Autowired
	USNamesDAO USNamesDao;
	
	/**
	 * @param USName the firstname pojo
	 * @return the first_name
	 */
	@Transactional
	public USName addFirstName(USName USName)
	{
		try {
			return USNamesDao.save(USName);
		}catch(Exception e)
		{
			logger.error("addition!", e);
			return null;
		}
	}
	
	
	/**
	 * @param partialName  the partialname given as input by the user
	 * @return list of first_names
	 */
	public List<USName> getMatchingFirstNames(String partialName)
	{
		try {
			return USNamesDao.findTop10ByFirstNameStartingWith(partialName);
		}catch(Exception e)
		{
			logger.error("autocomplete!", e);
			return null;
		}
	}
	
	/**
	 * @param fullName the fullname given by the user
	 * @return a boolean true if name exists
	 */
	public Boolean findIfNameExists(String fullName)
	{
		try {
			USName name = USNamesDao.findByFirstName(fullName);
			logger.debug("already exists::"+name);
			if(name != null)
				return true;
			else
				return false;
		}catch(Exception e)
		{
			logger.error("findIfExists!", e);
			return null;
		}
	}
}
