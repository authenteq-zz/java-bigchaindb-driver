package com.authenteq.model;

import com.authenteq.annotations.Exclude;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * The Class Asset.
 */
public class Asset implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 163400160721472722L;

	/** The id. */
	@SerializedName("id")
	@Exclude
	private String id;
	
	/** The data. */
	@SerializedName("data")
	private Object data;

	/** the data class the type of the data class needed for serialization/deserialization */
	@Exclude
	private Class dataClass;

	/**
	 * Instantiates a new asset.
	 */
	public Asset() {}
	
	/**
	 * Instantiates a new asset.
	 *
	 * @param data the data
	 * @param dataClass due to type erasure the data class needs to be provided for serialization/deserialization
	 */
	public Asset(Object data, Class dataClass ) {
		this.data = data;
		this.dataClass = dataClass;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * return the type of the Asset data class
	 *
	 * @return  the data class type
	 */
	public Class getDataClass()
	{
		return dataClass;
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
}
