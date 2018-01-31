package com.authenteq.util;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * container for storing type sdapter information
 */
public class TypeAdapter
{
	private Class type;
	private Object serializer;

	/**
	 * Contruct a type adapter
	 *
	 * @param type the class
	 * @param serializer its serializer
	 */
	TypeAdapter( Class type, JsonDeserializer<?> serializer  )
	{
		this.type = type;
		this.serializer = serializer;
	}

	/**
	 * Contruct a type adapter
	 *
	 * @param type the class
	 * @param serializer its serializer
	 */
	TypeAdapter( Class type, JsonSerializer<?> serializer )
	{
		this.type = type;
		this.serializer = serializer;
	}

	/**
	 * Get the deserializer
	 *
	 * @return the deserializer
	 */
	public Object getSerializer()
	{
		return this.serializer;
	}

	/**
	 * Get the class for the deserializer
	 *
	 * @return the class
	 */
	public Class getType()
	{
		return type;
	}
}