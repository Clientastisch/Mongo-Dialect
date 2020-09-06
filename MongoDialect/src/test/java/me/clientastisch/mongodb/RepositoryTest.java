package me.clientastisch.mongodb;

import lombok.Getter;
import me.clientastisch.mongodb.collection.DelegateCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        DelegateCollection collection = database.createCollection("humans");

        collection.find("name", "Fuchs").stream().findFirst().ifPresent(var -> {
            collection.update(var, "age", (int) var.get("age") * 2);
        });

        System.out.println(collection.countDocuments());
    }

}