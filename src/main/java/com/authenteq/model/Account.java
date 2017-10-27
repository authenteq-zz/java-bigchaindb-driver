package com.authenteq.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;

public class Account {

	private PublicKey publicKey;
	private PrivateKey privateKey;

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public static PrivateKey privateKeyFromHex(String hex) throws InvalidKeySpecException {

		final PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Utils.hexToBytes(hex));
		final PrivateKey privKey = new EdDSAPrivateKey(encoded);

		return privKey;

	}

	public static PublicKey publicKeyFromHex(String hex) throws InvalidKeySpecException {
		final X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Utils.hexToBytes(hex));
		final PublicKey pubKey = new EdDSAPublicKey(pubKeySpec);

		return pubKey;
	}

}
