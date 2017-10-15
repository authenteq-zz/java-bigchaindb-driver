package com.authenteq.util;

import com.authenteq.json.strategy.CustomExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class for handling JSON serialization and deserialization.
 * 
 */
public class JsonUtils {

	private static Gson gson;

	private JsonUtils() {
	}

	private static Gson getGson() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();

			gson = builder.setPrettyPrinting().serializeNulls().disableHtmlEscaping().setPrettyPrinting()
					.setExclusionStrategies(new CustomExclusionStrategy()).create();
		}
		return gson;
	}

	/**
	 * From json.
	 *
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the string from which the object is to be deserialized.
	 * @param T
	 *            the type of the desired object.
	 * @return an object of type T from the string. Returns null if json is
	 *         null.
	 * @see Gson#fromJson(String, Class)
	 */
	public static <T> T fromJson(String json, Class<T> T) {
		return getGson().fromJson(json, T);
	}

	/**
	 * To json.
	 *
	 * @param src
	 *            the object for which Json representation is to be created
	 *            setting for Gson .
	 * @return Json representation of src.
	 * @see Gson#toJson(Object)
	 */
	public static String toJson(Object src) {
		return getGson().toJson(src);
	}

	public static Gson gson() {
		return gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonUtils []";
	}

}
