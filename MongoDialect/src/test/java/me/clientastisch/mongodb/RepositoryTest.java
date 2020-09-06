package me.clientastisch.mongodb;

import me.clientastisch.mongodb.collection.DelegateCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        DelegateCollection collection = database.createCollection("humans");

        collection.insert("{name: \"Name\", age: 15");
        collection.insert("{name: \"Name\", age: 262");

        collection.find("age", 15).forEach(collection::delete);

        collection.find("age", 15).forEach(System.out::println);
    }

}