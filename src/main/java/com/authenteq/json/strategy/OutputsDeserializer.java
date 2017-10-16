package com.authenteq.json.strategy;

import java.lang.reflect.Type;
import java.util.Iterator;
import com.authenteq.model.Output;
import com.authenteq.model.Outputs;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


/**
 * The Class OutputsDeserializer.
 */
public class OutputsDeserializer implements JsonDeserializer<Outputs> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Outputs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		Outputs outputs = new Outputs();
		Iterator<JsonElement> jsonIter = json.getAsJsonArray().iterator();
		while(jsonIter.hasNext()) {
			Output output = new Output();
			JsonElement jElement = jsonIter.next();
			output.setTransactionId(jElement.getAsJsonObject().get("transaction_id").toString().replace("\"", ""));
			output.setOutputIndex(jElement.getAsJsonObject().get("output_index").toString().replace("\"", ""));
			outputs.addOutput(output);
		}
		return outputs;
	}
	
}
