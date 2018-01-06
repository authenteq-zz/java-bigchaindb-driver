package com.authenteq.builders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Logger;

import com.authenteq.json.strategy.TransactionDeserializer;
import com.authenteq.json.strategy.TransactionsDeserializer;
import com.google.gson.*;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import com.authenteq.api.TransactionsApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.Asset;
import com.authenteq.model.Condition;
import com.authenteq.model.Details;
import com.authenteq.model.FulFill;
import com.authenteq.model.Input;
import com.authenteq.model.Output;
import com.authenteq.model.Transaction;
import com.authenteq.model.GenericCallback;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.KeyPairUtils;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

/**
 * The Class BigchainDbTransactionBuilder.
 */
public class BigchainDbTransactionBuilder {
	private static final Logger LOGGER = Logger.getLogger( BigchainDbTransactionBuilder.class.getName() );
	
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

		/*
		 * Adds the asset.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @return the i asset meta data
		 */
		//ITransactionAttributes addAsset(String key, String value);

		ITransactionAttributes addAssetDataClass( Class assetDataClass, JsonDeserializer<?> jsonDeserializer );

		ITransactionAttributes addOutput(String amount, EdDSAPublicKey... publicKey);

		ITransactionAttributes addOutput(String amount);

		ITransactionAttributes addOutput(String amount, EdDSAPublicKey publicKey);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey... publicKey);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill);

		ITransactionAttributes addInput(String fullfillment, FulFill fullFill, EdDSAPublicKey publicKey);

		/**
		 * Adds the assets.
		 *
		 * @param assets
		 *            the assets
		 * @return the i asset meta data
		 */
		ITransactionAttributes addAssets( Object assets, Class assetsDataClass );

		/**
		 * Adds the meta data.
		 *
		 * @param metaData
		 *            the json object
		 * @return the i asset meta data
		 */
		ITransactionAttributes addMetaData( Object metaData );

		/**
		 * Add the class and deserializer for metadata
		 *
		 * @param metaDataClass the class of the metadata object
		 * @param jsonDeserializer the deserializer
		 * @return
		 */
		ITransactionAttributes addMetaDataClassDeserializer( Class metaDataClass, JsonDeserializer<?> jsonDeserializer );

		/**
		 * Add the class and serializer for metadata
		 *
		 * @param metaDataClass the class of the metadata object
		 * @param jsonSerializer the deserializer
		 * @return
		 */
		ITransactionAttributes addMetaDataClassSerializer( Class metaDataClass, JsonSerializer<?> jsonSerializer );

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
		private Object metadata = null;

		/** The assets. */
		private Object assets = null;
		private Class assetsDataClass = null;

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
			return addInput( fullfillment, fullFill, this.publicKey );
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

		public ITransactionAttributes addAssetDataClass( Class assetDataClass, JsonDeserializer<?> jsonDeserializer )
		{
			return this;
		}

		/**
		 * Add
		 * @param metaDataClass the class of the metadata object
		 * @param jsonDeserializer the deserializer
		 * @return self
		 */
		@Override
		public ITransactionAttributes addMetaDataClassDeserializer( Class metaDataClass, JsonDeserializer<?> jsonDeserializer )
		{
			TransactionDeserializer.setMetaDataClass( metaDataClass );
			TransactionsDeserializer.setMetaDataClass( metaDataClass );
			JsonUtils.addTypeAdapterDeserializer( metaDataClass, jsonDeserializer );
			return this;
		}

		public ITransactionAttributes addMetaDataClassSerializer( Class metaDataClass, JsonSerializer<?> jsonSerializer )
		{
			JsonUtils.addTypeAdapterSerializer( metaDataClass, jsonSerializer );
			return this;
		}

		public ITransactionAttributes addMetaData( Object object )
		{
			this.metadata = object;
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
			
			if(this.transaction.getOperation() == null) {
				this.transaction.setOperation("CREATE");
			}

			this.transaction.setAsset(new Asset(this.assets, this.assetsDataClass));
			this.transaction.setMetaData(this.metadata);
			this.transaction.setVersion("1.0");

			String temp = this.transaction.toHashInput();
			LOGGER.info( "TO BE HASHED ---->\n" + temp + "\n<" );
			JsonObject transactionJObject = DriverUtils.makeSelfSortingGson( temp );

			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(transactionJObject.toString().getBytes());
			String id = DriverUtils.getHex(md.digest());
			this.transaction.setId(id);

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
			JsonObject transactionJObject = DriverUtils.makeSelfSortingGson(this.transaction.toString());
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

		/**
		 * Add an asset along with the assetDataClass
		 *
		 * @param obj the asset data
		 * @param assetsDataClass the type of the asset data class
		 *
		 * @return self
		 */
		public ITransactionAttributes addAssets( Object obj, Class assetsDataClass )
		{
			this.assets = obj;
			this.assetsDataClass = assetsDataClass;
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
