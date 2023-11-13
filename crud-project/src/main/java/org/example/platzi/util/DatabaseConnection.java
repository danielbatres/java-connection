package org.example.platzi.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "toor";
    private static Connection myConnection;

    public static Connection getInstance() {
        if (myConnection == null) {
            try {
                myConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Connection failed");
            }
        }

        return myConnection;
    }
}
