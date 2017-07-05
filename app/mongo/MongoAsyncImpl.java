package mongo;

import com.mongodb.async.client.MongoCollection;
import org.bson.Document;

/**
 * Created by satrioaditya on 24/02/17.
 */
public class MongoAsyncImpl implements MongoAsync {
    private MongoCollection<Document> collection;


    public MongoAsyncImpl(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public MongoCollection<Document> get() {
        return collection;
    }
}
