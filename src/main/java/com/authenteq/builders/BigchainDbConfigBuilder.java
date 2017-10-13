package com.authenteq.builders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.authenteq.constants.BigchainDbApi;
import com.authenteq.model.Globals;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO: Auto-generated Javadoc
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
	 * @param baseUrl the base url
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
		 * @param key the key
		 * @param map the map
		 * @return the i tokens
		 */
		ITokens addToken(String key, String map);
		
		/**
		 * Setup.
		 */
		void setup();
	}

	/**
	 * The Class Builder.
	 */
	private static class Builder implements ITokens {
		
		/** The baser url. */
		String baserUrl;
		
		/** The tokens. */
		Map<String, String> tokens = new HashMap<String,String>();
		
		/** The http client. */
		OkHttpClient httpClient;

		/**
		 * Instantiates a new builder.
		 *
		 * @param baseUrl the base url
		 */
		public Builder(String baseUrl) {
			this.baserUrl = baseUrl;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbConfigBuilder.ITokens#addToken(java.lang.String, java.lang.String)
		 */
		@Override
		public ITokens addToken(String key, String value) {
			tokens.put(key, value);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.authenteq.builders.BigchainDbConfigBuilder.ITokens#setup()
		 */
		@Override
		public void setup() {
			Globals.setAuthorizationTokens(tokens);
			Globals.setBaseUrl(this.baserUrl + "/api" + BigchainDbApi.API_VERSION);

			if (this.httpClient == null) {
				Globals.setHttpClient(buildDefaultHttpClient());
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
	}
}
