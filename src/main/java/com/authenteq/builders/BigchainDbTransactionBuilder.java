package com.authenteq.builders;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import org.json.JSONObject;
import com.authenteq.api.TransactionsApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.Asset;
import com.authenteq.model.Condition;
import com.authenteq.model.DataModel;
import com.authenteq.model.Details;
import com.authenteq.model.FulFill;
import com.authenteq.model.Input;
import com.authenteq.model.Output;
import com.authenteq.model.Transaction;
import com.authenteq.model.GenericCallback;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.KeyPairUtils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

/**
 * The Class BigchainDbTransactionBuilder.
 */
public class BigchainDbTransactionBuilder {

	/**
	 * Instantiates a new bigchain db transaction builder.
	 */
	private BigchainDbTransactionBuilder() {
	}

	/**
	 * Inits the.
	 *
	 * @return the builder
	 */
	public static Builder init() {
		return new BigchainDbTransactionBuilder.Builder();
	}

	/**
	 * The Interface IAssetMetaData.
	 */
	public interface ITransactionAttributes {

		/**
		 * Operation.
		 *
		 * @param operation
		 *            the operation
		 * @return the i asset meta data
		 */
		ITransactionAttributes operation(Operations operation);

		/**
		 * Adds the asset.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @return the i asset meta data
		 */
		ITransactionAttributes addAsset(String key, String value);

		ITransactionAttributes addOutput(String amount, EdDSAPublicKey... publicKey);

		ITransactionAttributes addOutput(String amount);

