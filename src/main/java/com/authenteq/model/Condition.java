package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



/**
 * The Class Condition.
 */
public class Condition implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The details. */
	@SerializedName("details")
	private Details details;
	
	/** The uri. */
	@SerializedName("uri")
	private String uri;
	
	/**
	 * Instantiates a new condition.
	 */
	public Condition() {
	}
	
	/**
	 * Instantiates a new condition.
	 *
	 * @param details the details
	 */
	public Condition(Details details) {
		this.details = details;
	}
	
	/**
	 * Instantiates a new condition.
	 *
	 * @param details the details
	 * @param uri the uri
	 */
	public Condition(Details details, String uri) {
		this.details = details;
		this.uri = uri;
	}
	
	/**
	 * Instantiates a new condition.
	 *
	 * @param uri the uri
	 */
	public Condition(String uri) {
		this.uri = uri;
	}
	

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public Details getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(Details details) {
		this.details = details;
	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Sets the uri.
	 *
	 * @param uri the new uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
