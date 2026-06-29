package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Entity;
import com.example.demo.services.MyServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class MyController {

    @Autowired
    private MyServices service;

    // Get all employees
    @GetMapping("/all")
    public List<Entity> getAllEmployees() {
        return service.getAllEmp();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public Entity getEmployeeById(@PathVariable int id) {
        return service.getEmpId(id);
    }

    // Add employee
    @PostMapping("/add")
    public String addEmployee(@Valid @RequestBody Entity employee) {
        return service.addEmployee(employee);
    }

    // Update employee
    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id,
                                 @RequestBody Entity employee) {
        return service.updateEmployee(id, employee);
    }

    // Delete employee by ID
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return service.deleteEmployee(id);
    }

    // Delete all employees
    @DeleteMapping("/deleteAll")
    public String deleteAllEmployees() {
        return service.deleteAllEmployee();
    }
}