import java.sql.*;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        Connection myConnection = null;
        PreparedStatement myStatement = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "toor");
            System.out.println("Connected");

            String sql = "INSERT INTO employees (first_name, pa_surname) VALUES (?, ?)";

            myStatement = myConnection.prepareStatement(sql);
            myStatement.setString(1, "Johana");
            myStatement.setString(2, "Dorantes");

            int rowsAffected = myStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("new employee created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}