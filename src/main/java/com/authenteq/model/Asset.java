package com.authenteq.model;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;



/**
 * The Class Asset.
 */
public class Asset implements Serializable {
	
	/** The id. */
	@SerializedName("id")
	@Exclude
	private String id;
	
	/** The data. */
	@SerializedName("data")
	private Map<String,String> data;

	/**
	 * Instantiates a new asset.
	 */
	public Asset() {}
	
	/**
	 * Instantiates a new asset.
	 *
	 * @param data the data
	 */
	public Asset(Map<String,String> data) {
		this.data = data;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Map<String, String> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data
	 */
	public void setData(Map<String, String> data) {
		this.data = data;
	}

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
	 * Adds the asset.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the asset
	 */
	public Asset addAsset(String key, String value) {
		if(this.data == null) {
			this.data = new TreeMap<String,String>();
		}
		this.data.put(key, value);
		return this;
	}
	
	

}
