package com.authenteq.api;

import java.io.IOException;

import com.authenteq.AbstractTest;
import com.authenteq.model.StatusCode;
import org.junit.BeforeClass;
import org.junit.Test;

import com.authenteq.builders.BigchainDbConfigBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * The Class StatusesApiTest.
 */
public class StatusesApiTest extends AbstractApiTest
{

	@Test(expected = StatusException.class)
	public void failWhenTransactionStatusNotFound() throws IOException, StatusException {
		StatusesApi.getTransactionStatus("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a").getStatus();
	}

	@Test
	public void getTransactionStatusSuccessfully() throws IOException, StatusException {
		StatusCode status = StatusesApi.getTransactionStatus("829752491efa070476431d7cb77ddd53eeb7916e8f01ef5bd7580bf731f799e3").getStatus();

		assertThat(status.statusCode(), is("valid"));
	}

	@Test(expected = StatusException.class)
	public void failWhenBlockStatusNotFound() throws IOException, StatusException {
		StatusesApi.getBlockStatus("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a").getStatus();
	}

	@Test
	public void getBlockStatusSuccessfully() throws IOException, StatusException {
		StatusCode status = StatusesApi.getBlockStatus("1929586867cb7d531d9de001de0370ecfab72c84f5a157cc51fa288445cb5605").getStatus();

		assertThat(status.statusCode(), is("valid"));
	}
}
