package com.example.employeevalidationnew.repository;

import com.example.employeevalidationnew.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

}