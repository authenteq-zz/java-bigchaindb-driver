package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.GenericCallback;
import com.authenteq.model.Globals;
import com.authenteq.model.Transaction;
import com.authenteq.model.Transactions;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;

import java.io.IOException;


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
	 * Sends the transaction.
	 *
	 * @param transaction the transaction
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void sendTransaction(Transaction transaction) throws IOException {
		RequestBody body = RequestBody.create(JSON, JsonUtils.toJson(transaction));
		Response response = NetworkUtils.sendPostRequest(Globals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, body);
		System.out.println(response.body().string());
		System.out.println(response.message());
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
