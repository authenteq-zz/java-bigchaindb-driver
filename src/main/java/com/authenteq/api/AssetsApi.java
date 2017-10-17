package com.authenteq.api;

import java.io.IOException;
import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Assets;
import com.authenteq.model.Globals;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.Response;

/**
 * The Class AssetsApi.
 */
public class AssetsApi {
	
	/**
	 * Gets the assets.
	 *
	 * @param searchKey the search key
	 * @return the assets
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Assets getAssets(String searchKey) throws IOException { 
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.ASSETS + "?search="+ searchKey);
		return JsonUtils.fromJson(response.body().string(), Assets.class);
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
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.ASSETS + "?search="+ searchKey+ "&limit=" + limit);
		return JsonUtils.fromJson(response.body().string(), Assets.class);
	}
	
}
