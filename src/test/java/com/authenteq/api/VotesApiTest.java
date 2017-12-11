package com.authenteq.api;

import org.junit.Test;

import java.io.IOException;


/**
 * The Class VotesApiTest.
 */
public class VotesApiTest extends AbstractApiTest
{

	/**
	 * Test asset search.
	 */
	@Test
	public void testAssetSearch() {
		try {
			VotesApi.getVotes("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
