package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Assets;
import com.authenteq.model.BigChainDBGlobals;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The Class AssetsApi.
 */
public class AssetsApi {
	
	private static final Logger LOGGER = Logger.getLogger(AssetsApi.class.getName());
	/**
	 * Gets the assets.
	 *
	 * @param searchKey the search key
	 * @return the assets
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Assets getAssets(String searchKey) throws IOException {
		LOGGER.info("getAssets Call :" + searchKey);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.ASSETS + "?search="+ searchKey);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Assets.class);
	}
	
	/**
	 * Gets the assets with limit.
	 *
	 * @param searchKey the search key
	 * @param limit the limit
	 * @return the assets with limit
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Assets getAssetsWithLimit(String searchKey, String limit) throws IOException {
		LOGGER.info("getAssets Call :" + searchKey + " limit " + limit);
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.ASSETS + "?search="+ searchKey+ "&limit=" + limit);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Assets.class);
	}
	
}
