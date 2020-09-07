# Mongo-Dialect
 
This project aims at simplifying the [Mongo-Driver](https://github.com/mongodb/mongo-java-driver) 
by providing some additional methods, and an easier way to establish connections.

```java
Repository repository = new Repository("localhost", 27017)
        .setUsername("root")
        .setPassword("password")
        .setTimeout(10000)
        .initialize();

DelegateDatabase database = repository.getDatabase("my_database");
DelegateCollection collection = database.createCollection("my_collection");

collection.insert("{name: \"Name\", age: 15}");

collection.find("name", "Human").stream().findFirst().ifPresent(var -> {
    collection.update(var, "age", -1);
});

collection.find("age", 15).forEach(collection::delete);
```


