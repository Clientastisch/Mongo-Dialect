package me.clientastisch.mongodb.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.clientastisch.mongodb.collection.DelegateCollection;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DelegateDatabase {

    private final MongoDatabase mongoDriver;

    public DelegateDatabase(MongoDatabase mongoDriver) {
        this.mongoDriver = mongoDriver;
    }

    public DelegateCollection createCollection(String collection) {
        if (!listCollectionNames().contains(collection))
            this.mongoDriver.createCollection(collection);

        return getCollection(collection);
    }

    public DelegateCollection getCollection(String collection) {
        return new DelegateCollection(mongoDriver.getCollection(collection));
    }

    public List<Document> listCollections() {
        List<Document> collections = new LinkedList<>();
        this.mongoDriver.listCollections().iterator().forEachRemaining(collections::add);
        return collections;
    }

    public List<String> listCollectionNames() {
        List<String> collections = new LinkedList<>();
        this.mongoDriver.listCollectionNames().iterator().forEachRemaining(collections::add);
        return collections;
    }
}
