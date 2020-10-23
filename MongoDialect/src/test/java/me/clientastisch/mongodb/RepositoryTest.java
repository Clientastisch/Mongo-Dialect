package me.clientastisch.mongodb;

import me.clientastisch.mongodb.collection.DelegateCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import me.clientastisch.mongodb.filter.Filter;
import org.bson.Document;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        DelegateCollection collection = database.createCollection("humans");

        collection.insert(new Document("age", 1));
        collection.find(Filter.EQUALS, "age", 1, 2, 3, 4).forEach(System.out::println);

        repository.terminate();
    }
}