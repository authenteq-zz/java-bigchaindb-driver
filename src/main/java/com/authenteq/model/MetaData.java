package com.authenteq.model;

import java.util.Map;
import java.util.TreeMap;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;



/**
 * The Class MetaData.
 */
public class MetaData {
	
	/** The sequence. */
	@SerializedName("sequence")
	private Integer sequence = 0;
	
	/** The data. */
	@SerializedName("data")
	private Map<String,String> data;

	/**
	 * Instantiates a new meta data.
	 */
	public MetaData() {
	}
	
	/**
	 * Instantiates a new meta data.
	 *
	 * @param data the data
	 */
	public MetaData(Map<String,String> data) {
		this.data = data;
	}
	
	
	/**
	 * Gets the sequence.
	 *
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence.
	 *
	 * @param sequence the new sequence
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
	 * Adds the meta data.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the meta data
	 */
	public MetaData addMetaData(String key, String value) {
		if(this.data == null) {
			this.data = new TreeMap<String,String>();
		}
		this.data.put(key, value);
		return this;
	}
	

}
