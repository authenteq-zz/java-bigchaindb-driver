package com.authenteq.api;

import com.authenteq.AbstractTest;
import com.authenteq.builders.BigchainDbTransactionBuilder;
import com.authenteq.constants.Operations;
import com.authenteq.model.Account;
import com.authenteq.model.DataModel;
import com.authenteq.model.GenericCallback;
import com.authenteq.model.Transaction;
import com.authenteq.util.JsonUtils;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.assertNotNull;

/**
 * The Class BigchaindbTransactionTest.
 */
public class TransactionApiTest extends AbstractApiTest
{

	private static final String publicKey = "302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373";
	private static final String privateKey = "302e020100300506032b6570042204206f6b0cd095f1e83fc5f08bffb79c7c8a30e77a3ab65f4bc659026b76394fcea8";

	/**
	 * Test post transaction using builder.
	 * @throws InvalidKeySpecException 
	 */
	@Test
	public void testPostTransactionUsingBuilder() throws InvalidKeySpecException {
		
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		Account account = null;
		try {
			
			account = AccountApi.loadAccount(publicKey, privateKey);
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ObjectDummy dummyAsset = new ObjectDummy();
		dummyAsset.setId("id");
		dummyAsset.setDescription("asset");

		try {
			
			Transaction transaction = BigchainDbTransactionBuilder.init().addAsset("middlename", "mname")
					.addAsset("firstname", "John").addAsset("giddlename", "mname").addAsset("ziddlename", "mname")
					.addAsset("lastname", "Smith").addMetaData("what", "My first BigchainDB transaction")
					.addAsset("aa", JsonUtils.toJson(dummyAsset))
					.addMetaData("asa",JsonUtils.toJson(dummyAsset))
					.operation(Operations.CREATE)
					.buildAndSign((EdDSAPublicKey)Account.publicKeyFromHex(publicKey), (EdDSAPrivateKey)Account.privateKeyFromHex(privateKey))
					.sendTransaction();

			assertNotNull(transaction.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPostTransactionOfObjectUsingBuilder() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			ObjectDummy dummyAsset = new ObjectDummy();
			dummyAsset.setId("id");
			dummyAsset.setDescription("asset");
			System.out.println(dummyAsset.toMapString());

			ObjectDummy dummyMeta = new ObjectDummy();
			dummyMeta.setId("id");
			dummyMeta.setDescription("meta");

			Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(dummyAsset).addMetaData(dummyMeta)
					.operation(Operations.CREATE)
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction();
			assertNotNull(transaction.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test post transaction using builder with call back.
	 */
	@Test
	public void testPostTransactionUsingBuilderWithCallBack() {
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		try {
			BigchainDbTransactionBuilder.init().addAsset("firstname", "alvin").addMetaData("what", "bigchaintrans")
					.operation(Operations.CREATE)
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction(new GenericCallback() {

						@Override
						public void transactionMalformed(Response response) {
							// System.out.println(response.message());
							System.out.println("malformed " + response.message());
						}

						@Override
						public void pushedSuccessfully(Response response) {
							System.out.println("pushedSuccessfully");
						}

						@Override
						public void otherError(Response response) {
							System.out.println("otherError");

						}
					});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test transaction by asset id create.
	 */
	@Test
	public void testTransactionByAssetIdCreate() {
		try {
			net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
			KeyPair keyPair = edDsaKpg.generateKeyPair();
			
			ObjectDummy dummyAsset = new ObjectDummy();
			dummyAsset.setId("id");
			dummyAsset.setDescription("asset");
			System.out.println(dummyAsset.toMapString());

			ObjectDummy dummyMeta = new ObjectDummy();
			dummyMeta.setId("id");
			dummyMeta.setDescription("meta");
			
			Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(dummyAsset).addMetaData(dummyMeta)
					.operation(Operations.CREATE)
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction();
			
			System.out.println(TransactionsApi
					.getTransactionsByAssetId(transaction.getId(),
							Operations.CREATE)
					.getTransactions().size());

			TransactionsApi
					.getTransactionsByAssetId(transaction.getId(),
							Operations.CREATE)
					.getTransactions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class ObjectDummy extends DataModel {
		private String id;
		private String description;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
}
