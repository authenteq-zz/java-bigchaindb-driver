package com.authenteq.api;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;

import com.authenteq.AbstractTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.model.Account;
import com.authenteq.model.Output;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.KeyPairUtils;

import net.i2p.crypto.eddsa.EdDSAPublicKey;


/**
 * The Class OutputsApiTest.
 */
public class OutputsApiTest extends AbstractApiTest
{

	/**
	 * Test.
	 */
	@Test
	public void testOutput() throws InvalidKeySpecException {
		try {

			String pubKey = KeyPairUtils.encodePublicKeyInBase58((EdDSAPublicKey)Account.publicKeyFromHex("302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373"));
			Iterator<Output> outputIter = OutputsApi.getOutputs(pubKey).getOutput().iterator();
			
			while(outputIter.hasNext()) {
				JsonUtils.toJson(TransactionsApi.getTransactionById(outputIter.next().getTransactionId()).getAsset().getData());
			}
			
			assertTrue(OutputsApi.getOutputs(pubKey).getOutput().size() > 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
