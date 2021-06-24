package com.powerschool.assignment.wrappers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.powerschool.assignment.entities.USName;
import com.powerschool.assignment.services.USNamesService;
import com.powerschool.assignment.utils.TrieStructure;

@Component
public class AutoCompleteWrapper {

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteWrapper.class);

	@Autowired
	USNamesService USNamesService;

	/**
	 * @param partialName the partial_name to lookup in the trie or the database
	 * @return the list of first names
	 */
	public List<String> getListOfNameSuggestions(String partialName)
	{
		try {
			
			List<String> suggestionList = TrieStructure.searchTrieForAutoComplete(partialName, 10);
			logger.debug("Got it from trie:"+suggestionList);
			Set<String> uniqueNames = null;

			if(suggestionList==null || suggestionList.size() < 10)
			{
				if(suggestionList==null)
					suggestionList = new ArrayList<String>();

				List<USName> USNames = USNamesService.getMatchingFirstNames(partialName);
				uniqueNames = new LinkedHashSet<String>(suggestionList);

				logger.debug("unique names from trie:"+uniqueNames);
				if(USNames!=null)
				{
					for(USName name:USNames)
					{
						if(uniqueNames.size()==10)
							break;

						uniqueNames.add(name.getFirstName().toLowerCase());
						TrieStructure.insertIntoTrie(name.getFirstName().toLowerCase());
					}
				}
			}

			if(uniqueNames!=null)
				return new ArrayList<String>(uniqueNames);
			else
				return suggestionList;
			
		}catch(Exception e)
		{
			logger.error("AutoComplete name suggestions:",e);
			return null;
		}
	}

	
	/**
	 * @param USName the full_name to store in the database and the trie
	 * @return the boolean reflecting the status of addition of new name to the database
	 */
	public Boolean addFirstName(USName USName)
	{
		try {
			
			Boolean exists = USNamesService.findIfNameExists(USName.getFirstName());
			if(exists!=null)
			{
				if(!exists)
				{
					USNamesService.addFirstName(USName);
					return true;
				}
				
				TrieStructure.insertIntoTrie(USName.getFirstName());				
			}
			
			return false;
		}catch(Exception e)
		{
			logger.error("Add name suggestions:",e);
			return false;
		}
	}

}
