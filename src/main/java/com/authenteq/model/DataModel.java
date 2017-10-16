package com.authenteq.model;

import java.lang.reflect.Type;
import java.util.Map;

import com.authenteq.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

public abstract class DataModel {
	
	public Map<String,String> toMapString() {
		Type mapType = new TypeToken<Map<String, String>>(){}.getType();  
		Map<String, String> son = JsonUtils.getGson().fromJson(JsonUtils.toJson(this), mapType);
		return son;
	}
}
