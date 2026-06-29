package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Employee;
import com.example.demo.repo.MyRepo;

@RestController
public class MyController {

    @Autowired
    private MyRepo repository;

    // CREATE
    @PostMapping("/addEmployee")
    public String addEmployee(@RequestBody Employee employee) {
        repository.save(employee);
        return "Employee Added Successfully";
    }

    // READ ALL
    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    // READ BY ID
    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable int id) {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            return employee.get();
        }

        return null;
    }

    // UPDATE
    @PutMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable int id,
                                 @RequestBody Employee updatedEmployee) {

        Optional<Employee> optional = repository.findById(id);

        if (optional.isPresent()) {

            Employee emp = optional.get();

            emp.setName(updatedEmployee.getName());
            emp.setSalary(updatedEmployee.getSalary());

            repository.save(emp);

            return "Employee Updated Successfully";
        }

        return "Employee Not Found";
    }

    // DELETE BY ID
    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) {

        if (repository.existsById(id)) {

            repository.deleteById(id);

            return "Employee Deleted Successfully";
        }

        return "Employee Not Found";
    }

    // DELETE ALL
    @DeleteMapping("/deleteAllEmployees")
    public String deleteAllEmployees() {

        repository.deleteAll();

        return "All Employees Deleted";
    }

}