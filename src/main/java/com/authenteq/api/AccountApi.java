package com.authenteq.api;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.authenteq.model.Account;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;
import org.slf4j.LoggerFactory;

/**
 * The Class AccountApi.
 */
public class AccountApi extends AbstractApi {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger( AccountApi.class );

	/**
	 * Creates the account.
	 *
	 * @return the account
	 */
	public static Account createAccount() {
		log.debug( "createAccount Call" );
		Account newAccount = new Account();
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());
		log.debug( "createAccount Call : " + newAccount.getPublicKey().toString() );
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
		log.debug( "loadAccount Call" );
		Account newAccount = new Account();

		final X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Utils.hexToBytes(publicKey));
		final PublicKey pubKey = new EdDSAPublicKey(pubKeySpec);

		final PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Utils.hexToBytes(privateKey));
		final PrivateKey privKey = new EdDSAPrivateKey(encoded);

		KeyPair keyPair = new KeyPair(pubKey, privKey);
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());
		log.debug( "loadAccount Call : " + newAccount.getPublicKey().toString() );
		return newAccount;
	}

}
