package com.authenteq.api;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.model.Account;
import com.authenteq.model.Output;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import net.i2p.crypto.eddsa.EdDSAPublicKey;


/**
 * The Class OutputsApiTest.
 */
public class OutputsApiTest {

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io").addToken("app_id", "2bbaf3ff")
				.addToken("app_key", "c929b708177dcc8b9d58180082029b8d").setup();
	}
	
	/**
	 * Test.
	 * @throws InvalidKeySpecException 
	 */
	@Test
	public void test() throws InvalidKeySpecException {
		try {

			String pubKey = DriverUtils.convertToBase58((EdDSAPublicKey)Account.publicKeyFromHex("302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373"));
			Iterator<Output> outputIter = OutputsApi.getOutputs(pubKey).getOutput().iterator();
			
			while(outputIter.hasNext()) {
				System.out.println(JsonUtils.toJson(TransactionsApi.getTransactionById(outputIter.next().getTransactionId()).getAsset().getData()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
