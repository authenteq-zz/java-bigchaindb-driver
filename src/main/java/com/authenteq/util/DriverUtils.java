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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class DriverUtils.
 */
public class DriverUtils {

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
	 * To conform with BigchainDB serialization
	 *
	 * @param input the json string to sort the properties for
	 *
	 * @return the json object
	 */
	public static JsonObject makeSelfSortingGson( String input )
    {
        if( input == null )
            return null;

        JsonParser jsonParser = new JsonParser();
        return makeSelfSortingGson( jsonParser.parse( input ).getAsJsonObject() );
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
    public static JsonObject makeSelfSortingGson(JsonObject input ) {
        if (input == null)
            return null;

	    JsonObject json = new JsonObject();

        for( String key: input.keySet() ) {
            JsonElement j = input.get(key);
            if (j instanceof JsonObject) {
                json.add(key, makeSelfSortingGson((JsonObject) j));
            } else if (j instanceof JsonArray ) {
                JsonArray h = (JsonArray) j;
                JsonArray oList = new JsonArray();
                for (int i = 0; i < h.size(); i++) {
                    JsonElement joi = h.get( i );
                    if (joi instanceof JsonObject) {
                        oList.add(makeSelfSortingGson((JsonObject) joi));
                        json.add(key, oList);
                    } else {
                        oList.add(joi);
                        json.add(key, oList);
                    }
                }
            } else {
                json.add(key, j);
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
    public static JsonObject getSelfSortingJson() {
        return makeSelfSortingGson(new JsonObject());
    }
}
