/*
 * (C) Copyright 2017 Authenteq (https://authenteq.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Bohdan Bezpartochnyi <bohdan@authenteq.com>
 */

package com.authenteq;

import com.authenteq.model.TransactionModel;
import com.authenteq.util.DriverUtils;
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


/**
 * The Class BigchaindbTransactionTest.
 */
public class BigchaindbTransactionTest {
    
    /** The Constant SHOULD_BE_FULFILMENT. */
    static final String SHOULD_BE_FULFILMENT = "pGSAIOJUaCNTxPOZO2g7x0h6cFHt4LmgrN1LNGXh9q7IDOKxgUDSvX-fMwu6b-VdHQj9plPncX-XiS-VIgBWHPd13hNlB3G-C6grKqzHYGjEGvcJ_fcfD9wy-QHwN4hEfyvebkAM";
    
    /** The Constant JSON_REPR_SIGNED. */
    static final String JSON_REPR_SIGNED = "{\n" +
            "  \"asset\": {\n" +
            "    \"data\": {\n" +
            "      \"msg\": \"Hello BigchainDB!\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"id\": \"8b20dbe164badd5ca0611b0e233aef9acce609fbca20f787fc7d926f300d0102\",\n" +
            "  \"inputs\": [\n" +
            "    {\n" +
            "      \"fulfillment\": \"pGSAIDE5i63cn4X8T8N1sZ2mGkJD5lNRnBM4PZgI_zvzbr-cgUCGvCc2HO2uB4IKix6INRzGIM10r7VsKFMPM9cT7uVJ1xFLOJ9bn6UioepBMLIrrwTlk2CkTolIPonf7BnzriQL\",\n" +
            "      \"fulfills\": null,\n" +
            "      \"owners_before\": [\n" +
            "        \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"metadata\": {\n" +
            "    \"sequence\": 0\n" +
            "  },\n" +
            "  \"operation\": \"CREATE\",\n" +
            "  \"outputs\": [\n" +
            "    {\n" +
            "      \"amount\": \"1\",\n" +
            "      \"condition\": {\n" +
            "        \"details\": {\n" +
            "          \"public_key\": \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\",\n" +
            "          \"type\": \"ed25519-sha-256\"\n" +
            "        },\n" +
            "        \"uri\": \"ni:///sha-256;PNYwdxaRaNw60N6LDFzOWO97b8tJeragczakL8PrAPc?fpt=ed25519-sha-256&cost=131072\"\n" +
            "      },\n" +
            "      \"public_keys\": [\n" +
            "        \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"version\": \"1.0\"\n" +
            "}";

    /** The Constant JSON_REPR_UNSIGNED. */
    static final String JSON_REPR_UNSIGNED = "{\n" +
            "  \"asset\": {\n" +
            "    \"data\": {\n" +
            "      \"msg\": \"Hello BigchainDB!\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"id\": \"8b20dbe164badd5ca0611b0e233aef9acce609fbca20f787fc7d926f300d0102\",\n" +
            "  \"inputs\": [\n" +
            "    {\n" +
            "      \"fulfillment\": null,\n" +
            "      \"fulfills\": null,\n" +
            "      \"owners_before\": [\n" +
            "        \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"metadata\": {\n" +
            "    \"sequence\": 0\n" +
            "  },\n" +
            "  \"operation\": \"CREATE\",\n" +
            "  \"outputs\": [\n" +
            "    {\n" +
            "      \"amount\": \"1\",\n" +
            "      \"condition\": {\n" +
            "        \"details\": {\n" +
            "          \"public_key\": \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\",\n" +
            "          \"type\": \"ed25519-sha-256\"\n" +
            "        },\n" +
            "        \"uri\": \"ni:///sha-256;PNYwdxaRaNw60N6LDFzOWO97b8tJeragczakL8PrAPc?fpt=ed25519-sha-256&cost=131072\"\n" +
            "      },\n" +
            "      \"public_keys\": [\n" +
            "        \"4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"version\": \"1.0\"\n" +
            "}";

    /** The Constant SHOULD_BE_PUBLIC_KEY. */
    static final String SHOULD_BE_PUBLIC_KEY = "4K9sWUMFwTgaDGPfdynrbxWqWS6sWmKbZoTjxLtVUibD";

    /**
     * Transaction generation test.
     *
     * @throws Exception the exception
     */
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

        TransactionModel bigchaindbTransaction = new TransactionModel(
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
        assertEquals("4eff006ca203061d6bc1100959018f008c0f61a4b53c5d8a333159adf69a7c46", bigchaindbTransaction.getTransactionId());
        assertEquals(SHOULD_BE_FULFILMENT, fulfilmentVal);

        assertTrue(fulfillment.verify(condition1, rootObject.toString().getBytes()));
//        return rootObject.toString();
    }

    /**
     * Transaction from json.
     *
     * @throws Exception the exception
     */
    @Test
    public void transactionFromJson() throws Exception {
        TransactionModel transactionSigned
                = TransactionModel.createFromJson(new JSONObject(JSON_REPR_SIGNED));
        TransactionModel transactionUnsigned
                = TransactionModel.createFromJson(new JSONObject(JSON_REPR_UNSIGNED));

        String publicEncoded = DriverUtils.convertToBase58(transactionSigned.getPublicKey());
        assertEquals(SHOULD_BE_PUBLIC_KEY, publicEncoded);
        assertTrue(transactionSigned.isSigned());
        assertFalse(transactionUnsigned.isSigned());
    }

    /**
     * Retrieve key pair.
     *
     * @return the key pair
     */
    private KeyPair retrieveKeyPair() {
        Security.addProvider(new BouncyCastleProvider());

        byte[] seedEncoded = Base64.decodeBase64("pK0Jep29SGSDx/AUdFBosyVtEU7EoTG1+Bq74Dnh6HQ");
        EdDSAParameterSpec keySpecs = EdDSANamedCurveTable.getByName("Ed25519");
        EdDSAPrivateKeySpec privKeySpec = new EdDSAPrivateKeySpec(seedEncoded, keySpecs);
        EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privKeySpec.getA(), keySpecs);
        return new KeyPair(new EdDSAPublicKey(pubKeySpec), new EdDSAPrivateKey(privKeySpec));
    }
}
