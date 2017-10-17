package com.authenteq.api;

import java.io.IOException;
import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Globals;
import com.authenteq.model.Votes;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;

import okhttp3.Response;


/**
 * The Class VotesApi.
 */
public class VotesApi {
	
	/**
	 * Gets the votes.
	 *
	 * @param blockId the block id
	 * @return the votes
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Votes getVotes(String blockId) throws IOException {
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.VOTES + "?block_id=" + blockId);
		return JsonUtils.fromJson(response.body().string(), Votes.class);
	}

}
