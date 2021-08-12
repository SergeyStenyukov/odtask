package com.opendev.odtask.connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ScriptReader {

    public void readSqlScript(String scriptPath, String db) {

        String line;
        StringBuilder sqlScript = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(db);
             Statement statement = connection.createStatement();
             FileReader fileReader = new FileReader(scriptPath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            while ((line = bufferedReader.readLine()) != null) {
                sqlScript.append(line);
            }

            String[] scriptLines = sqlScript.toString().split(";");

            for (String scriptLine : scriptLines) {
                if (!scriptLine.trim().equals("")) {
                    statement.executeUpdate(scriptLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
