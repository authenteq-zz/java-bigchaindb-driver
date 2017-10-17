package com.authenteq.constants;


/**
 * The Enum BigchainDbApi.
 */
public enum BigchainDbApi {
	
	/** The api version. */
	//	version.
	API_VERSION("/v1"),
	
	/** The streams. */
	//	Websocket
	STREAMS("/streams/valid_transactions"),
	
	/** The assets. */
	//	Core models
	ASSETS("/assets"),
	
	/** The outputs. */
	OUTPUTS("/outputs"),
	
	/** The statuses. */
	STATUSES("/statuses"),
	
	/** The blocks. */
	BLOCKS("/blocks"),
	
	/** The votes. */
	VOTES("/votes"),
	
	/** The transactions. */
	TRANSACTIONS("/transactions");

	/** The value. */
	private final String value;


	/**
	 * Instantiates a new bigchain db api.
	 *
	 * @param value the value
	 */
	BigchainDbApi(final String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}
}
