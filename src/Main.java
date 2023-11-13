import java.sql.*;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResult = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "toor");
            System.out.println("Connected");

            myStatement = myConnection.createStatement();
            myResult = myStatement.executeQuery("SELECT * FROM employees");

            while (myResult.next()) {
                System.out.println(myResult.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}