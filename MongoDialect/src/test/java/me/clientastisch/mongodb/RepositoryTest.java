package me.clientastisch.mongodb;

import me.clientastisch.mongodb.collection.DelegateCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import me.clientastisch.mongodb.filter.Filter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        DelegateCollection collection = database.createCollection("humans");

        collection.find("name", "Fuchs").stream().findFirst().ifPresent(var -> {
            collection.update(var, "age", 4);
        });

//        collection.findAll().stream().filter(var -> var.containsKey("age")).forEach(var -> {
//            collection.update(var, "age", (int) var.get("age") * 2);
//        });

        collection.find("age", Arrays.asList(8, 4, 12, 2), Filter.UNEQUAL).stream().findFirst().ifPresent(var -> {
            System.out.println(var.get("name"));
        });

        collection.findAll().forEach(System.out::println);
    }

}