package com.authenteq.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BlockBody {

	@SerializedName("timestamp")
	private String timstamp;
	
	@SerializedName("transactions")
	private List<Transaction> transactions;
	
	@SerializedName("node_pubkey")
	private String nodePublicKey;
	
	@SerializedName("voters")
	private List<String> voters;

	public String getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(String timstamp) {
		this.timstamp = timstamp;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getNodePublicKey() {
		return nodePublicKey;
	}

	public void setNodePublicKey(String nodePublicKey) {
		this.nodePublicKey = nodePublicKey;
	}

	public List<String> getVoters() {
		return voters;
	}

	public void setVoters(List<String> voters) {
		this.voters = voters;
	}
	
}
