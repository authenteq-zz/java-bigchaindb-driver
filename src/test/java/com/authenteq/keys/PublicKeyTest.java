package com.authenteq.keys;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.spec.X509EncodedKeySpec;

import org.junit.Test;

import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class PublicKeyTest {
	 /**
     * The example public key MCowBQYDK2VwAyEAGb9ECWmEzf6FQbrBZ9w7lshQhqowtrbLDFw4rXAxZuE=
     * from https://tools.ietf.org/html/draft-ietf-curdle-pkix-04#section-10.1
     */
    static final byte[] TEST_PUBKEY = Utils.hexToBytes("302a300506032b657003210019bf44096984cdfe8541bac167dc3b96c85086aa30b6b6cb0c5c38ad703166e1");

    static final byte[] TEST_PUBKEY_NULL_PARAMS = Utils.hexToBytes("302c300706032b6570050003210019bf44096984cdfe8541bac167dc3b96c85086aa30b6b6cb0c5c38ad703166e1");
    static final byte[] TEST_PUBKEY_OLD = Utils.hexToBytes("302d300806032b65640a010103210019bf44096984cdfe8541bac167dc3b96c85086aa30b6b6cb0c5c38ad703166e1");

    @Test
    public void testDecodeAndEncode() throws Exception {
        // Decode
        X509EncodedKeySpec encoded = new X509EncodedKeySpec(TEST_PUBKEY);
        EdDSAPublicKey keyIn = new EdDSAPublicKey(encoded);

        // Encode
        EdDSAPublicKeySpec decoded = new EdDSAPublicKeySpec(
                keyIn.getA(),
                keyIn.getParams());
        EdDSAPublicKey keyOut = new EdDSAPublicKey(decoded);

        // Check
        assertThat(keyOut.getEncoded(), is(equalTo(TEST_PUBKEY)));
    }

    @Test
    public void testDecodeWithNullAndEncode() throws Exception {
        // Decode
        X509EncodedKeySpec encoded = new X509EncodedKeySpec(TEST_PUBKEY_NULL_PARAMS);
        EdDSAPublicKey keyIn = new EdDSAPublicKey(encoded);

        // Encode
        EdDSAPublicKeySpec decoded = new EdDSAPublicKeySpec(
                keyIn.getA(),
                keyIn.getParams());
        EdDSAPublicKey keyOut = new EdDSAPublicKey(decoded);

        // Check
        assertThat(keyOut.getEncoded(), is(equalTo(TEST_PUBKEY)));
    }

    @Test
    public void testReEncodeOldEncoding() throws Exception {
        // Decode
        X509EncodedKeySpec encoded = new X509EncodedKeySpec(TEST_PUBKEY_OLD);
        EdDSAPublicKey keyIn = new EdDSAPublicKey(encoded);

        // Encode
        EdDSAPublicKeySpec decoded = new EdDSAPublicKeySpec(
                keyIn.getA(),
                keyIn.getParams());
        EdDSAPublicKey keyOut = new EdDSAPublicKey(decoded);

        // Check
        assertThat(keyOut.getEncoded(), is(equalTo(TEST_PUBKEY)));
    }
}
