/*
 * (C) Copyright 2017 Authenteq (https://authenteq.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Bohdan Bezpartochnyi <bohdan@authenteq.com>
 */

package com.authenteq.model;

import com.authenteq.util.Base58;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.KeyPairUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;

import java.security.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Transaction.
 */
public class TransactionModel {
	
	/** The public key. */
	private EdDSAPublicKey publicKey;
	
	/** The data. */
	private JsonObject data;
	
	/** The metadata. */
	private JsonObject metadata;
	
	/** The transaction json. */
	private JsonObject transactionJson;
	
	/** The signed. */
	private boolean signed;

	/**
	 * Create a BigchainDB transaction with specified data and metadata.
	 *
	 * @param data            Payload of the transaction, defined as the asset to store
	 * @param metadata            Metadata contains information about the transaction itself
	 *            (can be `null` if not needed)
	 * @param publicKey the public key
	 */
	public TransactionModel(JsonObject data, JsonObject metadata, EdDSAPublicKey publicKey) {
		this.publicKey = publicKey;
		this.data = DriverUtils.makeSelfSortingGson(data);
		this.metadata = DriverUtils.makeSelfSortingGson(metadata);
		buildTransactionJson();
	}

	/**
	 * Instantiates a new transaction.
	 *
	 * @param data the data
	 * @param metadata the metadata
	 * @param transactionJson the transaction json
	 * @param publicKey the public key
	 * @param signed the signed
	 */
	private TransactionModel( JsonObject data, JsonObject metadata, JsonObject transactionJson,
	                          EdDSAPublicKey publicKey, boolean signed) {
		this.publicKey = publicKey;
		this.data = DriverUtils.makeSelfSortingGson(data);
		this.metadata = DriverUtils.makeSelfSortingGson(metadata);
		this.transactionJson = DriverUtils.makeSelfSortingGson(transactionJson);
		this.signed = signed;
	}

