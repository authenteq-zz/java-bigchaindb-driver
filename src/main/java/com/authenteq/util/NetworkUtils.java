package com.authenteq.util;

import com.authenteq.model.GenericCallback;
import com.authenteq.model.Globals;
import okhttp3.*;

import java.io.IOException;


/**
 * The Class NetworkUtils.
 */
public class NetworkUtils {

	/**
	 *  The Constant JSON.
	 *
	 * @param url the url
	 * @param body the body
	 * @param callback the callback
	 */
	

	/**
	 * Send post request.
	 *
	 * @param url the url
	 * @param transaction the transaction
	 * @param callback the callback
	 */
	public static void sendPostRequest(String url, RequestBody body,
			final GenericCallback callback) {
		
		Request request = new Request.Builder().url(url).post(body).build();

		Globals.getHttpClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.code() == 202) {
					callback.pushedSuccessfully(response);
				} else if (response.code() == 400) {
					callback.transactionMalformed(response);
				} else {
					callback.otherError(response);
				}
			}
		});
	}
	
	/**
	 * Send post request.
	 *
	 * @param url the url
	 * @param body the body
	 * @return the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Response sendPostRequest(String url, RequestBody body) throws IOException {
		Request request = new Request.Builder().url(url).post(body).build();
		return Globals.getHttpClient().newCall(request).execute();
	}

	/**
	 * Send get request.
	 *
	 * @param url the url
	 * @return the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Response sendGetRequest(String url) throws IOException {
		Request request = new Request.Builder().url(url).get().build();
		return Globals.getHttpClient().newCall(request).execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NetworkUtils []";
	}

}
