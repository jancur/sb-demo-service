/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class UnitTestApplicationConfig extends AbstractMongoConfiguration {

    @Override
    protected final String getDatabaseName() {
        String dbName = "my_test_db";
        return dbName;
    }

    @Override
    public final Mongo mongo() throws Exception {
        MongoClient client = new Fongo(getDatabaseName()).getMongo();
        return client;
    }
}
