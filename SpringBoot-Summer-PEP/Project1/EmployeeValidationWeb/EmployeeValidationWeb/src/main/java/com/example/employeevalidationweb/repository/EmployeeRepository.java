package com.example.employeevalidationweb.repository;

import com.example.employeevalidationweb.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

}