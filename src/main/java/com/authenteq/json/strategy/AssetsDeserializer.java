package com.authenteq.json.strategy;

import com.authenteq.model.Asset;
import com.authenteq.model.Assets;
import com.authenteq.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * The Class AssetsDeserializer.
 */
public class AssetsDeserializer implements JsonDeserializer<Assets> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Assets deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		Assets assets = new Assets();
		for( JsonElement jElement: json.getAsJsonArray() ) {
			assets.addAsset(JsonUtils.fromJson(jElement.getAsJsonObject().toString(), Asset.class));
		}
		return assets;
	}
	
}
