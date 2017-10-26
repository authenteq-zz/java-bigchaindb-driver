package com.authenteq.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;

import org.junit.Before;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.constants.BlockStatus;
import com.authenteq.constants.Operations;
import com.authenteq.model.Account;
import com.authenteq.model.Transaction;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

/**
 * The Class BlocksApiTest.
 */
public class BlocksApiTest {

	private String publicKey = "302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373";
	private String privateKey = "302e020100300506032b6570042204206f6b0cd095f1e83fc5f08bffb79c7c8a30e77a3ab65f4bc659026b76394fcea8";

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
	 * @throws InterruptedException 
	 */
	@Test
	public void testBlockSearch() throws InterruptedException {
		try {
			
			Transaction transaction = BigchainDbTransactionBuilder.init().addAsset("middlename", "mname")
					.addAsset("firstname", "John").addAsset("giddlename", "mname").addAsset("ziddlename", "mname")
					.addAsset("lastname", "Smith").addMetaData("what", "My first BigchainDB transaction")
					.operation(Operations.CREATE).buildAndSign((EdDSAPublicKey) Account.publicKeyFromHex(publicKey),
							(EdDSAPrivateKey) Account.privateKeyFromHex(privateKey))
					.sendTransaction();
			
			assertFalse(BlocksApi.getBlocks(transaction.getId(), BlockStatus.VALID).isEmpty());
			
		} catch (IOException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
