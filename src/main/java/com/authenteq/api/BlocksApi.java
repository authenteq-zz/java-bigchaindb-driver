package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Block;
import com.authenteq.model.Globals;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;


/**
 * The Class BlocksApi.
 */
public class BlocksApi {

	/**
	 * Gets the block.
	 *
	 * @param blockId the block id
	 * @return the block
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Block getBlock(String blockId) throws IOException { 
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.BLOCKS + "/"+ blockId);
		return JsonUtils.fromJson(response.body().string(), Block.class);
	}
	
	/**
	 * Gets the blocks.
	 *
	 * @param transactionId the transaction id
	 * @param status the status
	 * @return the blocks
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<String> getBlocks(String transactionId, String status) throws IOException { 
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.BLOCKS + "?transaction_id="+transactionId+"&status="+status);
		return JsonUtils.gson().fromJson(response.body().string(), new TypeToken<List<String>>(){}.getType());
	}
	
}
