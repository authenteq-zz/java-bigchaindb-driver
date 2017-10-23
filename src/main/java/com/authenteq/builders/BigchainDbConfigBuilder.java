package com.authenteq.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.ApiEndpoints;
import com.authenteq.model.Globals;
import com.authenteq.util.JsonUtils;
import com.authenteq.util.NetworkUtils;
import com.authenteq.ws.WsMonitorSessionHandler;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The Class BigchainDbConfigBuilder.
 */
public class BigchainDbConfigBuilder {

	/**
	 * Instantiates a new bigchain db config builder.
	 */
	private BigchainDbConfigBuilder() {
	}

	/**
	 * Base url.
	 *
	 * @param baseUrl
	 *            the base url
	 * @return the i tokens
	 */
	public static ITokens baseUrl(String baseUrl) {
		return new BigchainDbConfigBuilder.Builder(baseUrl);
	}

	/**
	 * The Interface ITokens.
	 */
	public interface ITokens {

		/**
		 * Adds the token.
		 *
		 * @param key
		 *            the key
		 * @param map
		 *            the map
		 * @return the i tokens
		 */
		ITokens addToken(String key, String map);

		ITokens webSocketMonitor();

		void setup();

		/**
		 * Setup.
		 */

	}

	/**
	 * The Class Builder.
	 */
	private static class Builder implements ITokens {

		/** The baser url. */
		String baserUrl;

		/** The tokens. */
		Map<String, String> tokens = new HashMap<String, String>();

		/** The http client. */
		OkHttpClient httpClient;

		boolean setupWsockets = false;

		/**
		 * Instantiates a new builder.
		 *
		 * @param baseUrl
		 *            the base url
		 */
		public Builder(String baseUrl) {
			this.baserUrl = baseUrl;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.authenteq.builders.BigchainDbConfigBuilder.ITokens#addToken(java.
		 * lang.String, java.lang.String)
		 */
		@Override
		public ITokens addToken(String key, String value) {
			tokens.put(key, value);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.authenteq.builders.BigchainDbConfigBuilder.ITokens#setup()
		 */
		@Override
		public void setup() {
			Globals.setAuthorizationTokens(tokens);
			Globals.setBaseUrl(this.baserUrl + "/api" + BigchainDbApi.API_VERSION);
			Globals.setWsSocketUrl(this.baserUrl + "/api" + BigchainDbApi.API_VERSION + BigchainDbApi.STREAMS);

			if (this.httpClient == null) {
				Globals.setHttpClient(buildDefaultHttpClient());
			}

			try {
				Globals.setApiEndpoints(JsonUtils.fromJson(
						NetworkUtils.sendGetRequest(this.baserUrl + "/api" + BigchainDbApi.API_VERSION).body().string(),
						ApiEndpoints.class));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (this.setupWsockets) {
				List<Transport> transports = new ArrayList<Transport>(1);
				transports.add(new WebSocketTransport(new StandardWebSocketClient()));
				WebSocketClient transport = new SockJsClient(transports);
				WebSocketStompClient stompClient = new WebSocketStompClient(transport);
				stompClient.setMessageConverter(new StringMessageConverter());
				StompSessionHandler handler = new WsMonitorSessionHandler();
				stompClient.connect("ws://138.197.169.112:9985/api/v1/streams", handler);
				
			}
		}

		/**
		 * Builds the default http client.
		 *
		 * @return the ok http client
		 */
		private OkHttpClient buildDefaultHttpClient() {
			return new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
					.readTimeout(60, TimeUnit.SECONDS).addInterceptor(authInterceptor).build();
		}

		/** The auth interceptor. */
		private Interceptor authInterceptor = new Interceptor() {
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request originalRequest = chain.request();

				if (tokens == null)
					return chain.proceed(originalRequest);

				// Add authorization header with updated authorization value to
				// intercepted request
				Request.Builder authorisedRequest = originalRequest.newBuilder();

				for (String key : tokens.keySet()) {
					authorisedRequest = authorisedRequest.addHeader(key, tokens.get(key));
				}

				return chain.proceed(authorisedRequest.build());
			}
		};

		@Override
		public ITokens webSocketMonitor() {

			this.setupWsockets = true;

			return this;
		}
	}
}
