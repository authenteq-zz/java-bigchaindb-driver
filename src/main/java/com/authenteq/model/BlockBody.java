package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



/**
 * The Class BlockBody.
 */
public class BlockBody {

	/** The timstamp. */
	@SerializedName("timestamp")
	private String timstamp;
	
	/** The transactions. */
	@SerializedName("transactions")
	private List<Transaction> transactions;
	
	/** The node public key. */
	@SerializedName("node_pubkey")
	private String nodePublicKey;
	
	/** The voters. */
	@SerializedName("voters")
	private List<String> voters;

	/**
	 * Gets the timstamp.
	 *
	 * @return the timstamp
	 */
	public String getTimstamp() {
		return timstamp;
	}

	/**
	 * Sets the timstamp.
	 *
	 * @param timstamp the new timstamp
	 */
	public void setTimstamp(String timstamp) {
		this.timstamp = timstamp;
	}

	/**
	 * Gets the transactions.
	 *
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Sets the transactions.
	 *
	 * @param transactions the new transactions
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * Gets the node public key.
	 *
	 * @return the node public key
	 */
	public String getNodePublicKey() {
		return nodePublicKey;
	}

	/**
	 * Sets the node public key.
	 *
	 * @param nodePublicKey the new node public key
	 */
	public void setNodePublicKey(String nodePublicKey) {
		this.nodePublicKey = nodePublicKey;
	}

	/**
	 * Gets the voters.
	 *
	 * @return the voters
	 */
	public List<String> getVoters() {
		return voters;
	}

	/**
	 * Sets the voters.
	 *
	 * @param voters the new voters
	 */
	public void setVoters(List<String> voters) {
		this.voters = voters;
	}
	
}
