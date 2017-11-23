package com.authenteq.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;

import com.authenteq.AbstractTest;
import org.junit.BeforeClass;
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
public class BlocksApiTest extends AbstractTest
{
	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		BigchainDbConfigBuilder
			.baseUrl( get( "test.api.url", "https://test.ipdb.io" ) )
			.addToken("app_id", "2bbaf3ff")
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
