package org.example.platzi.main;

import org.example.platzi.entity.Employee;
import org.example.platzi.util.UtilEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = UtilEntity.getEntityManager();

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();

        employees.forEach(System.out::println);

        int employeeId = 3;

        Employee employee = entityManager.find(Employee.class, employeeId);

        System.out.println("SEARCH: " + employee);

        System.out.println("CREATE");

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Diego");
        newEmployee.setPaSurname("Lechuga");
        newEmployee.setMaSurname("Pimentel");
        newEmployee.setEmail("diego@example.com");
        newEmployee.setSalary(8900f);

        entityManager.getTransaction().begin();
        entityManager.persist(newEmployee);
        entityManager.getTransaction().commit();

        System.out.println("UPDATE");

        int employeeToUpdateId = 2;

        Employee employeeToUpdate = entityManager.find(Employee.class, employeeToUpdateId);

        System.out.println("Employee to update" + employeeToUpdate);

        employeeToUpdate.setFirstName("Irving");
        employeeToUpdate.setPaSurname("Juarez");
        employeeToUpdate.setSalary(97000f);

        entityManager.getTransaction().begin();
        entityManager.persist(employeeToUpdate);
        entityManager.getTransaction().commit();

        System.out.println("Employee updated " + employeeToUpdate);

        System.out.println("DELETE");

        int employeeToDeleteId = 1;

        Employee employeeToDelete = entityManager.find(Employee.class, employeeToDeleteId);

        System.out.println(employeeToDelete);

        entityManager.getTransaction().begin();
        entityManager.remove(employeeToDelete);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
