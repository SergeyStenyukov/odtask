package com.opendev.odtask.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static final String URL = "db.url";

    private static final String PROPERTIES = "application.properties";

    private String url;

    public ConnectionProvider() {
        try {
            Properties properties = new Properties();
            InputStream inStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES);
            if (inStream != null) {
                properties.load(inStream);
            } else {
                throw new FileNotFoundException("Unable to find properties file");
            }
            this.url = properties.getProperty(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
