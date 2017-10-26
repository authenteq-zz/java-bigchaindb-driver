package com.authenteq.constants;

public enum BlockStatus {

	UNDECIDED("UNDECIDED"),

	VALID("VALID"), INVALID("INVALID");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new operations.
	 *
	 * @param value
	 *            the value
	 */
	BlockStatus(final String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}
}
