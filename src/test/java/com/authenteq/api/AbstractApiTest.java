package com.authenteq.api;

import com.authenteq.AbstractTest;
import com.authenteq.builders.BigchainDbConfigBuilder;
import org.junit.BeforeClass;

public class AbstractApiTest extends AbstractTest{

    /**
     * Inits the.
     */
    @BeforeClass
    public static void init() {
        BigchainDbConfigBuilder
                .baseUrl(get( "test.api.url", "https://test.ipdb.io" ))
                .addToken("app_id", get("test.app.id", ""))
                .addToken("app_key", get("test.app.key", ""))
                .setup();
    }

}
