package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.BigChainDBGlobals;
import com.authenteq.model.Status;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;



/**
 * The Class StatusesApi.
 */
public class StatusesApi {
	
	private static final Logger LOGGER = Logger.getLogger(StatusesApi.class.getName());
	
	/**
	 * Gets the transaction status.
	 *
	 * @param transactionId the transaction id
	 * @return the transaction status
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Status getTransactionStatus(String transactionId) throws IOException { 
		LOGGER.info("getTransactionStatus Call :" + transactionId);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.STATUSES + "?transaction_id="+ transactionId);
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
		LOGGER.info("getBlockStatus Call :" + blockId);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.STATUSES + "?block_id="+ blockId);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Status.class);
	}
}
