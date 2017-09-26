/*
 * (C) Copyright 2017 Authenteq (https://authenteq.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Bohdan Bezpartochnyi <bohdan@authenteq.com>
 */

package com.authenteq;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Communication layer that represents the connection to the BigchainDB server
 */
public class BigchaindbConnection {
    public static final String API_VERSION = "/v1";
    public static final String TRANSACTIONS_ENDPOINT = "/transactions";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE = MediaType.parse("multipart/mixed");

    protected String baseUrl;
    protected Map<String, String> tokens;
    protected OkHttpClient httpClient;

    private Interceptor authInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();

            if (tokens == null)
                return chain.proceed(originalRequest);

            // Add authorization header with updated authorization value to intercepted request
            Request.Builder authorisedRequest = originalRequest.newBuilder();

            for (String key: tokens.keySet()) {
                authorisedRequest = authorisedRequest
                        .addHeader(key, tokens.get(key));
            }

            return chain.proceed(authorisedRequest.build());
        }
    };

    /**
     * @param baseUrl The base URL for the server, for example http://localhost:9984 - the default
     */
    public BigchaindbConnection(String baseUrl) {
        this(baseUrl, null, null);
    }

    private OkHttpClient buildDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .build();
    }

    /**
     * @param baseUrl The base URL for the server, for example http://localhost:9984 - the default
     * @param httpClient OkHttpClient to use for the HTTP communications
     * @param tokens Optional headers to pass to each request.
     */
    public BigchaindbConnection(String baseUrl, OkHttpClient httpClient,
                                Map<String, String> tokens) {
        this.baseUrl = baseUrl + "/api" + API_VERSION;
        this.tokens = tokens;
        this.httpClient = httpClient;

        if (this.httpClient == null) {
            this.httpClient = buildDefaultHttpClient();
        }
    }

    /**
     * Submit a transaction to the Federation. The method is async and result should be handled
     * via the callback.
     * @param transaction the transaction to be sent to the Federation node(s).
     * @param callback The results callback
     */
    public void send(BigchaindbTransaction transaction, final TransactionCallback callback) {
        RequestBody body = RequestBody.create(JSON, transaction.toString());

        Request request = new Request.Builder()
                .url(baseUrl+TRANSACTIONS_ENDPOINT)
                .post(body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.otherError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 202) {
                    callback.pushedSuccessfully();
                }
                else if (response.code() == 400) {
                    callback.transactionMalformed();
                }
                else {
                    callback.otherError();
                }
            }
        });
    }

    /**
     * This endpoint returns a transaction if it was included in a VALID block. All instances of a transaction in
     * invalid/undecided blocks or the backlog are ignored and treated as if they don’t exist. If a request is
     * made for a transaction and instances of that transaction are found only in invalid/undecided blocks or
     * the backlog, then the response will be null.
     * @param id transaction ID (hex string)
     * @return Transaction if exists or null if not found
     */
    public BigchaindbTransaction getTransactionById(String id) {
        Request request = new Request.Builder()
                .url(baseUrl+TRANSACTIONS_ENDPOINT+"/"+id)
                .get()
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            return BigchaindbTransaction.createFromJson(new JSONObject(response.body().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
