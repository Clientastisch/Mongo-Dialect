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
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        DelegateCollection collection = database.createCollection("humans");

//        collection.insert(new Document("value", 1)
//                .append("list", new Document("var1", 1)
//                        .append("var2", 2)
//                        .append("var3", 3)
//                )
//        );

        collection.find("name", "Fuchs").stream().findFirst().ifPresent(document -> {
            collection.update(document, "age", 4);
        });

        collection.find(Filter.EQUALS, "age", 1, 2, 3, 4).forEach(System.out::println);

//        collection.find("value", 1).stream().findFirst().ifPresent(document -> {
//            collection.update(document, "list", ((Document) document.get("list")).append("var6", 6));
//        });
    }

}