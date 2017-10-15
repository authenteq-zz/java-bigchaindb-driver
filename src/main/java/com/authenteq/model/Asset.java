package com.authenteq.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;

public class Asset implements Serializable {
	
	@SerializedName("id")
	@Exclude
	private String id;
	
	@SerializedName("data")
	private Map<String,String> data;

	public Asset() {}
	
	public Asset(Map<String,String> data) {
		this.data = data;
	}
	
	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public Asset addAsset(String key, String value) {
		if(this.data == null) {
			this.data = new TreeMap<String,String>();
		}
		this.data.put(key, value);
		return this;
	}
	
	

}
