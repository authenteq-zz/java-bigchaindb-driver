package com.authenteq.builders;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import com.authenteq.api.TransactionsApi;
import com.authenteq.model.Asset;
import com.authenteq.model.Condition;
import com.authenteq.model.Details;
import com.authenteq.model.Input;
import com.authenteq.model.MetaData;
import com.authenteq.model.Output;
import com.authenteq.model.Transaction;
import com.authenteq.model.TransactionCallback;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.google.gson.JsonObject;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

public class BigchainDbTransactionBuilder {

	private BigchainDbTransactionBuilder() {
	}

	public static Builder init() {
		return new BigchainDbTransactionBuilder.Builder();
	}

	public interface IAssetMetaData {
		IAssetMetaData addAsset(String key, String value);

		IAssetMetaData addMetaData(String key, String value);

		IAssetMetaData addAssets(Map<String, String> assets);

		IAssetMetaData addMetaData(Map<String, String> metadata);

		IAssetMetaData addMetaData(JsonObject jsonObject);

		IBuild build(EdDSAPublicKey publicKey);

		IBuild buildAndSign(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey);
		
		Transaction buildAndSignAndReturn(EdDSAPublicKey publicKey);
		
		Transaction buildAndSignAndReturn(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey);
	}

	public interface IBuild {
		Transaction sendTransaction() throws IOException;
		void sendTransaction(TransactionCallback callback) throws IOException;
	}

	public static class Builder implements IAssetMetaData, IBuild {

		private Map<String, String> metadata = new LinkedHashMap<String, String>();
		private Map<String, String> assets = new LinkedHashMap<String, String>();
		private EdDSAPublicKey publicKey;
		Transaction transaction;


		@Override
		public IAssetMetaData addAsset(String key, String value) {
			this.assets.put(key, value);
			return this;
		}

		@Override
		public IAssetMetaData addMetaData(String key, String value) {
			this.metadata.put(key, value);
			return this;
		}

		@Override
		public IAssetMetaData addAssets(Map<String, String> assets) {
			this.assets.putAll(assets);
			return this;
		}

		@Override
		public IAssetMetaData addMetaData(Map<String, String> metadata) {
			this.metadata.putAll(metadata);
			return this;
		}

		@Override
		public IAssetMetaData addMetaData(JsonObject jsonObject) {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public IBuild build(EdDSAPublicKey publicKey) {

			transaction = new Transaction();
			Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition(publicKey);
			this.publicKey = publicKey;
			transaction.setAsset(new Asset(this.assets));
			transaction.setMetaData(this.metadata);
			transaction.setOperation("CREATE");
			transaction.setVersion("1.0");
			Input input = new Input();
			input.setFullFillment(null);
			input.setFulFills(null);
			input.addOwner(DriverUtils.convertToBase58(publicKey));
			transaction.addInput(input);

			Output output = new Output();
			output.setAmount("1");
			output.addPublicKey(DriverUtils.convertToBase58(publicKey));
			Details details = new Details();
			details.setPublicKey(DriverUtils.convertToBase58(publicKey));
			details.setType("ed25519-sha-256");
			output.setCondition(new Condition(details, condition1.getUri().toString()));
			transaction.addOutput(output);

			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(JsonUtils.toJson(transaction).toString().getBytes());
			transaction.setId(DriverUtils.getHex(md.digest()));

			return this;
		}

		@Override
		public IBuild buildAndSign(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey) {
			this.build(publicKey);
			try {
				this.sign(privateKey, transaction);
			} catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return this;
		}

		private void sign(EdDSAPrivateKey privateKey, Transaction transaction)
				throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
			// signing the transaction
			Signature edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
			edDsaSigner.initSign(privateKey);
			edDsaSigner.update(JsonUtils.toJson(transaction).toString().getBytes());
			byte[] signature = edDsaSigner.sign();
			Ed25519Sha256Fulfillment fulfillment = new Ed25519Sha256Fulfillment(publicKey, signature);
			transaction.getInputs().get(0).setFullFillment(Base64.encodeBase64URLSafeString(fulfillment.getEncoded()));
			transaction.setSigned(true);
		}

		@Override
		public Transaction buildAndSignAndReturn(EdDSAPublicKey publicKey) {
			this.build(publicKey);
			return transaction;
		}

		@Override
		public Transaction buildAndSignAndReturn(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey) {
			this.buildAndSign(publicKey, privateKey);
			return transaction;
		}

		@Override
		public void sendTransaction(TransactionCallback callback) throws IOException {
			TransactionsApi.sendTransaction(transaction, callback);
		}


		@Override
		public Transaction sendTransaction() throws IOException {
			return TransactionsApi.sendTransaction(transaction);
		}
	}
}
