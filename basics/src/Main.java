import javax.xml.transform.Result;
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

            int rowsAffected = myStatement.executeUpdate("UPDATE employees SET email = 'johanador2@example.com' WHERE first_name = 'Johana'");

            myResult = myStatement.executeQuery("SELECT * FROM employees ORDER BY pa_surname");

            while (myResult.next()) {
                System.out.println(myResult.getString("first_name") + " " + myResult.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}