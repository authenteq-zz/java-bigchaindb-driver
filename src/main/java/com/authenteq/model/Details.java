package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public class Details {

	@SerializedName("public_key")
	private String publicKey;
	
	@SerializedName("type")
	private String type;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
