package me.clientastisch.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import me.clientastisch.mongodb.database.DelegateDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    public void testRepository() {
        Repository repository = new Repository("localhost", 27017)
                .setTimeout(10000)
                .initialize();

        DelegateDatabase database = repository.getDatabase("my_database");
        MongoCollection<Document> document = database.createCollection("my_collection");

        document.insertOne(Document.parse("{name: \"Human\", age: 15}"));

        BasicDBObject fields = new BasicDBObject("age", 17);

        assertTrue(document.find(fields).iterator().hasNext());
    }

}