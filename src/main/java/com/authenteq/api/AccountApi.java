package com.authenteq.api;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;

import com.authenteq.model.Account;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;

/**
 * The Class AccountApi.
 */
public class AccountApi extends AbstractApi {
	
	
	private static final Logger LOGGER = Logger.getLogger(AccountApi.class.getName());
	/**
	 * Creates the account.
	 *
	 * @return the account
	 */
	public static Account createAccount() {
		LOGGER.info("createAccount Call");
		Account newAccount = new Account();
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());
		LOGGER.info("createAccount Call : " + newAccount.getPublicKey().toString());
		return newAccount;
	}

	/**
	 * Load account.
	 *
	 * @param publicKey
	 *            the public key
	 * @param privateKey
	 *            the private key
	 * @return the account
	 * @throws InvalidKeySpecException
	 *             the invalid key spec exception
	 */
	public static Account loadAccount(String publicKey, String privateKey) throws InvalidKeySpecException {
		LOGGER.info("loadAccount Call");
		Account newAccount = new Account();

		final X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Utils.hexToBytes(publicKey));
		final PublicKey pubKey = new EdDSAPublicKey(pubKeySpec);

		final PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Utils.hexToBytes(privateKey));
		final PrivateKey privKey = new EdDSAPrivateKey(encoded);

		KeyPair keyPair = new KeyPair(pubKey, privKey);
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());
		LOGGER.info("loadAccount Call : " + newAccount.getPublicKey().toString());
		return newAccount;
	}

}
