package com.authenteq.transaction;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SignatureException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.authenteq.api.TransactionsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.constants.Operations;
import com.authenteq.model.TransactionModel;
import com.authenteq.util.JsonUtils;
import com.authenteq.model.Transaction;
import com.authenteq.model.TransactionCallback;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

/**
 * The Class BigchaindbTransactionTest.
 */
public class BigchaindbTransactionTest {
	
	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io").addToken("app_id", "2bbaf3ff")
		.addToken("app_key", "c929b708177dcc8b9d58180082029b8d").setup();
	}

	@Test
	public void testPostTransactionUsingBuilder() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			Transaction transaction = BigchainDbTransactionBuilder.init()
				.addAsset("firstname", "alvin").addMetaData("what", "bigchaintrans")
				.buildAndSign((EdDSAPublicKey) keyPair.getPublic(),(EdDSAPrivateKey) keyPair.getPrivate())
				.sendTransaction();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPostTransaction() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();

		// Payload of the transaction, defined as the asset to store
		JSONObject data = new JSONObject();
		data.put("firstname", "John");
		data.put("lastname", "Smith");

		// Metadata contains information about the transaction itself
		// (can be `null` if not needed)
		JSONObject metadata = new JSONObject();
		metadata.put("what", "My first BigchainDB transaction");

		TransactionModel bigchaindbTransaction = new TransactionModel(data, metadata,
				(EdDSAPublicKey) keyPair.getPublic());
		try {
			bigchaindbTransaction.signTransaction((EdDSAPrivateKey) keyPair.getPrivate());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testPostTransactionUsingBuilderWithCallBack() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			BigchainDbTransactionBuilder.init()
				.addAsset("firstname", "alvin").addMetaData("what", "bigchaintrans")
				.buildAndSign((EdDSAPublicKey) keyPair.getPublic(),(EdDSAPrivateKey) keyPair.getPrivate())
				.sendTransaction(new TransactionCallback() {
					
					@Override
					public void transactionMalformed(Response response) {
						System.out.println(response.message());
						System.out.println("malformed " + response.message());
						
					}
					
					@Override
					public void pushedSuccessfully(Response response) {
						System.out.println("pushedSuccessfully");
						
					}
					
					@Override
					public void otherError(Response response) {
						System.out.println("otherError");
						
					}
				});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test transaction by asset id create.
	 */
	@Test
	public void testTransactionByAssetIdCreate() {
		try {
			assertTrue(TransactionsApi.getTransactionsByAssetId("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a", Operations.CREATE).size() > 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test transaction by asset id transfer.
	 */
	@Test
	public void testTransactionByAssetIdTransfer() {
		try {
			assertTrue(TransactionsApi.getTransactionsByAssetId("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a", Operations.CREATE).size() > 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
