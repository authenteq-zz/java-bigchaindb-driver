package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

public class Block {
	
	@SerializedName("id")
	private String id;
	@SerializedName("block")
	private BlockBody block;
	@SerializedName("signature")
	private String signature;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BlockBody getBlock() {
		return block;
	}
	public void setBlock(BlockBody block) {
		this.block = block;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	

}
