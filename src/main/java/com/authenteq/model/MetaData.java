package com.authenteq.model;

import java.util.Map;
import java.util.TreeMap;

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
	
	public MetaData addMetaData(String key, String value) {
		if(this.data == null) {
			this.data = new TreeMap<String,String>();
		}
		this.data.put(key, value);
		return this;
	}
	

}
