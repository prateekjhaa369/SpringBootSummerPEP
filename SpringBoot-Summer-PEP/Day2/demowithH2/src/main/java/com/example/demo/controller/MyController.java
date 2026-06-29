package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.repo.MyRepo;

@RestController
public class MyController {
	
	@Autowired
	private MyRepo repository;
	
	@PostMapping("/addEmployee")
	public String addEmployee(@RequestBody Employee employee)
	{
		repository.save(employee);
		return "Employee added successfully";
	}
	
	@GetMapping("/getAllEmployee")
    public List<Employee> getAllEmployee() {
        return repository.findAll();
    }

    // GET BY ID
    @GetMapping("/getAllEmployee/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        Optional<Employee> emp = repository.findById(id);
        return emp.orElse(null);
    }
 // UPDATE
    @PutMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> optional = repository.findById(id);

        if (optional.isPresent()) {
            Employee emp = optional.get();
            emp.setName(updatedEmployee.getName());
            emp.setSalary(updatedEmployee.getSalary());
            repository.save(emp);
            return "Employee updated successfully";
        }

        return "Employee not found";
    }

    // DELETE BY ID
    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "Employee not found";
    }

    // DELETE ALL
    @DeleteMapping("/deleteAllEmployee")
    public String deleteAllEmployee() {
        repository.deleteAll();
        return "All employees deleted";
    }
}

	
	

