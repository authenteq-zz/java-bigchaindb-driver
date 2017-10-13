package com.authenteq.api;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.Transaction;
import com.authenteq.model.Globals;
import com.authenteq.model.TransactionCallback;
import com.authenteq.util.NetworkUtils;

import okhttp3.Response;

// TODO: Auto-generated Javadoc
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
		Response response = NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS,
				transaction);
		return Transaction.createFromJson(new JSONObject(response.body().string()));
	}

	/**
	 * Gets the transaction by id.
	 *
	 * @param id the id
	 * @return the transaction by id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Transaction getTransactionById(String id) throws IOException {
		Response response = NetworkUtils.sendGetRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "/" + id);
		return Transaction.createFromJson(new JSONObject(response.body().string()));
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
	public static List<Transaction> getTransactionsByAssetId(String assetId, Operations operation)
			throws JSONException, IOException {
		Response response = NetworkUtils.sendGetRequest(
				Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "?asset_id=" + assetId + "&operation=" + operation);
		return Transaction.createFromJsonArray(new JSONArray(response.body().string()));

	}
}
