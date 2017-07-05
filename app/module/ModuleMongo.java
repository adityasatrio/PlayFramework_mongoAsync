package module;

import com.google.inject.AbstractModule;
import mongo.MongoAsync;
import mongo.MongoClientProvider;

/**
 * Created by satrioaditya on 24/02/17.
 */
public class ModuleMongo extends AbstractModule {
    @Override
    protected void configure() {
        bind(MongoAsync.class).toProvider(MongoClientProvider.class);
    }
}
