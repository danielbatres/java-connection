package org.example.platzi.main;

import org.example.platzi.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (Connection myConnection = DatabaseConnection.getInstance();
            Statement myStatement = myConnection.createStatement();
            ResultSet myResult = myStatement.executeQuery("SELECT * FROM employees");) {

            while (myResult.next()) {
                System.out.println(myResult.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
