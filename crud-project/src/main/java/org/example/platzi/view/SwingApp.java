package org.example.platzi.view;

import org.example.platzi.model.Employee;
import org.example.platzi.repository.EmployeeRepository;
import org.example.platzi.repository.Repository;
import org.example.platzi.util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingApp extends JFrame {

    private final Repository<Employee> employeeRepository;
    private final JTable employeeTable;

    public SwingApp() {
        // Configure the window
        setTitle("Employee Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 230);

        // Create a table to display employees
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for actions
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Configure the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set styles for the buttons
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        // Create the Repository object to access the database
        employeeRepository = new EmployeeRepository();

        // Load initial employees into the table
        refreshEmployeeTable();

        // Add ActionListener for the buttons
        addButton.addActionListener(e -> {
            try {
                addEmployee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        updateButton.addActionListener(e -> updateEmployee());

        deleteButton.addActionListener(e -> {
            try {
                deleteEmployee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void refreshEmployeeTable() {
        // Get the updated list of employees from the database
        List<Employee> employees = employeeRepository.findAll();

        // Create a table model and set employee data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name (Paternal)");
        model.addColumn("Last Name (Maternal)");
        model.addColumn("Email");
        model.addColumn("Salary");

        for (Employee employee : employees) {
            Object[] rowData = {
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getPaSurname(),
                    employee.getMaSurname(),
                    employee.getEmail(),
                    employee.getSalary()
            };
            model.addRow(rowData);
        }

        // Set the updated table model
        employeeTable.setModel(model);
    }

    private void addEmployee() throws SQLException {
        // Create a form to add an employee
        JTextField firstNameField = new JTextField();
        JTextField paternalLastNameField = new JTextField();
        JTextField maternalLastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name (Paternal):", paternalLastNameField,
                "Last Name (Maternal):", maternalLastNameField,
                "Email:", emailField,
                "Salary:", salaryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            // Create a new Employee object with the entered data
            Employee employee = new Employee();
            employee.setFirstName(firstNameField.getText());
            employee.setPaSurname(paternalLastNameField.getText());
            employee.setMaSurname(maternalLastNameField.getText());
            employee.setEmail(emailField.getText());
            employee.setSalary(Float.parseFloat(salaryField.getText()));

            // Save the new employee to the database
            employeeRepository.save(employee);

            // Update the table with the updated employees
            refreshEmployeeTable();

            JOptionPane.showMessageDialog(this, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateEmployee() {
        // Get the ID of the employee to update
        String employeeIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the employee to update:", "Update Employee", JOptionPane.QUESTION_MESSAGE);
        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                // Get the employee from the database
                Employee employee = employeeRepository.getById(employeeId);

                if (employee != null) {
                    // Create a form with the employee's data
                    JTextField firstNameField = new JTextField(employee.getFirstName());
                    JTextField paternalLastNameField = new JTextField(employee.getPaSurname());
                    JTextField maternalLastNameField = new JTextField(employee.getMaSurname());
                    JTextField emailField = new JTextField(employee.getEmail());
                    JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));

                    Object[] fields = {
                            "First Name:", firstNameField,
                            "Last Name (Paternal):", paternalLastNameField,
                            "Last Name (Maternal):", maternalLastNameField,
                            "Email:", emailField,
                            "Salary:", salaryField
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmResult == JOptionPane.OK_OPTION) {
                        // Update the employee data with the values entered in the form
                        employee.setFirstName(firstNameField.getText());
                        employee.setPaSurname(paternalLastNameField.getText());
                        employee.setMaSurname(maternalLastNameField.getText());
                        employee.setEmail(emailField.getText());
                        employee.setSalary(Float.parseFloat(salaryField.getText()));

                        // Save the changes to the database
                        employeeRepository.save(employee);

                        // Update the employee table in the interface
                        refreshEmployeeTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No employee found with the specified ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for the employee ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEmployee() throws SQLException {
        // Get the ID of the employee to delete
        String employeeIdStr = JOptionPane.showInputDialog(this, "Enter the ID of the employee to delete:", "Delete Employee", JOptionPane.QUESTION_MESSAGE);
        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                // Confirm the deletion of the employee
                int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    // Delete the employee from the database
                    employeeRepository.delete(employeeId);

                    // Update the employee table in the interface
                    refreshEmployeeTable();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for the employee ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

