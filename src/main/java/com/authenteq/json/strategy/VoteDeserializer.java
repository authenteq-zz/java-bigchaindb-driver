package com.authenteq.json.strategy;

import com.authenteq.model.Vote;
import com.authenteq.model.Votes;
import com.authenteq.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Iterator;


/**
 * The Class VoteDeserializer.
 */
public class VoteDeserializer implements JsonDeserializer<Votes> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Votes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		Votes votes = new Votes();
		Iterator<JsonElement> jsonIter = json.getAsJsonArray().iterator();
		while(jsonIter.hasNext()) {
			JsonElement jElement = jsonIter.next();
			votes.addVote(JsonUtils.fromJson(jElement.getAsJsonObject().toString(), Vote.class));
		}
		return votes;
	}
	
}
