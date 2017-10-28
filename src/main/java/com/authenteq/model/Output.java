package com.authenteq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;



/**
 * The Class Output.
 */
public class Output implements Serializable {
	
	/** The output index. */
	@SerializedName("output_index")
	@Exclude
	private String outputIndex;
	
	/** The transaction id. */
	@SerializedName("transaction_id")
	@Exclude
	private String transactionId;
	
	/** The amount. */
	@SerializedName("amount")
	private String amount;
	
	/** The condition. */
	@SerializedName("condition")
	private Condition condition;
	
	/** The public keys. */
	@SerializedName("public_keys")
	private List<String> publicKeys = new ArrayList<String>();
	
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
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}
	
	/**
	 * Sets the condition.
	 *
	 * @param condition the new condition
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	/**
	 * Gets the public keys.
	 *
	 * @return the public keys
	 */
	public List<String> getPublicKeys() {
		return publicKeys;
	}
	
	/**
	 * Sets the public keys.
	 *
	 * @param publicKeys the new public keys
	 */
	public void setPublicKeys(List<String> publicKeys) {
		this.publicKeys = publicKeys;
	}
	
	/**
	 * Adds the public key.
	 *
	 * @param publicKey the public key
	 */
	public void addPublicKey(String publicKey){
		this.publicKeys.add(publicKey);
	}
	
}
