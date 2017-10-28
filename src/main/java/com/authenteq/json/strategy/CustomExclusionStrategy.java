package com.authenteq.json.strategy;

import com.authenteq.annotations.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;



/**
 * The Class CustomExclusionStrategy.
 */
public class CustomExclusionStrategy implements ExclusionStrategy {

	/* (non-Javadoc)
	 * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
	 */
	public boolean shouldSkipField(FieldAttributes f) {
		if(f.getAnnotation(Exclude.class)!=null){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
	 */
	public boolean shouldSkipClass(Class clazz) {
		return false;
	}

}