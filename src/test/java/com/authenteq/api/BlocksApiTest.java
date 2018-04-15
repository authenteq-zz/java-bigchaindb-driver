package com.authenteq.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.TreeMap;

import com.authenteq.AbstractTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.constants.BlockStatus;
import com.authenteq.constants.Operations;
import com.authenteq.model.Account;
import com.authenteq.model.StatusCode;
import com.authenteq.model.Transaction;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

/**
 * The Class BlocksApiTest.
 */
public class BlocksApiTest extends AbstractApiTest
{
	private String publicKey = "302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373";
	private String privateKey = "302e020100300506032b6570042204206f6b0cd095f1e83fc5f08bffb79c7c8a30e77a3ab65f4bc659026b76394fcea8";

	/**
	 * Test asset search.
	 * @throws InterruptedException
	 */
	@Test
	public void testBlockSearch() throws InterruptedException {
		try {
			Map<String, String> metaData = new TreeMap<String, String>() {{ put( "what", "My first BigchainDB transaction" ); }};
			Map<String, String> assetData = new TreeMap<String, String>() {{
				put( "middlename", "mname" );
				put("firstname", "John");
				put( "giddlename", "mname" );
				put( "ziddlename", "mname" );
				put( "lastname", "Smith" );
			}};

			Transaction transaction = BigchainDbTransactionBuilder
                      .init()
                      .addAssets(assetData, TreeMap.class)
                      .operation(Operations.CREATE)
                      .buildAndSign((EdDSAPublicKey) Account.publicKeyFromHex(publicKey), (EdDSAPrivateKey) Account.privateKeyFromHex(privateKey))
                      .sendTransaction();

			assertEquals(StatusCode.VALID, getStatus(transaction).getStatus());
			assertFalse(BlocksApi.getBlocks(transaction.getId(), BlockStatus.VALID).isEmpty());

		} catch (IOException | InvalidKeySpecException | StatusException e) {
			e.printStackTrace();
		}
	}
}
