package com.authenteq.transaction;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import org.json.JSONObject;
import org.junit.Test;

import com.authenteq.api.TransactionsApi;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.model.Asset;
import com.authenteq.model.Condition;
import com.authenteq.model.Details;
import com.authenteq.model.Input;
import com.authenteq.model.MetaData;
import com.authenteq.model.Output;
import com.authenteq.model.Transaction;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;

import junit.framework.Assert;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

public class TransactionHashTest {

	Transaction transaction;

	@Test
	public void testBeforeAfterHash() throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io").addToken("app_id", "2bbaf3ff")
		.addToken("app_key", "c929b708177dcc8b9d58180082029b8d").setup();
		
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();

		this.transaction = new Transaction();
		Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition((EdDSAPublicKey) keyPair.getPublic());

		Input input = new Input();
		input.setFullFillment(null);
		input.setFulFills(null);
		input.addOwner(DriverUtils.convertToBase58((EdDSAPublicKey) keyPair.getPublic()));
		this.transaction.addInput(input);

		Output output = new Output();
		output.setAmount("1");
		output.addPublicKey(DriverUtils.convertToBase58((EdDSAPublicKey) keyPair.getPublic()));
		Details details = new Details();
		details.setPublicKey(DriverUtils.convertToBase58((EdDSAPublicKey) keyPair.getPublic()));
		details.setType("ed25519-sha-256");
		output.setCondition(new Condition(details, condition1.getUri().toString()));
		this.transaction.addOutput(output);
		this.transaction.setAsset(new Asset().addAsset("hello", "world"));
		this.transaction.addMetaData("meta", "data");
		this.transaction.setOperation("CREATE");
		this.transaction.setVersion("1.0");

		Transaction sortedTransactionBeforeSign = JsonUtils.fromJson(
				DriverUtils.makeSelfSorting(new JSONObject(JsonUtils.toJson(this.transaction))).toString(),
				Transaction.class);
		// sha3.sha3_256(data.encode()).hexdigest()

		// before signature
		SHA3.DigestSHA3 md1 = new SHA3.DigestSHA3(256);
		md1.update(sortedTransactionBeforeSign.toString().getBytes());
		String id1 = DriverUtils.getHex(md1.digest());
		sortedTransactionBeforeSign.setId(id1);
		System.out.println("b4 " + id1);

		// sign it
		Signature edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
		edDsaSigner.initSign(keyPair.getPrivate());
		edDsaSigner.update(sortedTransactionBeforeSign.toString().getBytes());
		byte[] signature = edDsaSigner.sign();
		Ed25519Sha256Fulfillment fulfillment = new Ed25519Sha256Fulfillment((EdDSAPublicKey) keyPair.getPublic(),
				signature);
		sortedTransactionBeforeSign.getInputs().get(0)
				.setFullFillment(Base64.encodeBase64URLSafeString(fulfillment.getEncoded()));

		SHA3.DigestSHA3 md2 = new SHA3.DigestSHA3(256);
		md2.update(sortedTransactionBeforeSign.toString().getBytes());
		String id2 = DriverUtils.getHex(md2.digest());

		// with signature
		System.out.println("with sign " + id2);

		// remove signature
		sortedTransactionBeforeSign.getInputs().get(0).setFullFillment(null);
		SHA3.DigestSHA3 md3 = new SHA3.DigestSHA3(256);
		md3.update(sortedTransactionBeforeSign.toString().getBytes());
		String id3 = DriverUtils.getHex(md3.digest());
		System.out.println("without signature " + id3);
		
		try {
			TransactionsApi.sendTransaction(sortedTransactionBeforeSign);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		org.junit.Assert.assertEquals("Signatures with and without are not equal",id1, id3);

	}
	
}
