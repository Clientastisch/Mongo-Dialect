# Mongo-Dialect
 
This project aims at simplifying the [Mongo-Driver](https://github.com/mongodb/mongo-java-driver) 
by providing some additional methods, and an easier way to establish connections.

````java
    Repository repository = new Repository("localhost", 27017)
            .setUsername("root")
            .setPassword("password")
            .setTimeout(10000)
            .initialize();

    DelegateDatabase database = repository.getDatabase("my_database");
    MongoCollection<Document> document = database.createCollection("my_collection");
````
