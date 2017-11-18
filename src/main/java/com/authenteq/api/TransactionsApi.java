package com.authenteq.api;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.constants.Operations;
import com.authenteq.model.GenericCallback;
import com.authenteq.model.BigChainDBGlobals;
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
		NetworkUtils.sendPostRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, body, callback);
	}

	/**
	 * Sends the transaction.
	 *
	 * @param transaction
	 *            the transaction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendTransaction(Transaction transaction) throws IOException {
		RequestBody body = RequestBody.create(JSON, JsonUtils.toJson(transaction));
		Response response = NetworkUtils.sendPostRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.TRANSACTIONS, body);
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
		Response response = NetworkUtils.sendGetRequest(BigChainDBGlobals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "/" + id);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Transaction.class);
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
				BigChainDBGlobals.getBaseUrl() + BigchainDbApi.TRANSACTIONS + "?asset_id=" + assetId + "&operation=" + operation);
		String body = response.body().string();
		response.close();
		return JsonUtils.fromJson(body, Transactions.class);

	}

}
