package com.authenteq.model;

import com.google.gson.annotations.SerializedName;



/**
 * The Class VoteBody.
 */
public class VoteBody {
	
	/** The voting for block. */
	@SerializedName("voting_for_block")
	private String votingForBlock;
	
	/** The previous block. */
	@SerializedName("previous_block")
	private String previousBlock;
	
	/** The is block valid. */
	@SerializedName("is_block_valid")
	private Boolean isBlockValid;
	
	/** The invalid reason. */
	@SerializedName("invalid_reason")
	private String invalidReason;
	
	/** The time stamp. */
	@SerializedName("timestamp")
	private String timeStamp;

	/**
	 * Gets the voting for block.
	 *
	 * @return the voting for block
	 */
	public String getVotingForBlock() {
		return votingForBlock;
	}

	/**
	 * Sets the voting for block.
	 *
	 * @param votingForBlock the new voting for block
	 */
	public void setVotingForBlock(String votingForBlock) {
		this.votingForBlock = votingForBlock;
	}

	/**
	 * Gets the previous block.
	 *
	 * @return the previous block
	 */
	public String getPreviousBlock() {
		return previousBlock;
	}

	/**
	 * Sets the previous block.
	 *
	 * @param previousBlock the new previous block
	 */
	public void setPreviousBlock(String previousBlock) {
		this.previousBlock = previousBlock;
	}

	/**
	 * Gets the checks if is block valid.
	 *
	 * @return the checks if is block valid
	 */
	public Boolean getIsBlockValid() {
		return isBlockValid;
	}

	/**
	 * Sets the checks if is block valid.
	 *
	 * @param isBlockValid the new checks if is block valid
	 */
	public void setIsBlockValid(Boolean isBlockValid) {
		this.isBlockValid = isBlockValid;
	}

	/**
	 * Gets the invalid reason.
	 *
	 * @return the invalid reason
	 */
	public String getInvalidReason() {
		return invalidReason;
	}

	/**
	 * Sets the invalid reason.
	 *
	 * @param invalidReason the new invalid reason
	 */
	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
