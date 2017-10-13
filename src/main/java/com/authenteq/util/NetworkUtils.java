package com.authenteq.util;

import java.io.IOException;

import org.json.JSONObject;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Transaction;
import com.authenteq.model.Globals;
import com.authenteq.model.TransactionCallback;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkUtils.
 */
public class NetworkUtils {

	/** The Constant JSON. */
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	/**
	 * Send post request.
	 *
	 * @param url the url
	 * @param transaction the transaction
	 * @param callback the callback
	 */
	public static void sendPostRequest(String url, Transaction transaction,
			final TransactionCallback callback) {
		RequestBody body = RequestBody.create(JSON, transaction.toString());
		Request request = new Request.Builder().url(url).post(body).build();

		Globals.getHttpClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				callback.otherError();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.code() == 202) {
					callback.pushedSuccessfully();
				} else if (response.code() == 400) {
					callback.transactionMalformed();
				} else {
					callback.otherError();
				}
			}
		});
	}
	
	/**
	 * Send post request.
	 *
	 * @param url the url
	 * @param transaction the transaction
	 * @return the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Response sendPostRequest(String url, Transaction transaction) throws IOException {
		RequestBody body = RequestBody.create(JSON, transaction.toString());
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
