package com.authenteq.api;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import com.authenteq.model.Account;
import com.authenteq.util.DriverUtils;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;

/**
 * The Class AccountApi.
 */
public class AccountApi {

	/**
	 * Creates the account.
	 *
	 * @return the account
	 */
	public static Account createAccount() {

		Account newAccount = new Account();
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());

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

		Account newAccount = new Account();

		final X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Utils.hexToBytes(publicKey));
		final PublicKey pubKey = new EdDSAPublicKey(pubKeySpec);

		final PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Utils.hexToBytes(privateKey));
		final PrivateKey privKey = new EdDSAPrivateKey(encoded);

		KeyPair keyPair = new KeyPair(pubKey, privKey);
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());

		return newAccount;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws DecoderException
	 *             the decoder exception
	 * @throws EncoderException
	 *             the encoder exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static void main(String args[])
			throws InterruptedException, DecoderException, EncoderException, UnsupportedEncodingException {
		try {
			Hex hex = new Hex();

			Account acc = AccountApi.createAccount();
			System.out.print(DriverUtils.convertToBase58((EdDSAPublicKey) acc.getPublicKey()));
			System.out.println(
					Utils.bytesToHex(DriverUtils.convertToBase58((EdDSAPublicKey) acc.getPublicKey()).getBytes()));

			System.out.println(Utils.bytesToHex(acc.getPublicKey().getEncoded()));
			System.out.println(Utils.bytesToHex(acc.getPrivateKey().getEncoded()));

			Account acc1 = AccountApi.loadAccount(Utils.bytesToHex(acc.getPublicKey().getEncoded()),
					Utils.bytesToHex(acc.getPrivateKey().getEncoded()));

			System.out.println(Utils.bytesToHex(acc1.getPublicKey().getEncoded()));
			System.out.println(Utils.bytesToHex(acc1.getPrivateKey().getEncoded()));

		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

}