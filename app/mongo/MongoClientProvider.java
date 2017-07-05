package mongo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

/**
 * Created by satrioaditya on 24/02/17.
 */
@Singleton
public class MongoClientProvider implements Provider<MongoAsync> {

    private Logger logger = LoggerFactory.getLogger(MongoClientProvider.class);
    private Configuration configuration;

    @Inject
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public MongoAsync get() {
        logger.info("initiliaze mongo async driver . . .");
        String mongoDbUri = configuration.getString("mongodburi");
        String mongodDatabase = configuration.getString("mongodb.database");
        String mongoCollection = configuration.getString("mongodb.collection");
        MongoClient mongoClient = MongoClients.create(new ConnectionString(mongoDbUri));

        //check builder method for available configuration
        //MongoClientSettings settings = MongoClientSettings.builder().*
        //MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongodDatabase);
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        logger.info("sucessfully connected mongo async driver . . ." + mongoDbUri + " # " + mongodDatabase + " # " + mongoCollection);

        SingleResultCallback<Void> callbackWhenFinished = (result, throwable) -> System.out.println("Operation Finished!");

        mongoClient.listDatabaseNames().forEach(new Block<String>() {
            @Override
            public void apply(final String databaseName) {
                logger.info(" mongoClient # listDatabaseNames = " + databaseName);
            }
        }, callbackWhenFinished);


        MongoAsync mongoAsync = new MongoAsyncImpl(collection);
        return mongoAsync;

    }
}
