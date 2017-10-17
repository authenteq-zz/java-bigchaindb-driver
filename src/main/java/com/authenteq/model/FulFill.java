package com.authenteq.model;

import com.google.gson.annotations.SerializedName;


/**
 * The Class FulFill.
 */
public class FulFill {
	
	/** The output index. */
	@SerializedName("output_index")
	private String outputIndex = "";
	
	/** The transaction id. */
	@SerializedName("transaction_id")
	private String transactionId = "";

	/**
	 * Gets the output index.
	 *
	 * @return the output index
	 */
	public String getOutputIndex() {
		return outputIndex;
	}

	/**
	 * Sets the output index.
	 *
	 * @param outputIndex the new output index
	 */
	public void setOutputIndex(String outputIndex) {
		this.outputIndex = outputIndex;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
}
