package dao;

import com.google.inject.Inject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import entity.Customer;
import mongo.MongoAsync;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import play.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class CustomerDaoImpl implements CustomerDao {
    private MongoAsync mongoAsync;

    @Inject
    public void setMongoAsync(MongoAsync mongoAsync) {
        this.mongoAsync = mongoAsync;
    }

    @Override
    public void save(Customer customer) {
        mongoAsync.get().insertOne(customer.getDocument(), (result, t) -> {
            if (t != null) {
                throw new RuntimeException(t.getMessage(), t.getCause());
            } else {
                Logger.info("data saved");
            }
        });
    }

    @Override
    public Customer update(String id, Customer customer) throws ExecutionException, InterruptedException, IOException {
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);

        Bson filterId = Filters.and(Filters.eq("_id", new ObjectId(id)));

        Bson updateName = Updates.set("name", customer.getName());
        Bson updateAddress = Updates.set("address", customer.getAddress());
        Bson updates = Updates.combine(updateName, updateAddress);

        CompletableFuture<Document> completableFuture = new CompletableFuture<>();
        mongoAsync.get().findOneAndUpdate(filterId, updates, options, (result, t) -> {
            if (t != null) {
                throw new RuntimeException(t.getMessage(), t.getCause());
            } else {
                if (result != null) {
                    completableFuture.complete(result);
                }
                Logger.info("data updated ");
            }
        });

        return new Customer().setDocument(completableFuture.get());
    }

    @Override
    public Boolean delete(String id) throws ExecutionException, InterruptedException {

        Boolean idDeleted = Boolean.FALSE;
        CompletableFuture<DeleteResult> completableFuture = new CompletableFuture<>();
        Bson filterId = Filters.and(Filters.eq("_id", new ObjectId(id)));
        mongoAsync.get().deleteOne(filterId, (result, t) -> {
            if (t == null) {
                if (result != null) {
                    completableFuture.complete(result);
                }
            } else {
                Logger.error(t.getMessage(), t.getCause());
            }
        });

        if (completableFuture.get().getDeletedCount() > 0) {
            idDeleted = Boolean.TRUE;
        }

        return idDeleted;
    }

    @Override
    public Customer findById(String id) throws ExecutionException, InterruptedException, IOException {

        CompletableFuture<ArrayList<Document>> completableFuture = new CompletableFuture<>();
        Bson filterId = Filters.and(Filters.eq("_id", new ObjectId(id)));
        mongoAsync.get().find(filterId).into(new ArrayList<Document>(), (result, t) -> {
            if (t == null) {
                if (result != null) {
                    completableFuture.complete(result);
                }
            } else {
                Logger.error(t.getMessage(), t.getCause());
            }
        });

        Customer customer = new Customer();
        if (completableFuture.get() != null && !completableFuture.get().isEmpty()) {
            customer = new Customer().setDocument(completableFuture.get().get(0));
        }

        return customer;
    }

    @Override
    public List<Customer> all() throws ExecutionException, InterruptedException {

        CompletableFuture<ArrayList<Document>> completableFuture = new CompletableFuture<>();
        mongoAsync.get().find().into(new ArrayList<Document>(), (result, t) -> {
            if (t == null) {
                if (result != null) {
                    completableFuture.complete(result);
                }
            } else {
                Logger.error(t.getMessage(), t.getCause());
            }
        });

        return new Customer().setAllDocument(completableFuture.get());
    }
}
