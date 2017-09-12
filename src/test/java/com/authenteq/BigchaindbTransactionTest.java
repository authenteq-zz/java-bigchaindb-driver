package com.authenteq;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.interledger.cryptoconditions.types.Ed25519Sha256Condition;
import org.interledger.cryptoconditions.types.Ed25519Sha256Fulfillment;
import org.json.JSONObject;
import org.junit.Test;

import java.security.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BigchaindbTransactionTest {
    static final String SHOULD_BE_FULFILMENT = "pGSAIOJUaCNTxPOZO2g7x0h6cFHt4LmgrN1LNGXh9q7IDOKxgUDSvX-fMwu6b-VdHQj9plPncX-XiS-VIgBWHPd13hNlB3G-C6grKqzHYGjEGvcJ_fcfD9wy-QHwN4hEfyvebkAM";

    @Test
    public void transactionGenerationTest() throws Exception {
        JSONObject data = new JSONObject();
        data.put("expiration", "NLpPB8MpOkQLZJuyn4rXacdQBXOt4OAwQUSAEpipi2Y=\\n");
        data.put("lat", "DP\\/p9q4D7L0IBZr53Dh98N1huD5BGG\\/nZ9zs\\/ydEUzc=\\n");
        data.put("lon", "ZPleIXiR3W4RWzjrdXcqXDYUjPGGQn6JKmMF5OH7T6U=\\n");
        data.put("firstname", "NLpPB8MpOkQLZJuyn4rXacdQBXOt4OAwQUSAEpipi2Y=\\n");
        data.put("lastname", "N4iDURp+thKsn1Mn7csSoU63QJnJxqyz+VNOPUikMMk=\\n");
        data.put("dob", "ZBJhOnJgC\\/E\\/iD2eyh15qWqD3jsyj+k9+2XIDJXvhEE=\\n");
        data.put("sex", "lg52\\/gnwsTWdwUW4teyj4SGQOF7y5C435on9HtW3DwI=\\n");
        data.put("nationality", "MjpcaVsVoR+5E5ov4Z2gAal1cUfYLUypL52nFqx7pyM=\\n");
        data.put("idNumber", "hsDKi81fiXWuNHoZrzezzTMHykjDIrAtiPozzPTkkbM=\\n");

        KeyPair keyPair = retrieveKeyPair();

        BigchaindbTransaction bigchaindbTransaction = new BigchaindbTransaction(
                data, null, (EdDSAPublicKey) keyPair.getPublic()
        );
        assertFalse(bigchaindbTransaction.isSigned());
        bigchaindbTransaction.signTransaction((EdDSAPrivateKey) keyPair.getPrivate());
        assertTrue(bigchaindbTransaction.isSigned());

        Ed25519Sha256Condition condition1 = new Ed25519Sha256Condition(
                (EdDSAPublicKey) keyPair.getPublic());
        Signature edDsaSigner = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));

        JSONObject rootObject = new JSONObject(bigchaindbTransaction.getTransactionJson().toString());
        String fulfilmentVal = rootObject.getJSONArray("inputs").getJSONObject(0).getString("fulfillment");
        rootObject.getJSONArray("inputs").getJSONObject(0).put("fulfillment", JSONObject.NULL);
        edDsaSigner.initSign(keyPair.getPrivate());
        edDsaSigner.update(rootObject.toString().getBytes());
        byte[] signature = edDsaSigner.sign();
        Ed25519Sha256Fulfillment fulfillment
                = new Ed25519Sha256Fulfillment((EdDSAPublicKey) keyPair.getPublic(), signature);
        assertEquals("4eff006ca203061d6bc1100959018f008c0f61a4b53c5d8a333159adf69a7c46", rootObject.getString("id"));
        assertEquals(SHOULD_BE_FULFILMENT, fulfilmentVal);

        assertTrue(fulfillment.verify(condition1, rootObject.toString().getBytes()));
//        return rootObject.toString();
    }

    private KeyPair retrieveKeyPair() {
        Security.addProvider(new BouncyCastleProvider());

        byte[] seedEncoded = Base64.decodeBase64("pK0Jep29SGSDx/AUdFBosyVtEU7EoTG1+Bq74Dnh6HQ");
        EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
        EdDSAPrivateKeySpec privKeySpec = new EdDSAPrivateKeySpec(seedEncoded, keySpecs);
        EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privKeySpec.getA(), keySpecs);
        return new KeyPair(new EdDSAPublicKey(pubKeySpec), new EdDSAPrivateKey(privKeySpec));
    }
}
