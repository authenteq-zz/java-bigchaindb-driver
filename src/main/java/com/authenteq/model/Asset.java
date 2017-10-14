package com.authenteq.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Asset {
	
	@SerializedName("id")
	private String id;
	
	
	@SerializedName("data")
	private Map<String,String> data;

	public Asset() {
		// TODO Auto-generated constructor stub
	}
	
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
	
	

}
