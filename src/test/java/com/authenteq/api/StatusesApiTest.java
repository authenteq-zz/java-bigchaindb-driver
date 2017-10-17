package com.authenteq.api;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.model.Status;


/**
 * The Class StatusesApiTest.
 */
public class StatusesApiTest {

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io").addToken("app_id", "2bbaf3ff")
				.addToken("app_key", "c929b708177dcc8b9d58180082029b8d").setup();
	}
	
	/**
	 * Test status transaction.
	 */
	@Test
	public void testStatusTransaction() {
		try {
			System.out.println(StatusesApi.getTransactionStatus("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a").getStatus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
