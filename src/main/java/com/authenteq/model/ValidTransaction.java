package com.authenteq.model;

import com.google.gson.annotations.SerializedName;


//{
//    "transaction_id": "<sha3-256 hash>",
//    "asset_id": "<sha3-256 hash>",
//    "block_id": "<sha3-256 hash>"
//}

/**
 * The Class ValidTransaction.
 */
public class ValidTransaction {
	
	/** The transaction id. */
	@SerializedName("transaction_id")
	private String transactionId;
	
	/** The asset id. */
	@SerializedName("asset_id")
	private String assetId;
	
	/** The block id. */
	@SerializedName("block_id")
	private String blockId;
	
	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}
	
	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	/**
	 * Gets the asset id.
	 *
	 * @return the asset id
	 */
	public String getAssetId() {
		return assetId;
	}
	
	/**
	 * Sets the asset id.
	 *
	 * @param assetId the new asset id
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	/**
	 * Gets the block id.
	 *
	 * @return the block id
	 */
	public String getBlockId() {
		return blockId;
	}
	
	/**
	 * Sets the block id.
	 *
	 * @param blockId the new block id
	 */
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	
}
