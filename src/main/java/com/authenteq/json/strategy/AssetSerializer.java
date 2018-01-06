package com.authenteq.json.strategy;

import com.authenteq.model.Asset;
import com.authenteq.util.JsonUtils;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AssetSerializer implements JsonSerializer<Asset>
{
	/**
	 *  Serialize an asset object to json object
	 *  Note: given the type of the asset.data it maybe necessary to
	 *  to add a type adapter {@link JsonSerializer} and/or {@link JsonDeserializer} with {@link JsonUtils} and
	 *  {@link com.authenteq.util.JsonUtils#addTypeAdapterSerializer}
	 *
	 *  TODO test user.data with custom serializer
	 *
	 * @param src object to serialize
	 * @param typeOfSrc type of src
	 * @param context the json context
	 * @return the json object
	 */
	public JsonElement serialize( Asset src, Type typeOfSrc, JsonSerializationContext context )
	{
		Gson gson = JsonUtils.getGson();
		JsonObject asset = new JsonObject();
		asset.add( "data", gson.toJsonTree( src.getData(), src.getDataClass() ) );
		
		return asset;
	}
}
