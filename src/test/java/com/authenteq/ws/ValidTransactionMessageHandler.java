package com.authenteq.ws;

import com.authenteq.model.ValidTransaction;
import com.authenteq.util.JsonUtils;

public class ValidTransactionMessageHandler implements MessageHandler {
	@Override
	public void handleMessage(String message) {
		ValidTransaction validTransaction = JsonUtils.fromJson(message, ValidTransaction.class);
	}
}
