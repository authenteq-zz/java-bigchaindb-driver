package com.authenteq.api;

import java.io.IOException;
import java.security.KeyPair;
import java.util.List;

import com.authenteq.AbstractTest;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.constants.BlockStatus;
import com.authenteq.constants.Operations;
import com.authenteq.model.Status;
import com.authenteq.model.StatusCode;
import com.authenteq.model.Transaction;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.junit.BeforeClass;
import org.junit.Test;

import com.authenteq.builders.BigchainDbConfigBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			ObjectDummy dummyAsset = new ObjectDummy();
			dummyAsset.setId("id");
			dummyAsset.setDescription("asset");

			Transaction transaction = BigchainDbTransactionBuilder.init()
			                                                      .addAssets(dummyAsset, AbstractApiTest.ObjectDummy.class )
			                                                      .operation(Operations.CREATE)
			                                                      .buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
			                                                      .sendTransaction();
			assertNotNull( transaction.getId() );
			Status txStatus = getStatus( transaction );
			assertEquals( StatusCode.VALID, txStatus.getStatus() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = StatusException.class)
	public void failWhenBlockStatusNotFound() throws IOException, StatusException {
		StatusesApi.getBlockStatus("437ce30de5cf1c3ad199fa983aded47d0db43567befa92e3a36b38a5784e4d3a").getStatus();
	}

	@Test
	public void getBlockStatusSuccessfully() throws IOException, StatusException {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			ObjectDummy dummyAsset = new ObjectDummy();
			dummyAsset.setId("id");
			dummyAsset.setDescription("asset");

			Transaction transaction = BigchainDbTransactionBuilder.init()
			                                                      .addAssets(dummyAsset, AbstractApiTest.ObjectDummy.class )
			                                                      .operation(Operations.CREATE)
			                                                      .buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
			                                                      .sendTransaction();
			assertNotNull( transaction.getId() );
			Status txStatus = getStatus( transaction );
			assertEquals( StatusCode.VALID, txStatus.getStatus() );

			List<String> blocks = BlocksApi.getBlocks( transaction.getId(), BlockStatus.VALID );
			assertEquals( blocks.size(), 1 );
			StatusCode bStatus = StatusesApi.getBlockStatus(blocks.get( 0 )).getStatus();
			assertThat( bStatus, is( StatusCode.VALID ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
