package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.BigChainDBGlobals;
import com.authenteq.model.Outputs;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;



/**
 * The Class OutputsApi.
 */
public class OutputsApi {

	private static final Logger LOGGER = Logger.getLogger(OutputsApi.class.getName());
	/**
	 * Gets the outputs.
	 *
	 * @param publicKey the public key
	 * @return the outputs
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Outputs getOutputs(String publicKey) throws IOException { 
		LOGGER.info("getOutputs Call :" + publicKey);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.OUTPUTS + "?public_key="+ publicKey);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Outputs.class);
	}
	
	/**
	 * Gets the spent outputs.
	 *
	 * @param publicKey the public key
	 * @return the spent outputs
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Outputs getSpentOutputs(String publicKey) throws IOException { 
		LOGGER.info("getSpentOutputs Call :" + publicKey);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.OUTPUTS + "?public_key="+ publicKey+ "&spent=true");
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Outputs.class);
	}
	
}
