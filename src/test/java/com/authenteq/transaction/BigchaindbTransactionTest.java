package com.authenteq.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
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

import net.i2p.crypto.eddsa.EdDSAEngine;
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
					.addAsset("middlename", "mname")
					.addAsset("firstname", "John")
					.addAsset("giddlename", "mname")
					.addAsset("ziddlename", "mname")
					.addAsset("lastname", "Smith")
					.addMetaData("what", "My first BigchainDB transaction")
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction();

			assertNotNull(transaction.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPostTransaction() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();

		// Payload of the transaction, defined as the asset to store
		JSONObject data = new JSONObject();
		data.put("middlename", "mname");
		data.put("firstname", "John");
		data.put("giddlename", "mname");
		data.put("ziddlename", "mname");
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

		try {
			System.out.println(bigchaindbTransaction.getTransactionJson().toString());
			Transaction tran = TransactionsApi.sendTransaction(
					JsonUtils.fromJson(bigchaindbTransaction.getTransactionJson().toString(), Transaction.class));
			System.out.println(tran.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPostTransactionComplex() {
		JSONObject data = new JSONObject();
		data.put("expiration", "NLpPB8MpOkQLZJuyn4rXacdQBXOt4OAwQUSAEpipi2Y=\\n");
		data.put("lat", "DP\\/p9q4D7L0IBZr53Dh98N1huD5BGG\\/nZ9zs\\/ydEUzc=\\n");
		data.put("lon", "ZPleIXiR3W4RWzjrdXcqXDYUjPGGQn6JKmMF5OH7T6U=\\n");
		data.put("firstname", "NLpPB8MpOkQLZJuyn4rXacdQBXOt4OAwQUSAEpipi2Y=\\n");
		data.put("lastname", "N4iDURp+thKsn1Mn7csSoU63QJnJxqyz+VNOPUikMMk=\\n");
		data.put("dob", "ZBJhOnJgC\\/E\\/iD2eyh15qWqD3jsyj+k9+2XIDJXvhEE=\\n");
		data.put("sex", "lg52\\/gnwsTWdwUW4teyj4SGQOF7y5C435on9HtW3DwI=\\n");
		data.put("nationality", "MjpcaVsVoR+5E5ov4Z2gAal1cUfYLUypL52nFqx7pyM=\\n");
		data.put("idNumber", "hsDKi81fiXWuNHoZrzezzTMHykjDIrAtiPozzPTkkbM=\\n");

		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();

		TransactionModel bigchaindbTransaction = new TransactionModel(data, null, (EdDSAPublicKey) keyPair.getPublic());
		assertFalse(bigchaindbTransaction.isSigned());
		try {
			bigchaindbTransaction.signTransaction((EdDSAPrivateKey) keyPair.getPrivate());
		} catch (InvalidKeyException | SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition((EdDSAPublicKey) keyPair.getPublic());
		Signature edDsaSigner = null;
		try {
			edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject rootObject = new JSONObject(bigchaindbTransaction.getTransactionJson().toString());
		String fulfilmentVal = rootObject.getJSONArray("inputs").getJSONObject(0).getString("fulfillment");
		rootObject.getJSONArray("inputs").getJSONObject(0).put("fulfillment", JSONObject.NULL);
		
		System.out.println(rootObject.toString());
		try {
			edDsaSigner.initSign(keyPair.getPrivate());
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			edDsaSigner.update(rootObject.toString().getBytes());
		} catch (SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] signature;
		Ed25519Sha256Fulfillment fulfillment;
		try {
			signature = edDsaSigner.sign();
			fulfillment = new Ed25519Sha256Fulfillment((EdDSAPublicKey) keyPair.getPublic(),
						signature);
		} catch (SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {
			Transaction tran = TransactionsApi
					.sendTransaction(JsonUtils.fromJson(bigchaindbTransaction.getTransactionJson().toString(), Transaction.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPostTransactionUsingBuilderWithCallBack() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			BigchainDbTransactionBuilder.init().addAsset("firstname", "alvin").addMetaData("what", "bigchaintrans")
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction(new TransactionCallback() {

						@Override
						public void transactionMalformed(Response response) {
							//System.out.println(response.message());
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
			System.out.println(TransactionsApi
					.getTransactionsByAssetId("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a",
							Operations.CREATE)
					.get(0).getTransactionId());
			assertTrue(TransactionsApi.getTransactionsByAssetId(
					"437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a", Operations.CREATE).size() > 0);
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
			assertTrue(TransactionsApi.getTransactionsByAssetId(
					"437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a", Operations.CREATE).size() > 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
