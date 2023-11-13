package org.example.platzi.main;

import org.example.platzi.model.Employee;
import org.example.platzi.repository.EmployeeRepository;
import org.example.platzi.repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<Employee> repository = new EmployeeRepository();

        Employee employee = new Employee();
        employee.setFirstName("Diego");
        employee.setPaSurname("Pimentel");
        employee.setMaSurname("Gutierrez");
        employee.setEmail("diego@example.com");
        employee.setSalary(1900f);

        repository.save(employee);

        repository.findAll().forEach(System.out::println);
    }
}
