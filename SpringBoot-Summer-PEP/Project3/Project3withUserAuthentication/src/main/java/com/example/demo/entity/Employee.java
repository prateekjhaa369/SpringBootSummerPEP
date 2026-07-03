package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Regex ensures letters only, allowing up to 2 interior single spaces
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+){0,2}$", message = "Name must contain only letters and a maximum of 2 spaces")
    private String name;

    @Pattern(regexp = "^(Manager|Tester|Programmer)$", message = "Designation must be Manager, Tester, or Programmer")
    private String designation;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age cannot be more than 60")
    private int age;

    private double salary;

    // Links this employee record back to the specific user account that added them
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username")
    private User creator;

    // Constructors
    public Employee() {}

    public Employee(String name, String designation, int age, double salary) {
        this.name = name;
        this.designation = designation;
        this.age = age;
        this.salary = salary;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }
}