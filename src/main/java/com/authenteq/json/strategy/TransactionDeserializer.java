package com.authenteq.json.strategy;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import com.authenteq.model.Asset;
import com.authenteq.model.Input;
import com.authenteq.model.Output;
import com.authenteq.model.Transaction;
import com.authenteq.model.Transactions;
import com.authenteq.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


/**
 * The Class TransactionsDeserializer.
 */
public class TransactionDeserializer implements JsonDeserializer<Transaction> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		// while(jsonIter.hasNext()) {
		Transaction transaction = new Transaction();
		JsonElement jElement = json.getAsJsonObject();

		transaction.setAsset(JsonUtils.fromJson(jElement.getAsJsonObject().get("asset").toString(), Asset.class));
		transaction.setMetaData((Map<String, String>) JsonUtils
				.fromJson(jElement.getAsJsonObject().get("metadata").toString(), Map.class));
		transaction.setId(jElement.getAsJsonObject().get("id").toString().replace("\"", ""));

		Iterator<JsonElement> jInputElementIter = jElement.getAsJsonObject().get("inputs").getAsJsonArray().iterator();

		while (jInputElementIter.hasNext()) {
			JsonElement jInputElement = jInputElementIter.next();
			transaction.addInput(JsonUtils.fromJson(jInputElement.toString(), Input.class));
		}

		Iterator<JsonElement> jOutputElementIter = jElement.getAsJsonObject().get("outputs").getAsJsonArray()
				.iterator();

		while (jOutputElementIter.hasNext()) {
			JsonElement jOutputElement = jOutputElementIter.next();
			transaction.addOutput(JsonUtils.fromJson(jOutputElement.toString(), Output.class));
		}

		transaction.setOperation(jElement.getAsJsonObject().get("operation").toString());
		transaction.setVersion(jElement.getAsJsonObject().get("version").toString());

		// }
		return transaction;
	}
}
