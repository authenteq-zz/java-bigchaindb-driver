package com.authenteq.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Driver;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.TransactionModel;
import com.authenteq.model.Transactions;
import com.authenteq.model.Globals;
import com.authenteq.model.Transaction;
import com.authenteq.model.GenericCallback;
import com.authenteq.util.DriverUtils;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import com.google.gson.reflect.TypeToken;

import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The Class TransactionsApi.
 */
public class TransactionsApi extends AbstractApi {

	/**
	 * Send transaction.
	 *
	 * @param transaction
	 *            the transaction
	 * @param callback
	 *            the callback
	 */
	public static void sendTransaction(Transaction transaction, final GenericCallback callback) {
		RequestBody body = RequestBody.create(JSON, transaction.toString());
		NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, body, callback);
	}

	/**
	 * Sends the transaction
	 * @param transaction
	 * @throws IOException
	 */
	public static void sendTransaction(Transaction transaction) throws IOException {
		RequestBody body = RequestBody.create(JSON, JsonUtils.toJson(transaction));
		NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, body);
	}


	/**
	 * Gets the transaction by id.
	 *
	 * @param id
	 *            the id
	 * @return the transaction by id
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Transaction getTransactionById(String id) throws IOException {
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "/" + id);
		return JsonUtils.fromJson(response.body().string(), Transaction.class);
	}

	/**
	 * Gets the transactions by asset id.
	 *
	 * @param assetId
	 *            the asset id
	 * @param operation
	 *            the operation
	 * @return the transactions by asset id
	 * @throws JSONException
	 *             the JSON exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Transactions getTransactionsByAssetId(String assetId, Operations operation)
			throws JSONException, IOException {

		Response response = NetworkUtils.sendGetRequest(
				Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "?asset_id=" + assetId + "&operation=" + operation);
		return JsonUtils.fromJson(response.body().string(), Transactions.class);

	}
}
