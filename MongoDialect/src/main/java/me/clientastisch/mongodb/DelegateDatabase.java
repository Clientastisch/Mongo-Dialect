package me.clientastisch.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DelegateDatabase {

    private final MongoDatabase mongoDriver;

    public DelegateDatabase(MongoDatabase mongoDriver) {
        this.mongoDriver = mongoDriver;
    }

    public MongoCollection<Document> createCollection(String collection) {
        if (!listCollectionNames().contains(collection))
            this.mongoDriver.createCollection(collection);
        return getCollection(collection);
    }

    public MongoCollection<Document> getCollection(String collection) {
        return this.mongoDriver.getCollection(collection);
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
