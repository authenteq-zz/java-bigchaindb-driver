package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

//{
//    "transaction_id": "<sha3-256 hash>",
//    "asset_id": "<sha3-256 hash>",
//    "block_id": "<sha3-256 hash>"
//}

public class ValidTransaction {
	@SerializedName("transaction_id")
	private String transactionId;
	@SerializedName("asset_id")
	private String assetId;
	@SerializedName("block_id")
	private String blockId;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	
}
