package com.authenteq.model;

import com.google.gson.annotations.SerializedName;


public class VoteBody {
	
	@SerializedName("voting_for_block")
	private String votingForBlock;
	
	@SerializedName("previous_block")
	private String previousBlock;
	
	@SerializedName("is_block_valid")
	private Boolean isBlockValid;
	
	@SerializedName("invalid_reason")
	private String invalidReason;
	
	@SerializedName("timestamp")
	private String timeStamp;

	public String getVotingForBlock() {
		return votingForBlock;
	}

	public void setVotingForBlock(String votingForBlock) {
		this.votingForBlock = votingForBlock;
	}

	public String getPreviousBlock() {
		return previousBlock;
	}

	public void setPreviousBlock(String previousBlock) {
		this.previousBlock = previousBlock;
	}

	public Boolean getIsBlockValid() {
		return isBlockValid;
	}

	public void setIsBlockValid(Boolean isBlockValid) {
		this.isBlockValid = isBlockValid;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
