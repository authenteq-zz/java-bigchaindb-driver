package com.authenteq.constants;


/**
 * The Enum Operations.
 */
public enum Operations {

	/** The create. */
	CREATE("CREATE"),
	
	/** The transfer. */
	TRANSFER("TRANSFER");
	
	/** The value. */
	private final String value;
	
	/**
	 * Instantiates a new operations.
	 *
	 * @param value the value
	 */
	Operations(final String value) {
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
