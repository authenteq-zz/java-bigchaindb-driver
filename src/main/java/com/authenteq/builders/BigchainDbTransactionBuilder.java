package com.authenteq.builders;

import com.authenteq.api.TransactionsApi;
import com.authenteq.model.*;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.KeyPairUtils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.*;
import java.util.Map;
import java.util.TreeMap;

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
	public interface IAssetMetaData {
		
		/**
		 * Adds the asset.
		 *
		 * @param key the key
		 * @param value the value
		 * @return the i asset meta data
		 */
		IAssetMetaData addAsset(String key, String value);

		/**
		 * Adds the meta data.
		 *
		 * @param key the key
		 * @param value the value
		 * @return the i asset meta data
		 */
		IAssetMetaData addMetaData(String key, String value);

		/**
		 * Adds the assets.
		 *
		 * @param assets the assets
		 * @return the i asset meta data
		 */
		IAssetMetaData addAssets(Map<String, String> assets);
		
		/**
		 * Adds the assets.
		 *
		 * @param obj the obj
		 * @return the i asset meta data
		 */
		IAssetMetaData addAssets(DataModel obj);

		/**
		 * Adds the meta data.
		 *
		 * @param metadata the metadata
		 * @return the i asset meta data
		 */
		IAssetMetaData addMetaData(Map<String, String> metadata);
		
		/**
		 * Adds the meta data.
		 *
		 * @param obj the obj
		 * @return the i asset meta data
		 */
		IAssetMetaData addMetaData(DataModel obj);

		/**
		 * Adds the meta data.
		 *
		 * @param jsonObject the json object
		 * @return the i asset meta data
		 */
		IAssetMetaData addMetaData(JsonObject jsonObject);

		/**
		 * Builds the.
		 *
		 * @param publicKey the public key
		 * @return the i build
		 */
		IBuild build(EdDSAPublicKey publicKey);

		/**
		 * Builds the and sign.
		 *
		 * @param publicKey the public key
		 * @param privateKey the private key
		 * @return the i build
		 */
		IBuild buildAndSign(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey);

		/**
		 * Builds the and sign and return.
		 *
		 * @param publicKey the public key
		 * @return the transaction
		 */
		Transaction buildOnly(EdDSAPublicKey publicKey);

		/**
		 * Builds the and sign and return.
		 *
		 * @param publicKey the public key
		 * @param privateKey the private key
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
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		Transaction sendTransaction() throws IOException;

		/**
		 * Send transaction.
		 *
		 * @param callback the callback
		 * @return the transaction
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		Transaction sendTransaction(GenericCallback callback) throws IOException;
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder implements IAssetMetaData, IBuild {

		/** The metadata. */
		private Map<String, String> metadata = new TreeMap<String, String>();
		
		/** The assets. */
		private Map<String, String> assets = new TreeMap<String, String>();
		
		/** The public key. */
		private EdDSAPublicKey publicKey;
		
		/** The transaction. */
		private Transaction transaction;

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addAsset(java.lang.String, java.lang.String)
		 */
		@Override
		public IAssetMetaData addAsset(String key, String value) {
			this.assets.put(key, value);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addMetaData(java.lang.String, java.lang.String)
		 */
		@Override
		public IAssetMetaData addMetaData(String key, String value) {
			this.metadata.put(key, value);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addAssets(java.util.Map)
		 */
		@Override
		public IAssetMetaData addAssets(Map<String, String> assets) {
			this.assets.putAll(assets);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addMetaData(java.util.Map)
		 */
		@Override
		public IAssetMetaData addMetaData(Map<String, String> metadata) {
			this.metadata.putAll(metadata);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addMetaData(com.google.gson.JsonObject)
		 */
		@Override
		public IAssetMetaData addMetaData(JsonObject jsonObject) {
			// TODO Auto-generated method stub
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#build(net.i2p.crypto.eddsa.EdDSAPublicKey)
		 */
		@Override
		public IBuild build(EdDSAPublicKey publicKey) {
			this.transaction = new Transaction();
			
			this.publicKey = publicKey;
			Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition(publicKey);

			Input input = new Input();
			input.setFullFillment(null);
			input.setFulFills(null);
			input.addOwner(KeyPairUtils.encodePublicKeyInBase58(publicKey));

			Output output = new Output();
			output.setAmount("1");
			output.addPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));

			Details details = new Details();
			details.setPublicKey(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			details.setType("ed25519-sha-256");

			output.setCondition(new Condition(details, condition1.getUri().toString()));

			this.transaction.addInput(input);
			this.transaction.addOutput(output);
			this.transaction.setAsset(new Asset(this.assets));
			this.transaction.setMetaData(this.metadata);
			this.transaction.setOperation("CREATE");
			this.transaction.setVersion("1.0");

			//	Workaround to pop out the field.
			JSONObject transactionJObject = DriverUtils.makeSelfSorting(new JSONObject(this.transaction.toString()));
			transactionJObject.remove("id"); // no need before we sign

			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(transactionJObject.toString().getBytes());
			String id = DriverUtils.getHex(md.digest());
			this.transaction.setId(id);
			// we need it after.
			transactionJObject.accumulate("id", id);
			this.transaction = JsonUtils.fromJson(DriverUtils.makeSelfSorting(transactionJObject).toString(), Transaction.class);
			return this;
		}

		/**
		 * Sign.
		 *
		 * @param privateKey the private key
		 * @throws InvalidKeyException the invalid key exception
		 * @throws SignatureException the signature exception
		 * @throws NoSuchAlgorithmException the no such algorithm exception
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

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#buildAndSign(net.i2p.crypto.eddsa.EdDSAPublicKey, net.i2p.crypto.eddsa.EdDSAPrivateKey)
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

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#buildAndSignAndReturn(net.i2p.crypto.eddsa.EdDSAPublicKey)
		 */
		@Override
		public Transaction buildOnly(EdDSAPublicKey publicKey) {
			this.build(publicKey);
			return this.transaction;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#buildAndSignAndReturn(net.i2p.crypto.eddsa.EdDSAPublicKey, net.i2p.crypto.eddsa.EdDSAPrivateKey)
		 */
		@Override
		public Transaction buildAndSignOnly(EdDSAPublicKey publicKey, EdDSAPrivateKey privateKey) {
			this.buildAndSign(publicKey, privateKey);
			return this.transaction;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IBuild#sendTransaction(com.authenteq.model.GenericCallback)
		 */
		@Override
		public Transaction sendTransaction(GenericCallback callback) throws IOException {
			TransactionsApi.sendTransaction(this.transaction, callback);
			return this.transaction;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IBuild#sendTransaction()
		 */
		@Override
		public Transaction sendTransaction() throws IOException {
			TransactionsApi.sendTransaction(this.transaction);
			return this.transaction;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addAssets(com.authenteq.model.DataModel)
		 */
		@Override
		public IAssetMetaData addAssets(DataModel obj) {
			Type mapType = new TypeToken<Map<String, String>>(){}.getType();  
			Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(obj), mapType);
			this.assets.putAll(son);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbTransactionBuilder.IAssetMetaData#addMetaData(com.authenteq.model.DataModel)
		 */
		@Override
		public IAssetMetaData addMetaData(DataModel obj) {
			Type mapType = new TypeToken<Map<String, String>>(){}.getType();  
			Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(obj), mapType);
			this.metadata.putAll(son);
			return this;
		}
	}
}
