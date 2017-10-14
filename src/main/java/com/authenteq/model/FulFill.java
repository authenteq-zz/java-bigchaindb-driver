package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public class FulFill {
	
	@SerializedName("output_index")
	private String outputIndex;
	
	@SerializedName("transaction_id")
	private String transactionId;

	public String getOutputIndex() {
		return outputIndex;
	}

	public void setOutputIndex(String outputIndex) {
		this.outputIndex = outputIndex;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
}
