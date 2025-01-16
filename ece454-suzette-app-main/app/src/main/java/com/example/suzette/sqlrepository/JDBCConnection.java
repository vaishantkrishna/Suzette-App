package com.example.suzette.sqlrepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    private Connection connection;
    private final String host = "127.0.0.1";
    private final String database = "madlane";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public JDBCConnection() {
        this.url = String.format(this.url, this.host, this.port, this.database);
    }

    //@Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            status = true;
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            status = false;
            System.out.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    //@Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
