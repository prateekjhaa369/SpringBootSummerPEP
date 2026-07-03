package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    // ==========================================
    //          USER & AUTHENTICATION METHODS
    // ==========================================

    /**
     * Registers a new user account if the username isn't already taken.
     */
    public boolean registerUser(User user) {
        if (userRepository.existsById(user.getUsername())) {
            return false; 
        }
        userRepository.save(user);
        return true;
    }

    /**
     * Validates user credentials against the database records.
     */
    public boolean loginUser(String username, String password) {
        Optional<User> dbUser = userRepository.findById(username);
        if (dbUser.isPresent()) {
            return dbUser.get().getPassword().equals(password);
        }
        return false;
    }

    /**
     * Finds a User entity object reference by their unique username string.
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findById(username);
    }

    // ==========================================
    //         EMPLOYEE MANAGEMENT METHODS
    // ==========================================

    /**
     * Returns only the employees that were created by the currently logged-in user.
     */
    public List<Employee> getEmployeesByUser(String username) {
        return employeeRepository.findByCreatorUsername(username);
    }

    /**
     * Creates an employee record, sets the default base salary matching the role tier,
     * and links the newly created record to the active creator account context.
     */
    public Employee saveEmployee(Employee employee, User creator) {
        // Enforce hardcoded base salaries according to corporate designation
        switch (employee.getDesignation()) {
            case "Manager":
                employee.setSalary(30000.0);
                break;
            case "Tester":
                employee.setSalary(20000.0);
                break;
            case "Programmer":
                employee.setSalary(25000.0);
                break;
            default:
                throw new IllegalArgumentException("Invalid designation tier selected.");
        }

        // Assign the owning user relationship link
        employee.setCreator(creator);
        return employeeRepository.save(employee);
    }

    /**
     * Raises an employee's salary by a specified percentage between 1% and 10%.
     * Performs a security check to verify the logged-in user owns the record before modifications.
     */
    public boolean raiseSalary(Long id, double percentage, String currentUsername) {
        // Strict business criteria range check
        if (percentage < 1.0 || percentage > 10.0) {
            throw new IllegalArgumentException("Salary raise must be between 1% and 10%");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            // Security guard rule: Block users from altering profiles they didn't create
            if (!employee.getCreator().getUsername().equals(currentUsername)) {
                throw new IllegalArgumentException("Unauthorized: You do not have permissions to modify this employee record.");
            }

            double currentSalary = employee.getSalary();
            double newSalary = currentSalary + (currentSalary * (percentage / 100.0));
            employee.setSalary(newSalary);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }
}