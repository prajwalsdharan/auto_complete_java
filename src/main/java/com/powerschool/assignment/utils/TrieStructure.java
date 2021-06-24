package com.powerschool.assignment.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.powerschool.assignment.constants.UtilConstants;

@Component
public class TrieStructure {

	private static final Logger logger = LoggerFactory.getLogger(TrieStructure.class);

	
	private TrieStructure()
	{
		/* No Initialization Allowed */
	}

	private static Integer wordInsertionCounter = 0;
	
	static class Node{
		Node[] kids = new Node[UtilConstants.MAX_WORD_SIZE];
		Boolean isEndOfWord = false;
		String word = null;
		Integer weight = 0;
	}

	static Node root = new Node();
	
	/**
	 * 	Cleans the TRIE and releases memory
	 */
	private synchronized static void makeSpaceInTrie()
	{
		root = new Node();
		System.gc();
	}


	/**
	 * Adds a new word to the TRIE
	 * 
	 * @param word the word to add to the trie
	 * @return the boolean based on the status of insert activity into TRIE
	 */
	public synchronized static Boolean insertIntoTrie(String word)
	{
		try {
			
			if(word==null || word.trim().length()==0)
				return false;
			
			word = word.toLowerCase();
			logger.debug("word inserted:"+wordInsertionCounter+"::"+word);
			
			if(wordInsertionCounter >= 50000)
			{
				makeSpaceInTrie();
				wordInsertionCounter = 0;
			}
			
			Node curr = root;
			int kid = 0;
			boolean newWord = false;
			for(int i=0;i< word.length();i++)
			{
				kid = word.charAt(i)-'a';
				if(curr.kids[kid] == null)
				{
					curr.kids[kid] =  new Node();
					newWord = true;
				}

				curr = curr.kids[kid];
			}
			curr.isEndOfWord = true;	
			curr.word = word;
			curr.weight++;
			
			if(newWord)
				wordInsertionCounter++;
			
		}catch(Exception e)
		{
			logger.error("autocomplete!", e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Searches the TRIE for a partial match of a string and the 
	 * maximum number of words it returns are limited by the count param
	 * 
	 * @param partialWord the partialword to lookup in the trie
	 * @param count maximum count of words to give as a suggestion
	 * @return the list of possible first names
	 */
	public static List<String> searchTrieForAutoComplete(String partialWord, int count)
	{
		if(partialWord==null || partialWord.trim().length()==0)
			return null;
		
		partialWord = partialWord.toLowerCase();
		Node curr = root;
		int kid = 0;

		for(int i=0;i< partialWord.length();i++)
		{
			kid = partialWord.charAt(i)-'a';

			if(curr.kids[kid] != null)
			{
				curr = curr.kids[kid];
			}
			else
				return null;
		}

		// curr is at the end of the prefix, let's continue from there
		Map<Integer,List<String>> wordWeight = new TreeMap<Integer,List<String>>(Collections.reverseOrder());
		
		Deque<Node> DQ = new ArrayDeque<Node>();
		DQ.addLast(curr);
		int countOfWords = 0;
		while (!DQ.isEmpty()) 
		{
			Node first = DQ.removeFirst();
			
			if(countOfWords==count)
				break;
				
			if(first.isEndOfWord){
				if(wordWeight.containsKey(first.weight))
				{
					List<String> modifiedList = wordWeight.get(first.weight);
					modifiedList.add(first.word);
					wordWeight.put(first.weight,modifiedList);
				}
				else
				{
					List<String> modifiedList = new ArrayList<String>();
					modifiedList.add(first.word);
					wordWeight.put(first.weight,modifiedList);
				}
				countOfWords++;
			}

			for(Node n : first.kids){
				if(n != null){
					DQ.add(n);
				}
			}
		}
		
		logger.debug(wordWeight.toString());
			
		return wordWeight.values().stream().flatMap(map -> map.stream()).collect(Collectors.toList());
	}

}
