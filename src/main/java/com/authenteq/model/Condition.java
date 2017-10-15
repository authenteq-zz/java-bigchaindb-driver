package com.authenteq.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Condition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("details")
	private Details details;
	
	@SerializedName("uri")
	private String uri;
	
	public Condition() {
	}
	
	public Condition(Details details) {
		this.details = details;
	}
	
	public Condition(Details details, String uri) {
		this.details = details;
		this.uri = uri;
	}
	public Condition(String uri) {
		this.uri = uri;
	}
	

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
