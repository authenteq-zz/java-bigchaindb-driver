package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public enum StatusCode
{
	@SerializedName( "valid" )
	VALID( "valid" ),
	@SerializedName( "backlog" )
	BACKLOG( "backlog" ),
	@SerializedName( "undecided" )
	UNDECIDED( "undecided" );

	private final String statusCode;

	/**
	 * Status code initializer
	 *
	 * @param status the status code
	 */
	StatusCode( final String status )
	{
		this.statusCode = status;
	}

	/**
	 * get the status
	 *
	 * @return the status
	 */
	public String statusCode()
	{
		return this.statusCode;
	}

	/**
	 * toString
	 *
	 * @return status
	 */
	@Override
	public String toString()
	{
		return this.statusCode;
	}
}