	/**
	 * Builds the transaction JSON without actually signing it.
	 */
	protected void buildTransactionJson() {
		JsonObject asset = DriverUtils.getSelfSortingJson();
		JsonObject outputs = DriverUtils.getSelfSortingJson();
		JsonObject inputs = DriverUtils.getSelfSortingJson();
		JsonObject condition = DriverUtils.getSelfSortingJson();
		JsonObject details = DriverUtils.getSelfSortingJson();
		JsonArray inputsArr = new JsonArray();
		JsonArray outputsArr = new JsonArray();

		Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition(publicKey);

		JsonObject rootObject = DriverUtils.getSelfSortingJson();
		try {
			if (metadata == null) {
				rootObject.add("metadata", null);
			} else {
				rootObject.add("metadata", metadata);
			}

			rootObject.addProperty("operation", "CREATE");
			rootObject.addProperty("version", "1.0");
			asset.add("data", data);
			rootObject.add("asset", asset);

			outputs.addProperty("amount", "1");
			JsonArray publicKeys = new JsonArray();
			publicKeys.add(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			outputs.add("public_keys", publicKeys);
			outputsArr.add(outputs);
			rootObject.add("outputs", outputsArr);

			condition.addProperty("uri", condition1.getUri().toString());

			details.addProperty("public_key", KeyPairUtils.encodePublicKeyInBase58(publicKey));
			details.addProperty("type", "ed25519-sha-256");
			condition.add("details", details);
			outputs.add("condition", condition);

			inputs.add("fulfillment", null);
			inputs.add("fulfills", null);
			JsonArray ownersBefore = new JsonArray();
			ownersBefore.add(KeyPairUtils.encodePublicKeyInBase58(publicKey));
			inputs.add("owners_before", ownersBefore);
			inputsArr.add(inputs);
			rootObject.add("inputs", inputsArr);

			// getting SHA3 hash of the current JSON object
			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(rootObject.toString().getBytes());
			String id = DriverUtils.getHex(md.digest());

			// putting the hash as id field
			rootObject.addProperty("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		transactionJson = rootObject;
		System.out.println(transactionJson.toString());
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return TransactionID which is SHA3 hash of the transaction JSON without
	 *         fulfillment
	 */
	public String getTransactionId() {
		return transactionJson.get("id").getAsString();
	}

	/**
	 * Sign the transaction with the specified private key.
	 *
	 * @param privateKey the private key
	 * @throws InvalidKeyException the invalid key exception
	 * @throws SignatureException the signature exception
	 */
	public void signTransaction(EdDSAPrivateKey privateKey) throws InvalidKeyException, SignatureException {
		try {
			// signing the transaction
			Signature edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
			edDsaSigner.initSign(privateKey);
			edDsaSigner.update(transactionJson.toString().getBytes());
			byte[] signature = edDsaSigner.sign();
			Ed25519Sha256Fulfillment fulfillment = new Ed25519Sha256Fulfillment(publicKey, signature);
			JsonObject inputs = transactionJson.get("inputs").getAsJsonArray().get( 0 ).getAsJsonObject();//   getJsonArray("inputs").getJSONObject(0);
			inputs.addProperty("fulfillment", Base64.encodeBase64URLSafeString(fulfillment.getEncoded()));
			signed = true;
		} catch ( NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if is signed.
	 *
	 * @return Whether the transaction is successfully signed
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * Gets the transaction json.
	 *
	 * @return The JSON representation of the transaction
	 */
	public JsonObject getTransactionJson() {
		return transactionJson;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (transactionJson == null) {
			return "";
		} else {
			return transactionJson.toString();
		}
	}

	/**
	 * Gets the public key.
	 *
	 * @return the public key
	 */
	public EdDSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public JsonObject getData() {
		return data;
	}

	/**
	 * Gets the metadata.
	 *
	 * @return the metadata
	 */
	public JsonObject getMetadata() {
		return metadata;
	}

	/**
	 * Parse the JSON representation of the transaction and return the
	 * BigchaindbTransaction object. The validation of signatures is not
	 * performed.
	 *
	 * @param jsonObject the json object
	 * @return the transaction
	 */
	public static TransactionModel createFromJson(JsonObject jsonObject) {
		JsonObject data = jsonObject.get("asset").getAsJsonObject().get("data").getAsJsonObject();
		JsonObject metadata = jsonObject.get("metadata").getAsJsonObject();
		String publicKeyEncoded = jsonObject.get("outputs").getAsJsonArray().get(0).getAsJsonObject().get("public_keys").getAsJsonArray().get(0).getAsString();
		byte[] publicKey = Base58.decode(publicKeyEncoded);
		EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
		EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, keySpecs);
		EdDSAPublicKey edDSAPublicKey = new EdDSAPublicKey(spec);

		// TODO: validate the signature
		JsonElement fulfObject = jsonObject.get("inputs").getAsJsonArray().get(0).getAsJsonObject().get("fulfillment");

		boolean signed = false;
		if ( ! fulfObject.equals( JsonNull.INSTANCE ) )
			signed = true;

		return new TransactionModel(data, metadata, jsonObject, edDSAPublicKey, signed);
	}

	/**
	 * Creates the from json array.
	 *
	 * @param jsonArray the json array
	 * @return the list
	 */
	public static List<TransactionModel> createFromJsonArray(JsonArray jsonArray) {
		List<TransactionModel> bigChaindbTransactionList = new ArrayList<TransactionModel>();
		for( JsonElement jsonElement: jsonArray ) {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonObject data = jsonObject.get("asset").getAsJsonObject().get("data").getAsJsonObject();
			JsonObject metadata = jsonObject.get("metadata").getAsJsonObject();
			String publicKeyEncoded = jsonObject.get("outputs").getAsJsonArray().get(0).getAsJsonObject().get("public_keys").getAsJsonArray().get(0).getAsString();
			byte[] publicKey = Base58.decode(publicKeyEncoded);
			EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
			EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, keySpecs);
			EdDSAPublicKey edDSAPublicKey = new EdDSAPublicKey(spec);

			// TODO: validate the signature
			JsonElement fulfObject = jsonObject.get("inputs").getAsJsonArray().get(0).getAsJsonObject().get("fulfillment");

			boolean signed = false;
			if ( ! fulfObject.equals( JsonNull.INSTANCE ) )
				signed = true;
			
			bigChaindbTransactionList.add(new TransactionModel(data, metadata, jsonObject, edDSAPublicKey, signed));
		}
		return bigChaindbTransactionList;
	}
}
