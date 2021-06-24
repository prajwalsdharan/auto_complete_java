package com.powerschool.assignment.rest.census;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class CensusControllerIT {

	@Test
	public void givenNameIsNull_whenAutoCompleteIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		String name = null;
		HttpUriRequest request = new HttpGet( "http://localhost:8083/powerschool/rest/v1.0/census/autocomplete?partial_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsEmpty_whenAutoCompleteIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		String name = "";
		HttpUriRequest request = new HttpGet( "http://localhost:8083/powerschool/rest/v1.0/census/autocomplete?partial_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsGreaterThan200Chars_whenAutoCompleteIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 201;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String name = buffer.toString();

		HttpUriRequest request = new HttpGet( "http://localhost:8083/powerschool/rest/v1.0/census/autocomplete?partial_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsValid_whenAutoCompleteIsRequested_thenSuccessIsReceived() throws ClientProtocolException, IOException
	{

		String name = "ra";

		HttpUriRequest request = new HttpGet( "http://localhost:8083/powerschool/rest/v1.0/census/autocomplete?partial_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		// Then
		assertEquals(HttpStatus.OK.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsValid_whenAutoCompleteIsRequested_thenSuccessIsReceived_Validating_Content() throws ClientProtocolException, IOException, JSONException
	{

		String name = "ra";

		HttpUriRequest request = new HttpGet( "http://localhost:8083/powerschool/rest/v1.0/census/autocomplete?partial_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
		String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
		JSONArray myRespObj = new JSONArray(jsonResponse);

		// Then
		assertEquals(10,myRespObj.length());
	}

	@Test
	public void givenNameIsNull_whenNewNameEntryIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		String name = null;
		HttpPost httpPost =  new HttpPost( "http://localhost:8083/powerschool/rest/v1.0/census/add?full_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( httpPost );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsEmpty_whenNewNameEntryIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		String name = "";
		HttpPost httpPost =  new HttpPost( "http://localhost:8083/powerschool/rest/v1.0/census/add?full_name=" + name );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( httpPost );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsGreaterThan200Chars_whenNewNameEntryIsRequested_thenBadRequestIsReceived() throws ClientProtocolException, IOException
	{

		// Given
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 201;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String name = buffer.toString();

		HttpPost httpPost = new HttpPost("http://localhost:8083/powerschool/rest/v1.0/census/add?full_name=" + name);

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( httpPost );

		// Then
		assertEquals(HttpStatus.BAD_REQUEST.value(),httpResponse.getStatusLine().getStatusCode());

	}

	@Test
	public void givenNameIsValid_whenNewNameEntryIsRequested_thenSuccessIsReceived_Validating_Content() throws ClientProtocolException, IOException
	{

		// Given
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String name = buffer.toString();

		HttpPost httpPost = new HttpPost("http://localhost:8083/powerschool/rest/v1.0/census/add?full_name=" + name);

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( httpPost );

		String resp = EntityUtils.toString(httpResponse.getEntity());
		
		// Then
		assertEquals("Saved",resp);
	}

}
