package com.authenteq.model;

import java.util.Map;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;

public class MetaData {
	
	@SerializedName("sequence")
	private Integer sequence = 0;
	
	@SerializedName("data")
	private Map<String,String> data;

	public MetaData() {
	}
	public MetaData(Map<String,String> data) {
		this.data = data;
	}
	
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	

}
