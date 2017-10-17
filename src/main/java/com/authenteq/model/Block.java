package com.authenteq.model;

import com.google.gson.annotations.SerializedName;


/**
 * The Class Block.
 */
public class Block {
	
	/** The id. */
	@SerializedName("id")
	private String id;
	
	/** The block. */
	@SerializedName("block")
	private BlockBody block;
	
	/** The signature. */
	@SerializedName("signature")
	private String signature;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the block.
	 *
	 * @return the block
	 */
	public BlockBody getBlock() {
		return block;
	}
	
	/**
	 * Sets the block.
	 *
	 * @param block the new block
	 */
	public void setBlock(BlockBody block) {
		this.block = block;
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
