package com.example.employeevalidationweb.controller;

import com.example.employeevalidationweb.entity.Employee;
import com.example.employeevalidationweb.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Open Create Page
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "create";
    }

    // Save Employee
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute Employee employee,
                               BindingResult result) {

        if(result.hasErrors()) {
            return "create";
        }

        if(employee.getDesignation().equalsIgnoreCase("Programmer"))
            employee.setSalary(25000);

        else if(employee.getDesignation().equalsIgnoreCase("Manager"))
            employee.setSalary(30000);

        else if(employee.getDesignation().equalsIgnoreCase("Tester"))
            employee.setSalary(20000);

        else
            employee.setSalary(0);

        repository.save(employee);

        return "redirect:/display";
    }

    // Display Employees
    @GetMapping("/display")
    public String displayEmployees(Model model) {

        model.addAttribute("employees", repository.findAll());

        return "display";
    }

    // Open Raise Salary Page
    @GetMapping("/raise")
    public String raisePage() {
        return "raise";
    }

    // Raise Salary
    @PostMapping("/raiseSalary")
    public String raiseSalary(@RequestParam String name,
                              @RequestParam int percentage) {

        Employee employee = repository.findByName(name);

        if(employee != null && percentage >= 1 && percentage <= 10) {

            double newSalary = employee.getSalary()
                    + (employee.getSalary() * percentage / 100.0);

            employee.setSalary(newSalary);

            repository.save(employee);
        }

        return "redirect:/display";
    }

}