		ITransactionAttributes addOutput(String amount, EdDSAPublicKey publicKey);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey... publicKey);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey publicKey);

		/**
		 * Adds the meta data.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @return the i asset meta data
		 */
		ITransactionAttributes addMetaData(String key, String value);

		/**
		 * Adds the assets.
		 *
		 * @param assets
		 *            the assets
		 * @return the i asset meta data
		 */
		ITransactionAttributes addAssets(Map<String, String> assets);

		/**
		 * Adds the assets.
		 *
		 * @param obj
		 *            the obj
		 * @return the i asset meta data
		 */
		ITransactionAttributes addAssets(DataModel obj);

		/**
		 * Adds the meta data.
		 *
		 * @param metadata
		 *            the metadata
		 * @return the i asset meta data
		 */
		ITransactionAttributes addMetaData(Map<String, String> metadata);

		/**
		 * Adds the meta data.
		 *
		 * @param obj
		 *            the obj
		 * @return the i asset meta data
		 */
		ITransactionAttributes addMetaData(DataModel obj);

		/**
		 * Adds the meta data.
		 *
		 * @param jsonObject
		 *            the json object
		 * @return the i asset meta data
		 */
		ITransactionAttributes addMetaData(JsonObject jsonObject);

		/**
		 * Builds the.
		 *
		 * @param publicKey
		 *            the public key
		 * @return the i build
		 */
		IBuild build(EdDSAPublicKey publicKey);

		/**
		 * Builds the and sign.
		 *
		 * @param publicKey
		 *            the public key
		 * @param privateKey
		 *            the private key
		 * @return the i build
		 */
		IBuild buildAndSign(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey);

		/**
		 * Builds the and sign and return.
		 *
		 * @param publicKey
		 *            the public key
		 * @return the transaction
		 */
		Transaction buildOnly(EdDSAPublicKey publicKey);

		/**
		 * Builds the and sign and return.
		 *
		 * @param publicKey
		 *            the public key
		 * @param privateKey
		 *            the private key
		 * @return the transaction
		 */
		Transaction buildAndSignOnly(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey);
	}

	/**
	 * The Interface IBuild.
	 */
	public interface IBuild {

		/**
		 * Send transaction.
		 *
		 * @return the transaction
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		Transaction sendTransaction() throws IOException;

		/**
		 * Send transaction.
		 *
		 * @param callback
		 *            the callback
		 * @return the transaction
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		Transaction sendTransaction(GenericCallback callback) throws IOException;
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder implements ITransactionAttributes, IBuild {

		/** The metadata. */
		private Map<String, String> metadata = new TreeMap<String, String>();

		/** The assets. */
		private Map<String, String> assets = new TreeMap<String, String>();

		/** The public key. */
		private EdDSAPublicKey publicKey;

		/** The transaction. */
		private Transaction transaction;

		/** The operation. */
		private Operations operation;

		@Override
		public ITransactionAttributes addOutput(String amount) {
			Output output = new Output();
			Ed25519Sha256Condition sha256Condition = new Ed25519Sha256Condition(this.publicKey);
			output.setAmount(amount);
			output.addPublicKey(KeyPairUtils.encodePublicKeyInBase58(this.publicKey));
			Details details = new Details();
			details.setPublicKey(KeyPairUtils.encodePublicKeyInBase58(this.publicKey));
			details.setType("ed25519-sha-256");
			output.setCondition(new Condition(details, sha256Condition.getUri().toString()));
			this.transaction.addOutput(output);
			return this;
		}

		@Override
		public ITransactionAttributes addOutput(String amount, EdDSAPublicKey publicKey) {
			Output output = new Output();
			Ed25519Sha256Condition sha256Condition = new Ed25519Sha256Condition(publicKey);
			output.setAmount(amount);
			output.addPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			Details details = new Details();
			details.setPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			details.setType("ed25519-sha-256");
			output.setCondition(new Condition(details, sha256Condition.getUri().toString()));
			this.transaction.addOutput(output);

			return this;
		}

		@Override
		public ITransactionAttributes addOutput(String amount, EdDSAPublicKey... publicKeys) {
			for (EdDSAPublicKey publicKey : publicKeys) {
				Output output = new Output();
				Ed25519Sha256Condition sha256Condition = new Ed25519Sha256Condition(publicKey);
				output.setAmount(amount);
				output.addPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				Details details = new Details();
				details.setPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				details.setType("ed25519-sha-256");
				output.setCondition(new Condition(details, sha256Condition.getUri().toString()));
				this.transaction.addOutput(output);
			}
			return this;
		}

		@Override
		public ITransactionAttributes addInput(String fullfillment, FulFill fullFill) {
			Input input = new Input();
			input.setFullFillment(fullfillment);
			input.setFulFills(fullFill);
			input.addOwner(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			this.transaction.addInput(input);
			return this;
		}

		@Override
		public ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey publicKey) {
			Input input = new Input();
			input.setFullFillment(fullfillment);
			input.setFulFills(fullFill);
			input.addOwner(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			this.transaction.addInput(input);
			return this;
		}

		@Override
		public ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey... publicKeys) {
			for (EdDSAPublicKey publicKey : publicKeys) {
				Input input = new Input();
				input.setFullFillment(fullfillment);
				input.setFulFills(fullFill);
				input.addOwner(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				this.transaction.addInput(input);
			}
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addAsset(java.lang.String, java.lang.String)
		 */
		@Override
		public ITransactionAttributes addAsset(String key, String value) {
			this.assets.put(key, value);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addMetaData(java.lang.String, java.lang.String)
		 */
		@Override
		public ITransactionAttributes addMetaData(String key, String value) {
			this.metadata.put(key, value);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addAssets(java.util.Map)
		 */
		@Override
		public ITransactionAttributes addAssets(Map<String, String> assets) {
			this.assets.putAll(assets);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addMetaData(java.util.Map)
		 */
		@Override
		public ITransactionAttributes addMetaData(Map<String, String> metadata) {
			this.metadata.putAll(metadata);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addMetaData(com.google.gson.JsonObject)
		 */
		@Override
		public ITransactionAttributes addMetaData(JsonObject jsonObject) {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * build(net.i2p.crypto.eddsa.EdDSAPublicKey)
		 */
		@Override
		public IBuild build(EdDSAPublicKey publicKey) {
			this.transaction = new Transaction();

			this.publicKey = publicKey;
			Ed25519Sha256Condition sha256Condition = new Ed25519Sha256Condition(publicKey);

			if (this.transaction.getOutputs().isEmpty()) {
				Output output = new Output();
				output.setAmount("1");
				output.addPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				Details details = new Details();
				details.setPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				details.setType("ed25519-sha-256");
				output.setCondition(new Condition(details, sha256Condition.getUri().toString()));
				this.transaction.addOutput(output);
			}

			if (this.transaction.getInputs().isEmpty()) {
				Input input = new Input();
				input.setFullFillment(null);
				input.setFulFills(null);
				input.addOwner(KeyPairUtils.encodePublicKeyInBase58(publicKey));
				this.transaction.addInput(input);
			}

			this.transaction.setAsset(new Asset(this.assets));
			this.transaction.setMetaData(this.metadata);
			this.transaction.setOperation(this.operation.toString());
			this.transaction.setVersion("1.0");

			// Workaround to pop out the field.
			JSONObject transactionJObject = DriverUtils.makeSelfSorting(new JSONObject(this.transaction.toString()));
			transactionJObject.remove("id"); // no need before we sign

			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(transactionJObject.toString().getBytes());
			String id = DriverUtils.getHex(md.digest());
			this.transaction.setId(id);
			// we need it after.
			transactionJObject.accumulate("id", id);
			this.transaction = JsonUtils.fromJson(DriverUtils.makeSelfSorting(transactionJObject).toString(),
					Transaction.class);
			return this;
		}

		/**
		 * Sign.
		 *
		 * @param privateKey
		 *            the private key
		 * @throws InvalidKeyException
		 *             the invalid key exception
		 * @throws SignatureException
		 *             the signature exception
		 * @throws NoSuchAlgorithmException
		 *             the no such algorithm exception
		 */
		private void sign(EdDSAPrivateKey privateKey)
				throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {

			// signing the transaction
			JSONObject transactionJObject = DriverUtils.makeSelfSorting(new JSONObject(this.transaction.toString()));
			Signature edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
			edDsaSigner.initSign(privateKey);
			edDsaSigner.update(transactionJObject.toString().getBytes());
			byte[] signature = edDsaSigner.sign();
			Ed25519Sha256Fulfillment fulfillment = new Ed25519Sha256Fulfillment(this.publicKey, signature);
			this.transaction.getInputs().get(0)
					.setFullFillment(Base64.encodeBase64URLSafeString(fulfillment.getEncoded()));
			this.transaction.setSigned(true);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * buildAndSign(net.i2p.crypto.eddsa.EdDSAPublicKey,
		 * net.i2p.crypto.eddsa.EdDSAPrivateKey)
		 */
		@Override
		public IBuild buildAndSign(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey) {
			try {
				this.build(publicKey);
				this.sign(privateKey);
			} catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * buildAndSignAndReturn(net.i2p.crypto.eddsa.EdDSAPublicKey)
		 */
		@Override
		public Transaction buildOnly(EdDSAPublicKey publicKey) {
			this.build(publicKey);
			return this.transaction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * buildAndSignAndReturn(net.i2p.crypto.eddsa.EdDSAPublicKey,
		 * net.i2p.crypto.eddsa.EdDSAPrivateKey)
		 */
		@Override
		public Transaction buildAndSignOnly(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey) {
			this.buildAndSign(publicKey, privateKey);
			return this.transaction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IBuild#
		 * sendTransaction(com.authenteq.model.GenericCallback)
		 */
		@Override
		public Transaction sendTransaction(GenericCallback callback) throws IOException {
			TransactionsApi.sendTransaction(this.transaction, callback);
			return this.transaction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IBuild#
		 * sendTransaction()
		 */
		@Override
		public Transaction sendTransaction() throws IOException {
			TransactionsApi.sendTransaction(this.transaction);
			return this.transaction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addAssets(com.authenteq.model.DataModel)
		 */
		@Override
		public ITransactionAttributes addAssets(DataModel obj) {
			Type mapType = new TypeToken<Map<String, String>>() {
			}.getType();
			Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(obj), mapType);
			this.assets.putAll(son);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * addMetaData(com.authenteq.model.DataModel)
		 */
		@Override
		public ITransactionAttributes addMetaData(DataModel obj) {
			Type mapType = new TypeToken<Map<String, String>>() {
			}.getType();
			Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(obj), mapType);
			this.metadata.putAll(son);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#
		 * operation(com.authenteq.constants.Operations)
		 */
		@Override
		public ITransactionAttributes operation(Operations operation) {
			this.operation = operation;
			return this;
		}
	}
}
