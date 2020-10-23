package me.clientastisch.mongodb;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.clientastisch.mongodb.database.DelegateDatabase;

import java.util.Optional;

@Accessors(chain = true)
public class Repository {

    private MongoClientSettings settings;
    private MongoClient client;

    @Setter private String username, password, name;

    private final String host;
    private final int port;

    public Repository(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Repository initialize() {
        ConnectionString connection = new ConnectionString(
                String.format("mongodb://%s:%s@%s:%s/",
                        Optional.ofNullable(username).orElse(""),
                        Optional.ofNullable(password).orElse(""),
                        host, port
                ).replace(":@", "")
        );

        System.out.println(connection.getConnectionString());

        settings = MongoClientSettings.builder()
                .applyConnectionString(connection)
                .applicationName(name)
                .build();

        client = MongoClients.create(settings);

        return this;
    }

    public void terminate() {
        client.close();
    }

    public DelegateDatabase getDatabase(String database) {
        return new DelegateDatabase(client.getDatabase(database));
    }
}
