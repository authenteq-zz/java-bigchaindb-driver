package com.authenteq.json.strategy;

import com.authenteq.model.*;
import com.authenteq.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;


/**
 * The Class TransactionsDeserializer.
 */
public class TransactionsDeserializer implements JsonDeserializer<Transactions> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Transactions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		Transactions transactions = new Transactions();
		Iterator<JsonElement> jsonIter = json.getAsJsonArray().iterator();
		while(jsonIter.hasNext()) {
			Transaction transaction = new Transaction();
			JsonElement jElement = jsonIter.next();
			
			transaction.setAsset(JsonUtils.fromJson(jElement.getAsJsonObject().get("asset").toString(), Asset.class));
			transaction.setMetaData((Map<String,String>)JsonUtils.fromJson(jElement.getAsJsonObject().get("metadata").toString(), Map.class));
			transaction.setId(jElement.getAsJsonObject().get("id").toString());
			
			Iterator<JsonElement> jInputElementIter = jElement.getAsJsonObject().get("inputs").getAsJsonArray().iterator();
			
			while(jInputElementIter.hasNext()) {
				JsonElement jInputElement = jInputElementIter.next();
				transaction.addInput(JsonUtils.fromJson(jInputElement.toString(), Input.class));
			}
			
			Iterator<JsonElement> jOutputElementIter = jElement.getAsJsonObject().get("outputs").getAsJsonArray().iterator();
			
			while(jOutputElementIter.hasNext()) {
				JsonElement jOutputElement = jOutputElementIter.next();
				transaction.addOutput(JsonUtils.fromJson(jOutputElement.toString(), Output.class));
			}
			
			transaction.setOperation(jElement.getAsJsonObject().get("operation").toString());
			transaction.setVersion(jElement.getAsJsonObject().get("version").toString());
			
			transactions.addTransaction(transaction);
			
		}
		return transactions;
	}
}
