package com.authenteq.api;

/**
 * Any http errors when checking the status of Transactions or Blocks
 */
public class StatusException extends Exception
{
	private final int httpResponseCode;

	/**
	 * Constructor
	 *
	 * @param httpResponseCode the http error
	 * @param msg the error message
	 */
	public StatusException( int httpResponseCode, String msg )
	{
		super( msg );
		this.httpResponseCode = httpResponseCode;
	}

	/**
	 * Get the augmented error message with the HTTP Response code
	 *
	 * @return the message
	 */
	@Override
	public String getMessage()
	{
		return "HTTP Response code: " + httpResponseCode + " Message: "+ super.getMessage();
	}
}
