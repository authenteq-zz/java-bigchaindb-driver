package com.authenteq.api;

import java.io.IOException;
import java.security.KeyPair;

import com.authenteq.AbstractTest;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.model.Assets;
import com.authenteq.model.Transaction;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.junit.BeforeClass;
import org.junit.Test;

import com.authenteq.api.AssetsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The Class AssetsApiTest.
 */
public class AssetsApiTest extends AbstractTest
{

	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		BigchainDbConfigBuilder
			.baseUrl( get( "test.api.url", "https://test.ipdb.io" ))
			.addToken("app_id", "2bbaf3ff")
			.addToken("app_key", "c929b708177dcc8b9d58180082029b8d")
			.setup();
	}
	
	/**
	 * Test asset search.
	 */
	@Test
	public void testAssetSearch() {
		String uuid = getUUID();
		System.err.println( "AssetApiTest.testAssetSearch.uuid " + uuid );
		try {
			// create transaction with unique asset
			net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
			KeyPair alice = edDsaKpg.generateKeyPair();

			Transaction transaction = BigchainDbTransactionBuilder
				                          .init()
				                          .addAsset( "uuid", uuid )
				                          .buildAndSign( (EdDSAPublicKey) alice.getPublic(), (EdDSAPrivateKey) alice.getPrivate() )
				                          .sendTransaction();
			assertEquals( "valid", getStatus( transaction ).toString());    // wait for the transaction to be marked valid

			Assets assets = AssetsApi.getAssets( asQuoted( uuid ) );
			assertTrue( assets.size() == 1 ); // there should be one and only one
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
