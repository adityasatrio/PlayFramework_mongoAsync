package mongo;

import com.mongodb.async.client.MongoCollection;
import org.bson.Document;

/**
 * Created by satrioaditya on 24/02/17.
 */
public interface MongoAsync {
    MongoCollection<Document> get();
}
