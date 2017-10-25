package com.authenteq.api;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.digest.Keccak.KeyGenerator512;
import org.bouncycastle.util.encoders.HexEncoder;

import com.authenteq.model.Account;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;

public class AccountApi {

	public static Account createAccount() {

		Account newAccount = new Account();
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		newAccount.setPrivateKey(keyPair.getPrivate());
		newAccount.setPublicKey(keyPair.getPublic());

		return newAccount;
	}

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

	public static void main(String args[]) throws InterruptedException, DecoderException, EncoderException {
		try {
			Hex hex = new Hex();

			Account acc = AccountApi.createAccount();
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
