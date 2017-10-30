package com.authenteq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Input.
 */
public class Input implements Serializable {

	/** The full fillment. */
	@SerializedName("fulfillment")
	private String fullFillment = null;
	
	/** The ful fills. */
	@SerializedName("fulfills")
	private FulFill fulFills = null;
	
	/** The owners before. */
	@SerializedName("owners_before")
	private List<String> ownersBefore = new ArrayList<String>();

	/**
	 * Gets the full fillment.
	 *
	 * @return the full fillment
	 */
	public String getFullFillment() {
		return fullFillment;
	}

	/**
	 * Sets the full fillment.
	 *
	 * @param fullFillment the new full fillment
	 */
	public void setFullFillment(String fullFillment) {
		this.fullFillment = fullFillment;
	}

	/**
	 * Gets the ful fills.
	 *
	 * @return the ful fills
	 */
	public FulFill getFulFills() {
		return fulFills;
	}

	/**
	 * Sets the ful fills.
	 *
	 * @param fulFills the new ful fills
	 */
	public void setFulFills(FulFill fulFills) {
		this.fulFills = fulFills;
	}

	/**
	 * Gets the owners before.
	 *
	 * @return the owners before
	 */
	public List<String> getOwnersBefore() {
		return ownersBefore;
	}

	/**
	 * Sets the owners before.
	 *
	 * @param ownersBefore the new owners before
	 */
	public void setOwnersBefore(List<String> ownersBefore) {
		this.ownersBefore = ownersBefore;
	}
	
	/**
	 * Adds the owner.
	 *
	 * @param owner the owner
	 */
	public void addOwner(String owner) {
		this.ownersBefore.add(owner);
	}

	
	
}
