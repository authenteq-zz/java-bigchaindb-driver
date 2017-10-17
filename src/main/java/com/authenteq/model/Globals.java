package com.authenteq.model;

import java.util.Map;

import okhttp3.OkHttpClient;


/**
 * The Class Globals.
 */
public class Globals {

	/** The authorization tokens. */
	private static Map<String, String> authorizationTokens;
	
	/** The base url. */
	private static String baseUrl;
	
	/** The http client. */
	private static OkHttpClient httpClient;

	/**
	 * Gets the authorization tokens.
	 *
	 * @return the authorization tokens
	 */
	public static Map<String, String> getAuthorizationTokens() {
		return authorizationTokens;
	}

	/**
	 * Sets the authorization tokens.
	 *
	 * @param authorizationTokens the authorization tokens
	 */
	public static void setAuthorizationTokens(Map<String, String> authorizationTokens) {
		Globals.authorizationTokens = authorizationTokens;
	}

	/**
	 * Gets the base url.
	 *
	 * @return the base url
	 */
	public static String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the base url.
	 *
	 * @param baseUrl the new base url
	 */
	public static void setBaseUrl(String baseUrl) {
		Globals.baseUrl = baseUrl;
	}

	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public static OkHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * Sets the http client.
	 *
	 * @param httpClient the new http client
	 */
	public static void setHttpClient(OkHttpClient httpClient) {
		Globals.httpClient = httpClient;
	}

}
