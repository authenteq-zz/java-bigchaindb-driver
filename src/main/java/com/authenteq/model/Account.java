package com.authenteq.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;


/**
 * The Class Account.
 */
public class Account {

	/** The public key. */
	private PublicKey publicKey;
	
	/** The private key. */
	private PrivateKey privateKey;

	/**
	 * Gets the public key.
	 *
	 * @return the public key
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Sets the public key.
	 *
	 * @param publicKey the new public key
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * Gets the private key.
	 *
	 * @return the private key
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * Sets the private key.
	 *
	 * @param privateKey the new private key
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * Private key from hex.
	 *
	 * @param hex the hex
	 * @return the private key
	 * @throws InvalidKeySpecException the invalid key spec exception
	 */
	public static PrivateKey privateKeyFromHex(String hex) throws InvalidKeySpecException {

		final PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Utils.hexToBytes(hex));
		final PrivateKey privKey = new EdDSAPrivateKey(encoded);

		return privKey;

	}

	/**
	 * Public key from hex.
	 *
	 * @param hex the hex
	 * @return the public key
	 * @throws InvalidKeySpecException the invalid key spec exception
	 */
	public static PublicKey publicKeyFromHex(String hex) throws InvalidKeySpecException {
		final X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Utils.hexToBytes(hex));
		final PublicKey pubKey = new EdDSAPublicKey(pubKeySpec);

		return pubKey;
	}

}
