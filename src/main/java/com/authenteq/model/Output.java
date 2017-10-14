package com.authenteq.model;

import java.util.ArrayList;
import java.util.List;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;

public class Output {
	
	@SerializedName("output_index")
	@Exclude
	private String outputIndex;
	@SerializedName("transaction_id")
	@Exclude
	private String transactionId;
	@SerializedName("amount")
	private String amount;
	@SerializedName("condition")
	private Condition condition;
	@SerializedName("public_keys")
	private List<String> publicKeys = new ArrayList<String>();
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public List<String> getPublicKeys() {
		return publicKeys;
	}
	public void setPublicKeys(List<String> publicKeys) {
		this.publicKeys = publicKeys;
	}
	
	public void addPublicKey(String publicKey){
		this.publicKeys.add(publicKey);
	}
	
}
