package me.clientastisch.mongodb.collection;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DelegateCollection {

    private final MongoCollection<Document> mongoCollection;

    public DelegateCollection(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public List<Document> find(String key, Object value) {
        List<Document> documents = new LinkedList<>();
        BasicDBObject filter = new BasicDBObject(key, value);
        this.mongoCollection.find(filter).iterator().forEachRemaining(documents::add);
        return documents;
    }

    public void insert(String json) {
        this.insert(Document.parse(json));
    }

    public void insert(Document document) {
        this.mongoCollection.insertOne(document);
    }

    public void delete(String json) {
        this.delete(Document.parse(json));
    }

    public void delete(Document document) {
        this.mongoCollection.deleteOne(document);
    }

    public long countDocuments(Bson filter, CountOptions options) {
        return this.mongoCollection.countDocuments(filter, options);
    }

    public long countDocuments(Bson filter) {
        return this.mongoCollection.countDocuments(filter);
    }
}
