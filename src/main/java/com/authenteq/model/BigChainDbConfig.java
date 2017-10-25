package com.authenteq.model;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class BigChainDbConfig {

	/** The baser url. */
	private String baserUrl;

	/** The tokens. */
	private Map<String, String> tokens = new HashMap<String, String>();

	/** The http client. */
	private OkHttpClient httpClient;

	public String getBaserUrl() {
		return baserUrl;
	}

	public void setBaserUrl(String baserUrl) {
		this.baserUrl = baserUrl;
	}

	public Map<String, String> getTokens() {
		return tokens;
	}

	public void setTokens(Map<String, String> tokens) {
		this.tokens = tokens;
	}

	public OkHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(OkHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	
	
}
