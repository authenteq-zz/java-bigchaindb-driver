package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.BigChainDBGlobals;
import com.authenteq.model.Outputs;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The Class OutputsApi.
 */
public class OutputsApi {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger( OutputsApi.class );
	/**
	 * Gets the outputs.
	 *
	 * @param publicKey the public key
	 * @return the outputs
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Outputs getOutputs(String publicKey) throws IOException { 
		log.debug( "getOutputs Call :" + publicKey );
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
		log.debug( "getSpentOutputs Call :" + publicKey );
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.OUTPUTS + "?public_key="+ publicKey+ "&spent=true");
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Outputs.class);
	}
	
}
