package me.clientastisch.mongodb.collection;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.clientastisch.mongodb.filter.Filter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DelegateCollection {

    private final MongoCollection<Document> mongoCollection;

    public DelegateCollection(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public List<Document> findAll() {
        List<Document> documents = new LinkedList<>();
        this.mongoCollection.find(Filters.ne("_id", null)).iterator().forEachRemaining(documents::add);
        return documents;
    }

    public List<Document> find(String key, Object value) {
        List<Document> documents = new LinkedList<>();
        BasicDBObject filter = new BasicDBObject(key, value);
        this.mongoCollection.find(filter).iterator().forEachRemaining(documents::add);
        return documents;
    }

    public List<Document> find(String key, Object value, Filter filter) {
        List<Document> documents = new LinkedList<>();
        this.mongoCollection.find(filter.getFiler(key, value)).iterator().forEachRemaining(documents::add);
        return documents;
    }

    public List<Document> find(String key, List<Object> value, Filter filter) {
        List<Document> documents = new LinkedList<>();
        Bson bson = Filters.or(value.stream().map(var -> filter.getFiler(key, var)).collect(Collectors.toList()));
        this.mongoCollection.find(bson).iterator().forEachRemaining(documents::add);
        return documents;
    }

    public void insert(String json) {
        this.insert(Document.parse(json));
    }

    public void insert(Document document) {
        this.mongoCollection.insertOne(document);
    }

    public void delete(Document document) {
        this.mongoCollection.deleteOne(document);
    }

    public void update(Document document, String key, Object value) {
        Bson bson = Filters.and(document.entrySet().stream().map(entry -> Filters.eq(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        this.mongoCollection.updateOne(bson, new Document("$set", new Document(key, value)));
    }

    public void remove(Document document, String key) {
        Bson bson = Filters.and(document.entrySet().stream().map(entry -> Filters.eq(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        this.mongoCollection.updateOne(bson, new Document("$unset", new Document(key, null)));
    }

    public void add(Document document, String key, Object value) {
        this.update(document, key, value);
    }

    public long countDocuments() {
        return this.mongoCollection.countDocuments();
    }
}
