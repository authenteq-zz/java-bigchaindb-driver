package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public class Vote {

	@SerializedName("node_pubkey")
	private String nodePublicKey;
	@SerializedName("vote")
	private VoteBody vote;
	@SerializedName("signature")
	private String signature;
	public String getNodePublicKey() {
		return nodePublicKey;
	}
	public void setNodePublicKey(String nodePublicKey) {
		this.nodePublicKey = nodePublicKey;
	}
	public VoteBody getVote() {
		return vote;
	}
	public void setVote(VoteBody vote) {
		this.vote = vote;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
