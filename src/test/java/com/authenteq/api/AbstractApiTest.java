package com.authenteq.api;

import com.authenteq.AbstractTest;
import com.authenteq.builders.BigchainDbConfigBuilder;
import com.authenteq.model.DataModel;
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


    public class ObjectDummy extends DataModel
    {
        private String id;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
