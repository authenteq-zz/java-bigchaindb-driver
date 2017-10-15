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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The Class Transaction.
 */
public class TransactionModel {
	
	/** The public key. */
	private EdDSAPublicKey publicKey;
	
	/** The data. */
	private JSONObject data;
	
	/** The metadata. */
	private JSONObject metadata;
	
	/** The transaction json. */
	private JSONObject transactionJson;
	
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
	public TransactionModel(JSONObject data, JSONObject metadata, EdDSAPublicKey publicKey) {
		this.publicKey = publicKey;
		this.data = DriverUtils.makeSelfSorting(data);
		this.metadata = DriverUtils.makeSelfSorting(metadata);
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
	private TransactionModel(JSONObject data, JSONObject metadata, JSONObject transactionJson,
			EdDSAPublicKey publicKey, boolean signed) {
		this.publicKey = publicKey;
		this.data = DriverUtils.makeSelfSorting(data);
		this.metadata = DriverUtils.makeSelfSorting(metadata);
		this.transactionJson = DriverUtils.makeSelfSorting(transactionJson);
		this.signed = signed;
	}

	/**
	 * Builds the transaction JSON without actually signing it.
	 */
	protected void buildTransactionJson() {
		JSONObject asset = DriverUtils.getSelfSortingJson();
		JSONObject outputs = DriverUtils.getSelfSortingJson();
		JSONObject inputs = DriverUtils.getSelfSortingJson();
		JSONObject condition = DriverUtils.getSelfSortingJson();
		JSONObject details = DriverUtils.getSelfSortingJson();
		JSONArray inputsArr = new JSONArray();
		JSONArray outputsArr = new JSONArray();

		Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition(publicKey);

		JSONObject rootObject = DriverUtils.getSelfSortingJson();
		try {
			if (metadata == null) {
				rootObject.put("metadata", JSONObject.NULL);
			} else {
				rootObject.put("metadata", metadata);
			}

			rootObject.put("operation", "CREATE");
			rootObject.put("version", "1.0");
			asset.put("data", data);
			rootObject.put("asset", asset);

			outputs.put("amount", "1");
			JSONArray publicKeys = new JSONArray();
			publicKeys.put(DriverUtils.convertToBase58(publicKey));
			outputs.put("public_keys", publicKeys);
			outputsArr.put(outputs);
			rootObject.put("outputs", outputsArr);

			condition.put("uri", condition1.getUri().toString());

			details.put("public_key", DriverUtils.convertToBase58(publicKey));
			details.put("type", "ed25519-sha-256");
			condition.put("details", details);
			outputs.put("condition", condition);

			inputs.put("fulfillment", JSONObject.NULL);
			inputs.put("fulfills", JSONObject.NULL);
			JSONArray ownersBefore = new JSONArray();
			ownersBefore.put(DriverUtils.convertToBase58(publicKey));
			inputs.put("owners_before", ownersBefore);
			inputsArr.put(inputs);
			rootObject.put("inputs", inputsArr);

			// getting SHA3 hash of the current JSON object
			SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
			md.update(rootObject.toString().getBytes());
			String id = DriverUtils.getHex(md.digest());

			// putting the hash as id field
			rootObject.put("id", id);
		} catch (JSONException e) {
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
		return transactionJson.getString("id");
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
			JSONObject inputs = transactionJson.getJSONArray("inputs").getJSONObject(0);
			inputs.put("fulfillment", Base64.encodeBase64URLSafeString(fulfillment.getEncoded()));
			signed = true;
		} catch (JSONException | NoSuchAlgorithmException e) {
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
	public JSONObject getTransactionJson() {
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
	public JSONObject getData() {
		return data;
	}

	/**
	 * Gets the metadata.
	 *
	 * @return the metadata
	 */
	public JSONObject getMetadata() {
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
	public static TransactionModel createFromJson(JSONObject jsonObject) {
		JSONObject data = jsonObject.getJSONObject("asset").getJSONObject("data");
		JSONObject metadata = jsonObject.getJSONObject("metadata");
		String publicKeyEncoded = jsonObject.getJSONArray("outputs").getJSONObject(0).getJSONArray("public_keys")
				.getString(0);
		byte[] publicKey = Base58.decode(publicKeyEncoded);
		EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
		EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, keySpecs);
		EdDSAPublicKey edDSAPublicKey = new EdDSAPublicKey(spec);

		// TODO: validate the signature
		Object fulfObject = jsonObject.getJSONArray("inputs").getJSONObject(0).get("fulfillment");

		boolean signed = false;
		if (!JSONObject.NULL.equals(fulfObject))
			signed = true;

		return new TransactionModel(data, metadata, jsonObject, edDSAPublicKey, signed);
	}

	/**
	 * Creates the from json array.
	 *
	 * @param jsonArray the json array
	 * @return the list
	 */
	public static List<TransactionModel> createFromJsonArray(JSONArray jsonArray) {
		List<TransactionModel> bigChaindbTransactionList = new ArrayList<TransactionModel>();
		Iterator<Object> jsonObjectIter = jsonArray.iterator();
		while (jsonObjectIter.hasNext()) {
			JSONObject jsonObject = (JSONObject)jsonObjectIter.next();
			JSONObject data = jsonObject.getJSONObject("asset").getJSONObject("data");
			JSONObject metadata = jsonObject.getJSONObject("metadata");
			String publicKeyEncoded = jsonObject.getJSONArray("outputs").getJSONObject(0).getJSONArray("public_keys")
					.getString(0);
			byte[] publicKey = Base58.decode(publicKeyEncoded);
			EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
			EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, keySpecs);
			EdDSAPublicKey edDSAPublicKey = new EdDSAPublicKey(spec);

			// TODO: validate the signature
			Object fulfObject = jsonObject.getJSONArray("inputs").getJSONObject(0).get("fulfillment");

			boolean signed = false;
			if (!JSONObject.NULL.equals(fulfObject))
				signed = true;
			
			bigChaindbTransactionList.add(new TransactionModel(data, metadata, jsonObject, edDSAPublicKey, signed));
		}
		return bigChaindbTransactionList;
	}
}
