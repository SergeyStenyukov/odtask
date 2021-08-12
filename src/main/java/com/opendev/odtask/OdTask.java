package com.opendev.odtask;

import com.opendev.odtask.connection.ConnectionProvider;
import com.opendev.odtask.connection.ScriptReader;
import com.opendev.odtask.ui.UserInterface;

public class OdTask {

    private static final String DB_URL = "jdbc:sqlite:ODTASK.s3db";

    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String schema = classLoader.getResource("schema.sql").getFile();
        String data = classLoader.getResource("data.sql").getFile();
        String drop = classLoader.getResource("drop.sql").getFile();

        ConnectionProvider connectionProvider = new ConnectionProvider();
        ScriptReader scriptReader = new ScriptReader();
        scriptReader.readSqlScript(schema, DB_URL);
        scriptReader.readSqlScript(data, DB_URL);

        UserInterface ui = new UserInterface(connectionProvider);
        ui.run();

        scriptReader.readSqlScript(drop, DB_URL);
    }
}
