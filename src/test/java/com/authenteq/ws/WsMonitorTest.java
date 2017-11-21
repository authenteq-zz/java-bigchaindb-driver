package com.authenteq.ws;

import com.authenteq.builders.BigchainDbConfigBuilder;

public class WsMonitorTest {

	public static void main(String[] args) {
		BigchainDbConfigBuilder.baseUrl("https://test.ipdb.io")
		.addToken("app_id", "2bbaf3ff")
		.addToken("app_key", "c929b708177dcc8b9d58180082029b8d")
		.webSocketMonitor(new ValidTransactionMessageHandler())
		.setup();
	}
}
