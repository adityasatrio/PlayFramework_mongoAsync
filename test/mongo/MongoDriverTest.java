package mongo;

import module.ModuleMongo;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class MongoDriverTest {

    @Test
    public void simpleTest() {
        String hello = "Hello";
        String world = "World";
        assertEquals(hello + " " + world, "Hello World");
    }

    @Test
    public void mongodbConnection() throws ClassNotFoundException {
        Application application = new GuiceApplicationBuilder()
                .bindings(new ModuleMongo())
                .bindings(bind(MongoAsync.class).toProvider(MongoClientProvider.class))
                .build();

        MongoClientProvider mongoClientProviders = application.injector().instanceOf(MongoClientProvider.class);
        MongoAsync mongoAsync = mongoClientProviders.get();
        assertNotNull(mongoAsync.get());

    }
}
