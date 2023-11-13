package org.example.platzi.main;

import org.example.platzi.model.Employee;
import org.example.platzi.repository.EmployeeRepository;
import org.example.platzi.repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<Employee> repository = new EmployeeRepository();

        repository.findAll().forEach(System.out::println);
    }
}
