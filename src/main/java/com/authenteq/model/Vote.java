package com.authenteq.model;

import com.google.gson.annotations.SerializedName;



/**
 * The Class Vote.
 */
public class Vote {

	/** The node public key. */
	@SerializedName("node_pubkey")
	private String nodePublicKey;
	
	/** The vote. */
	@SerializedName("vote")
	private VoteBody vote;
	
	/** The signature. */
	@SerializedName("signature")
	private String signature;
	
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
	 * Gets the vote.
	 *
	 * @return the vote
	 */
	public VoteBody getVote() {
		return vote;
	}
	
	/**
	 * Sets the vote.
	 *
	 * @param vote the new vote
	 */
	public void setVote(VoteBody vote) {
		this.vote = vote;
	}
	
	/**
	 * Gets the signature.
	 *
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}
	
	/**
	 * Sets the signature.
	 *
	 * @param signature the new signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
