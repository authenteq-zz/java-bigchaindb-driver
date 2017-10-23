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

package com.authenteq.util;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;


/**
 * The Class DriverUtils.
 */
public class DriverUtils {
    

    private static final int OID_OLD = 100;
    private static final int OID_ED25519 = 112;
    private static final int OID_BYTE = 11;
    private static final int IDLEN_BYTE = 6;
    
    /** The Constant DIGITS. */
    private static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Gets the hex.
     *
     * @param data the data
     * @return the hex
     */
    public static String getHex(byte[] data) {
        final int l = data.length;
        final char[] outData = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            outData[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            outData[j++] = DIGITS[0x0F & data[i]];
        }

        return new String(outData);
    }

    /**
     * Make self sorting.
     *
     * @param input the input
     * @return the JSON object
     */
    /*
    We are using a hack to make stardard org.json be automatically sorted
    by key desc alphabetically
     */
    public static JSONObject makeSelfSorting(JSONObject input) {
        if (input == null)
            return null;

        JSONObject json = new JSONObject();
        Field map = null;
        try {
            map = json.getClass().getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (map == null) {
            return json;
        }

        map.setAccessible(true);//because the field is private final...
        try {
            map.set(json, new TreeMap<>());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.setAccessible(false);

        Iterator<String> flavoursIter = input.keys();
        while (flavoursIter.hasNext()){
            String key = flavoursIter.next();
            try {
            	Object j = input.get(key);
                if(j instanceof JSONObject) {
                	json.put(key, makeSelfSorting((JSONObject)j));
                }else if(j instanceof JSONArray) {
                	JSONArray h = (JSONArray)j;
                	Iterator<Object> jo = h.iterator();
                	List<Object> oList = new ArrayList<Object>();
                	while(jo.hasNext()) {
                		Object joi = jo.next();
                		if(joi instanceof JSONObject) {
                			oList.add(makeSelfSorting((JSONObject)joi));
                			json.put(key, oList);
                		}else {
                			oList.add((String)joi);
                			json.put(key, oList);
                		}
                	}
                }else {
                	json.put(key, j);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * Gets the self sorting json.
     *
     * @return the self sorting json
     */
    /*
    We need to sort the keys in alphabetical order to sign the transaction successfully.
     */
    public static JSONObject getSelfSortingJson() {
        JSONObject json = makeSelfSorting(new JSONObject());
        return json;
    }

    /**
     * Convert to base 58.
     *
     * @param publicKey the public key
     * @return the string
     */
    public static String convertToBase58(EdDSAPublicKey publicKey) {
        return Base58.encode(Arrays.copyOfRange(publicKey.getEncoded(), 12, 44));
    }
    
    public static String convertToBase58(EdDSAPrivateKey privateKey) {
        return Base58.encode(privateKey.getEncoded());
    }
    

    public static byte[] decode(byte[] d) throws InvalidKeySpecException {
        try {
            //
            // Setup and OID check
            //
            int totlen = 48;
            int idlen = 5;
            int doid = d[OID_BYTE];
            if (doid == OID_OLD) {
                totlen = 49;
                idlen = 8;
            } else if (doid == OID_ED25519) {
                // Detect parameter value of NULL
                if (d[IDLEN_BYTE] == 7) {
                    totlen = 50;
                    idlen = 7;
                }
            } else {
                throw new InvalidKeySpecException("unsupported key spec");
            }

            //
            // Pre-decoding check
            //
            if (d.length != totlen) {
                throw new InvalidKeySpecException("invalid key spec length");
            }

            //
            // Decoding
            //
            int idx = 0;
            if (d[idx++] != 0x30 ||
                d[idx++] != (totlen - 2) ||
                d[idx++] != 0x02 ||
                d[idx++] != 1 ||
                d[idx++] != 0 ||
                d[idx++] != 0x30 ||
                d[idx++] != idlen ||
                d[idx++] != 0x06 ||
                d[idx++] != 3 ||
                d[idx++] != (1 * 40) + 3 ||
                d[idx++] != 101) {
                throw new InvalidKeySpecException("unsupported key spec");
            }
            idx++; // OID, checked above
            // parameters only with old OID
            if (doid == OID_OLD) {
                if (d[idx++] != 0x0a ||
                    d[idx++] != 1 ||
                    d[idx++] != 1) {
                    throw new InvalidKeySpecException("unsupported key spec");
                }
            } else {
                // Handle parameter value of NULL
                //
                // Quote https://tools.ietf.org/html/draft-ietf-curdle-pkix-04 :
                //   For all of the OIDs, the parameters MUST be absent.
                //   Regardless of the defect in the original 1997 syntax,
                //   implementations MUST NOT accept a parameters value of NULL.
                //
                // But Java's default keystore puts it in (when decoding as
                // PKCS8 and then re-encoding to pass on), so we must accept it.
                if (idlen == 7) {
                    if (d[idx++] != 0x05 ||
                        d[idx++] != 0) {
                        throw new InvalidKeySpecException("unsupported key spec");
                    }
                }
                // PrivateKey wrapping the CurvePrivateKey
                if (d[idx++] != 0x04 ||
                    d[idx++] != 34) {
                    throw new InvalidKeySpecException("unsupported key spec");
                }
            }
            if (d[idx++] != 0x04 ||
                d[idx++] != 32) {
                throw new InvalidKeySpecException("unsupported key spec");
            }
            byte[] rv = new byte[32];
            System.arraycopy(d, idx, rv, 0, 32);
            return rv;
        } catch (IndexOutOfBoundsException ioobe) {
            throw new InvalidKeySpecException(ioobe);
        }
    }
}
