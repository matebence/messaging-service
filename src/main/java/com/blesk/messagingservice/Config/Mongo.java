package com.blesk.messagingservice.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class Mongo extends AbstractMongoClientConfiguration {

    private MongoProperties mongoProperties;

    @Autowired
    public Mongo(MongoProperties mongoProperties){
        this.mongoProperties = mongoProperties;
    }

    @Autowired
    @ConditionalOnExpression("'${mongo.transactions}'=='enabled'")
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoProperties.getDatabase();
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(this.mongoProperties.getUri());
    }
}