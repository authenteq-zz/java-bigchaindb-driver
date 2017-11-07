package com.authenteq.model;

import com.authenteq.util.JsonUtils;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class DataModel {
	
	/**
	 * To map string.
	 *
	 * @return the map
	 */
	public Map<String,String> toMapString() {
		Type mapType = new TypeToken<Map<String, String>>(){}.getType();  
		Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(this), mapType);
		return son;
	}
}
