package com.example.employeevalidationnew.controller;

import com.example.employeevalidationnew.entity.Employee;
import com.example.employeevalidationnew.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    // Create Employee
    @PostMapping("/employee")
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {

        if(employee.getDesignation().equalsIgnoreCase("Programmer"))
            employee.setSalary(25000);

        else if(employee.getDesignation().equalsIgnoreCase("Manager"))
            employee.setSalary(30000);

        else if(employee.getDesignation().equalsIgnoreCase("Tester"))
            employee.setSalary(20000);

        else
            throw new RuntimeException("Invalid Designation");

        return repository.save(employee);
    }

    // Display Employees
    @GetMapping("/employees")
    public List<Employee> displayEmployees() {
        return repository.findAll();
    }

    // Raise Salary by Percentage
    @PutMapping("/raise")
    public Employee raiseSalary(@RequestParam String name,
                                @RequestParam int percentage) {

        System.out.println("Name received = " + name);

        Employee employee = repository.findByName(name);

        System.out.println("Employee = " + employee);

        if(employee == null)
            throw new RuntimeException("Employee Not Found");

        double newSalary = employee.getSalary()
                + (employee.getSalary() * percentage / 100.0);

        employee.setSalary(newSalary);

        return repository.save(employee);
    }
}