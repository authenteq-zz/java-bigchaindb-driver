package com.authenteq.util;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;

public class KeyPairUtilsTest {

    private KeyPair generatedKeyPair;

    /**
     * Inits the.
     */
    @Before
    public void init() {
        generatedKeyPair = KeyPairUtils.generateNewKeyPair();
    }

    @Test
    public void testBytesEncoding() {
        byte[] encodedKey = KeyPairUtils.encodePrivateKey(generatedKeyPair);
        KeyPair decodedKeyPair = KeyPairUtils.decodeKeyPair(encodedKey);
        Assert.assertArrayEquals(generatedKeyPair.getPrivate().getEncoded(),
                decodedKeyPair.getPrivate().getEncoded());
    }

    @Test
    public void testBase64Encoding() {
        String encodedKey = KeyPairUtils.encodePrivateKeyBase64(generatedKeyPair);
        KeyPair decodedKeyPair = KeyPairUtils.decodeKeyPair(encodedKey);
        Assert.assertArrayEquals(generatedKeyPair.getPrivate().getEncoded(),
                decodedKeyPair.getPrivate().getEncoded());
    }
}
