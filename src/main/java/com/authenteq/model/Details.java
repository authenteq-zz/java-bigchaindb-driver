package com.authenteq.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;


/**
 * The Class Details.
 */
public class Details implements Serializable {

	/** The public key. */
	@SerializedName("public_key")
	private String publicKey;
	
	/** The type. */
	@SerializedName("type")
	private String type;

	/**
	 * Gets the public key.
	 *
	 * @return the public key
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * Sets the public key.
	 *
	 * @param publicKey the new public key
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}
