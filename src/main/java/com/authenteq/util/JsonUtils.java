package com.authenteq.util;

import com.authenteq.json.factory.GsonEmptyCheckTypeAdapterFactory;
import com.authenteq.json.strategy.*;
import com.authenteq.model.*;
import com.google.gson.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for handling JSON serialization and deserialization.
 */
public class JsonUtils {

	/** The gson. */
	private static String jsonDateFormat = "yyyy-MM-dd'T'HH:mm:ssX";  // Assumes Java 7 or higher

	private static Map<String, TypeAdapter> typeAdaptersDeserialize = new ConcurrentHashMap<String, TypeAdapter>(16)
	{{
		put( Transaction.class.getCanonicalName(), new TypeAdapter( Transaction.class, new TransactionDeserializer() ) );
		put( Transactions.class.getCanonicalName(), new TypeAdapter( Transactions.class, new TransactionsDeserializer() ) );
		put( Assets.class.getCanonicalName(), new TypeAdapter( Assets.class, new AssetsDeserializer() ) );
		put( Outputs.class.getCanonicalName(), new TypeAdapter( Outputs.class, new OutputsDeserializer() ) );
		put( Votes.class.getCanonicalName(), new TypeAdapter( Votes.class, new VoteDeserializer() ) );
	}};

	private static Map<String, TypeAdapter> typeAdaptersSerialize = new ConcurrentHashMap<String, TypeAdapter>(16)
	{{
		put( Asset.class.getCanonicalName(), new TypeAdapter( Asset.class, new AssetSerializer() ) );
	}};

	/**
	 * Instantiates a new json utils.
	 */
	private JsonUtils() {
	}
	
	private static synchronized GsonBuilder base()
	{
		GsonBuilder builder = new GsonBuilder();

		builder = builder
			          .serializeNulls()
			          .disableHtmlEscaping()
			          .setDateFormat( jsonDateFormat )
			          .registerTypeAdapterFactory(new GsonEmptyCheckTypeAdapterFactory())
			          .addSerializationExclusionStrategy(new CustomExclusionStrategy());

		return builder;
	}

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	public static Gson getGson() {
		GsonBuilder builder = base();

		for( TypeAdapter value : typeAdaptersDeserialize.values() )
			builder.registerTypeAdapter( value.getType(), value.getSerializer() );

		for( TypeAdapter value : typeAdaptersSerialize.values() )
			builder.registerTypeAdapter( value.getType(), value.getSerializer() );

		return builder.create();
	}

	/**
	 *
	 * @return the gson
	 */
	public static Gson getGson( ExclusionStrategy... exclusionStrategies ) {
		return getGson( null, exclusionStrategies );
	}

	/**
	 *
	 * @return the gson
	 */
	public static Gson getGson( Class ignoreClass, ExclusionStrategy... exclusionStrategies )
	{
		GsonBuilder builder = base();

		for( TypeAdapter value : typeAdaptersDeserialize.values() ) {
			if( ignoreClass != null && value.getType().equals( ignoreClass ) )
				continue;
			builder.registerTypeAdapter( value.getType(), value.getSerializer() );
		}

		for( TypeAdapter value : typeAdaptersSerialize.values() ) {
			if( ignoreClass != null && value.getType().equals( ignoreClass ) )
				continue;

			builder.registerTypeAdapter( value.getType(), value.getSerializer() );
		}

		return builder.setExclusionStrategies( exclusionStrategies ).create();
	}

	public static void setJsonDateFormat( final String dateFormat )
	{
		jsonDateFormat = dateFormat;
	}

	/**
	 * Add a type adapter
	 *
	 * @param type the type (@Class) we're adapting
	 * @param jsonDeserializer the type's deserializer
	 */
	public static void addTypeAdapterDeserializer( Class type, JsonDeserializer<?> jsonDeserializer )
	{
		typeAdaptersDeserialize.put( type.getCanonicalName(), new TypeAdapter( type, jsonDeserializer ) );
	}

	/**
	 * Add a type adapter
	 *
	 * @param type the type (@Class) we're adapting
	 * @param jsonSerializer the type's deserializer
	 */
	public static void addTypeAdapterSerializer( Class type, JsonSerializer<?> jsonSerializer )
	{
		typeAdaptersSerialize.put( type.getCanonicalName(), new TypeAdapter( type, jsonSerializer ) );
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

	/**
	 * To json.
	 *
	 * @param src
	 *            the object for which Json representation is to be created
	 *            setting for Gson .
	 * @return Json representation of src.
	 * @see Gson#toJson(Object)
	 */
	public static String toJson(Object src, ExclusionStrategy ... exclusionStrategies) {
		return getGson( exclusionStrategies ).toJson(src);
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
