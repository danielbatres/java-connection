package org.example.platzi.main;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "toor";

        try (
            Connection myConnection = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConnection.createStatement();
            ResultSet myResult = myStatement.executeQuery("SELECT * FROM employees");
            ) {

            while (myResult.next()) {
                System.out.println(myResult.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
