package org.example.platzi.main;

import org.example.platzi.model.Employee;
import org.example.platzi.repository.EmployeeRepository;
import org.example.platzi.repository.Repository;
import org.example.platzi.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection myConnection = DatabaseConnection.getInstance()) {
            if (myConnection.getAutoCommit()) {
                myConnection.setAutoCommit(false);
            }

            try {
                Repository<Employee> repository = new EmployeeRepository(myConnection);

                System.out.println("New employee");
                Employee employee = new Employee();
                employee.setFirstName("America");
                employee.setPaSurname("Lopez");
                employee.setMaSurname("Villa");
                employee.setEmail("ame2@example.com");
                employee.setSalary(3000f);
                employee.setCurp("AMECJGUTJLDKWE3IOP");

                repository.save(employee);

                myConnection.commit();
            } catch (Exception e) {
                myConnection.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong");
        }
    }
}
