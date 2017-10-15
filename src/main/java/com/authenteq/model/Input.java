package com.authenteq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Input implements Serializable {

	@SerializedName("fulfillment")
	private String fullFillment = null;
	
	@SerializedName("fulfills")
	private FulFill fulFills = null;
	
	@SerializedName("owners_before")
	private List<String> ownersBefore = new ArrayList<String>();

	public String getFullFillment() {
		return fullFillment;
	}

	public void setFullFillment(String fullFillment) {
		this.fullFillment = fullFillment;
	}

	public FulFill getFulFills() {
		return fulFills;
	}

	public void setFulFills(FulFill fulFills) {
		this.fulFills = fulFills;
	}

	public List<String> getOwnersBefore() {
		return ownersBefore;
	}

	public void setOwnersBefore(List<String> ownersBefore) {
		this.ownersBefore = ownersBefore;
	}
	
	public void addOwner(String owner) {
		this.ownersBefore.add(owner);
	}

	
	
}
