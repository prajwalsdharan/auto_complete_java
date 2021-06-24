package com.powerschool.assignment.rest.census;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerschool.assignment.constants.URIConstants;
import com.powerschool.assignment.entities.USName;
import com.powerschool.assignment.wrappers.AutoCompleteWrapper;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(URIConstants.URI_HOST+URIConstants.URI_CENSUS_SUFFIX)
public class CensusController {

	@Autowired
	AutoCompleteWrapper autoCompleteWrapper;

	/**
	 * The end point which takes in a partial string of firstName and gives the 
	 * possible names sorted based on their weights ( frequency )
	 * 
	 * @param partialName The partial_name to search for autocomplete suggestions
	 * @return list of first_names
	 */
	@RequestMapping(value = URIConstants.URI_CENSUS_AUTOCOMPLETE_SUFFIX, method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<String>> autoCompleteRequest(@RequestParam("partial_name") String partialName) {

		if(partialName==null || partialName.equals("null") || partialName.trim().length()==0 || partialName.trim().length()>200)
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(null);


		List<String> matchingNames = autoCompleteWrapper.getListOfNameSuggestions(partialName.toLowerCase());

		if(matchingNames!=null)
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(matchingNames);
		else
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(matchingNames);

	}

	/**
	 * The end point which takes in a complete firstName and puts it onto the database if it does not exists, 
	 * however TRIE will be updated where it would have an impact on the corresponding weight of the word
	 * 
	 * @param fullName The fullname to store in the database and the trie
	 * @return a string representing the boolean of write status into the DB and the TRIE
	 */
	@RequestMapping(value = URIConstants.URI_CENSUS_NEW_NAME_SUFFIX, method = RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> newNameRequest(@RequestParam("full_name") String fullName) {

		if(fullName==null || fullName.equals("null") || fullName.trim().length()==0||fullName.trim().length()>200)
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(null);

		USName newName = new USName(fullName.toLowerCase());
		Boolean nameSaved = autoCompleteWrapper.addFirstName(newName);

		if(nameSaved!=null && nameSaved)
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Saved");
		else
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Server Error");


	}


}
