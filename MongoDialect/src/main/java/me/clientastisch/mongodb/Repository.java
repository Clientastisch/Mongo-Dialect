package me.clientastisch.mongodb;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.clientastisch.mongodb.database.DelegateDatabase;

@Accessors(chain = true)
public class Repository {

    private MongoClientSettings settings;
    private MongoClient client;

    @Setter private String username, password, name;
    @Setter private long timeout;

    private final String host;
    private final int port;

    public Repository(String host, int port) {
        this.timeout = Integer.MAX_VALUE;
        this.host = host;
        this.port = port;
    }

    public Repository initialize() {
        ConnectionString connection = new ConnectionString(
                String.format("mongodb://%s%s%s:%s/?maxTimeMS=%s",
                        username != null ? username + ":" : "",
                        password != null ? password + "@" : "",
                        host, port, timeout
                )
        );

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
