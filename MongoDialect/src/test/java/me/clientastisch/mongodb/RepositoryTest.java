package me.clientastisch.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("test");
        MongoCollection<Document> document = database.createCollection("humans");

        document.insertOne(Document.parse("{name: \"Lucas\", age: 15}"));

        BasicDBObject fields = new BasicDBObject("age", 17);
        document.find(fields).forEach(var -> System.out.println(var.toJson()));
    }

}