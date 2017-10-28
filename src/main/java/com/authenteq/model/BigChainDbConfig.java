package com.authenteq.model;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;


/**
 * The Class BigChainDbConfig.
 */
public class BigChainDbConfig {

	/** The baser url. */
	private String baserUrl;

	/** The tokens. */
	private Map<String, String> tokens = new HashMap<String, String>();

	/** The http client. */
	private OkHttpClient httpClient;

	/**
	 * Gets the baser url.
	 *
	 * @return the baser url
	 */
	public String getBaserUrl() {
		return baserUrl;
	}

	/**
	 * Sets the baser url.
	 *
	 * @param baserUrl the new baser url
	 */
	public void setBaserUrl(String baserUrl) {
		this.baserUrl = baserUrl;
	}

	/**
	 * Gets the tokens.
	 *
	 * @return the tokens
	 */
	public Map<String, String> getTokens() {
		return tokens;
	}

	/**
	 * Sets the tokens.
	 *
	 * @param tokens the tokens
	 */
	public void setTokens(Map<String, String> tokens) {
		this.tokens = tokens;
	}

	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public OkHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * Sets the http client.
	 *
	 * @param httpClient the new http client
	 */
	public void setHttpClient(OkHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	
	
}
