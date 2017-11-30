package com.authenteq.util;

import com.authenteq.json.factory.GsonEmptyCheckTypeAdapterFactory;
import com.authenteq.json.strategy.*;
import com.authenteq.model.Assets;
import com.authenteq.model.Outputs;
import com.authenteq.model.Transactions;
import com.authenteq.model.Votes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class for handling JSON serialization and deserialization.
 * 
 */
public class JsonUtils {

	/** The gson. */
	private static Gson gson;

	/**
	 * Instantiates a new json utils.
	 */
	private JsonUtils() {
	}

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	public static Gson getGson() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			
			gson = builder.setPrettyPrinting()
					.serializeNulls()
					.disableHtmlEscaping()
					.setPrettyPrinting()
					.registerTypeAdapter(Transactions.class, new TransactionsDeserializer())
					.registerTypeAdapter(Assets.class, new AssetsDeserializer())
					.registerTypeAdapter(Outputs.class, new OutputsDeserializer())
					.registerTypeAdapter(Votes.class, new VoteDeserializer())
					.registerTypeAdapterFactory(new GsonEmptyCheckTypeAdapterFactory())
					.addSerializationExclusionStrategy(new CustomExclusionStrategy()).create();
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
