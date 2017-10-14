package com.authenteq.api;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.TransactionModel;
import com.authenteq.model.Globals;
import com.authenteq.model.Transaction;
import com.authenteq.model.TransactionCallback;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;

import okhttp3.Response;

/**
 * The Class TransactionsApi.
 */
public class TransactionsApi {

	/**
	 * Send transaction.
	 *
	 * @param transaction the transaction
	 * @param callback the callback
	 */
	public static void sendTransaction(Transaction transaction, final TransactionCallback callback) {
		NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, transaction, callback);
	}

	/**
	 * Send transaction.
	 *
	 * @param transaction the transaction
	 * @return the transaction
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Transaction sendTransaction(Transaction transaction) throws IOException {
		System.out.println(JsonUtils.toJson(transaction));
		Response response = NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS,
				transaction);
		System.out.println(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + " > " + response.code());
		System.out.println(response.body().string());
		return JsonUtils.fromJson(response.body().string(), Transaction.class);
	}

	/**
	 * Gets the transaction by id.
	 *
	 * @param id the id
	 * @return the transaction by id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static TransactionModel getTransactionById(String id) throws IOException {
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "/" + id);
		return TransactionModel.createFromJson(new JSONObject(response.body().string()));
	}

	/**
	 * Gets the transactions by asset id.
	 *
	 * @param assetId the asset id
	 * @param operation the operation
	 * @return the transactions by asset id
	 * @throws JSONException the JSON exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<TransactionModel> getTransactionsByAssetId(String assetId, Operations operation)
			throws JSONException, IOException {
		Response response = NetworkUtils.sendGetRequest(
				Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "?asset_id=" + assetId + "&operation=" + operation);
		return TransactionModel.createFromJsonArray(new JSONArray(response.body().string()));

	}
}
