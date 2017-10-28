package com.authenteq.model;

import com.google.gson.annotations.SerializedName;



/**
 * The Class Status.
 */
public class Status {

	/** The status. */
	@SerializedName("status")
	private String status;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
