package com.authenteq.api;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;


/**
 * The Class AssetsApiTest.
 */
public class AssetsApiTest {

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io").addToken("app_id", "2bbaf3ff")
				.addToken("app_key", "c929b708177dcc8b9d58180082029b8d").setup();
	}
	
	/**
	 * Test asset search.
	 */
	@Test
	public void testAssetSearch() {
		try {
			System.out.println(AssetsApi.getAssets("John").getAssets().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
