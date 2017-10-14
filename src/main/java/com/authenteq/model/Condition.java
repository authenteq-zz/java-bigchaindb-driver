package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public class Condition {
	@SerializedName("details")
	private Details details;
	
	@SerializedName("uri")
	private String uri;
	
	public Condition() {
		// TODO Auto-generated constructor stub
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
