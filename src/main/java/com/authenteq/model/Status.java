package com.authenteq.model;

import com.google.gson.annotations.SerializedName;



/**
 * The Class Status.
 */
public class Status
{
	/** The status. */
	private StatusCode status;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public StatusCode getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( StatusCode statusCode )
	{
		this.status = statusCode;
	}
}
