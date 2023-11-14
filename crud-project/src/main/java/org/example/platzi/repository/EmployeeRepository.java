package org.example.platzi.repository;

import org.example.platzi.model.Employee;
import org.example.platzi.util.DatabaseConnection;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection myConnection = getConnection();
             Statement myStatement = myConnection.createStatement();
            ResultSet myResult = myStatement.executeQuery("SELECT * FROM employees")) {
            while (myResult.next()) {
                employees.add(createEmployee(myResult));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }

        return employees;
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = null;
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection myConnection = getConnection();
             PreparedStatement myStatement = myConnection.prepareStatement(sql)) {
            myStatement.setInt(1, id);

            try (ResultSet myResult = myStatement.executeQuery()) {
                if (myResult.next()) {
                    employee = createEmployee(myResult);
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }

        return employee;
    }

    @Override
    public void save(Employee employee) {
        String sql = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary, curp) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection myConnection = getConnection();
            PreparedStatement myStatement = myConnection.prepareStatement(sql);) {
            myStatement.setString(1, employee.getFirstName());
            myStatement.setString(2, employee.getPaSurname());
            myStatement.setString(3, employee.getMaSurname());
            myStatement.setString(4, employee.getEmail());
            myStatement.setFloat(5, employee.getSalary());
            myStatement.setString(6, employee.getCurp());

            myStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection myConnection = getConnection();
             PreparedStatement myStatement = myConnection.prepareStatement("DELETE FROM employees WHERE id = ?")){
            myStatement.setInt(1, id);

            myStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }
    }

    private Employee createEmployee(ResultSet myResult) throws SQLException {
        Employee employee = new Employee();
        employee.setId(myResult.getInt("id"));
        employee.setFirstName(myResult.getString("first_name"));
        employee.setPaSurname(myResult.getString("pa_surname"));
        employee.setMaSurname(myResult.getString("ma_surname"));
        employee.setEmail(myResult.getString("email"));
        employee.setSalary(myResult.getFloat("salary"));
        employee.setCurp(myResult.getString("curp"));

        return employee;
    }
}
