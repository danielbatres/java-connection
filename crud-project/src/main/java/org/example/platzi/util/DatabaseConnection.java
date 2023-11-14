package org.example.platzi.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "toor";
    private static BasicDataSource pool;

    public static BasicDataSource getInstance() {
        if (pool == null) {
            try {
                pool = new BasicDataSource();
                pool.setUrl(URL);
                pool.setUsername(USER);
                pool.setPassword(PASSWORD);

                pool.setInitialSize(3);
                pool.setMinIdle(2);
                pool.setMaxIdle(10);
                pool.setMaxTotal(10);
            } catch (Exception e) {
                System.out.println("Connection failed");
            }
        }

        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
