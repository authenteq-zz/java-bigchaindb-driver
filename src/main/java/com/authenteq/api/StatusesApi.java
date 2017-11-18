package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Globals;
import com.authenteq.model.Status;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;

import java.io.IOException;


/**
 * The Class StatusesApi.
 */
public class StatusesApi {
	
	/**
	 * Gets the transaction status.
	 *
	 * @param transactionId the transaction id
	 * @return the transaction status
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Status getTransactionStatus(String transactionId) throws IOException { 
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.STATUSES + "?transaction_id="+ transactionId);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Status.class);
	}
	
	/**
	 * Gets the block status.
	 *
	 * @param blockId the block id
	 * @return the block status
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Status getBlockStatus(String blockId) throws IOException { 
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.STATUSES + "?block_id="+ blockId);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Status.class);
	}
}
