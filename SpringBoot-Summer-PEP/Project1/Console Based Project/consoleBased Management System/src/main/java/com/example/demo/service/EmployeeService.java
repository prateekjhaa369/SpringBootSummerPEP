package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // Create Employee
    public void createEmployee(String name, int age, String design) {

        double salary = 0;

        if (design.equalsIgnoreCase("Programmer")) {
            salary = 25000;
        } else if (design.equalsIgnoreCase("Manager")) {
            salary = 30000;
        } else if (design.equalsIgnoreCase("Tester")) {
            salary = 20000;
        } else {
            System.out.println("Invalid Designation");
            return;
        }

        Employee emp = new Employee(name, age, design, salary);

        repo.save(emp);

        System.out.println("Employee Saved Successfully.");
    }

    // Display Employees
    public void displayEmployees() {

        List<Employee> list = repo.findAll();

        if (list.isEmpty()) {
            System.out.println("No Employees Found.");
            return;
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("ID\tName\t\tAge\t\tDesignation\tSalary");

        for (Employee e : list) {
            System.out.println(
                    e.getId() + "\t" +
                    e.getName() + "\t" +
                    e.getAge() + "\t\t" +
                    e.getDesign() + "\t\t" +
                    e.getSalary()
            );
        }
    }

    // Raise Salary
    public void raiseSalary(int id, double amount) {

        Employee emp = repo.findById(id).orElse(null);

        if (emp == null) {
            System.out.println("Employee Not Found.");
            return;
        }

        emp.setSalary(emp.getSalary() + amount);

        repo.save(emp);

        System.out.println("Salary Updated Successfully.");
    }
}