package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Document (collection = "JavaSBEmp")
public class Entity {
	
	@Id
	
	private int id;
	private String name;
	
	 @Min(value = 18, message = "Age must be at least 18")
	 @Max(value = 60, message = "Age cannot be greater than 60")
	private int age;
	private int salary;
	private String desig;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getDesig() {
		return desig;
	}
	public void setDesig(String desig) {
		this.desig = desig;
	}
	public Entity() {
		
	}
	public Entity(int id, String name, int age, int salary, String desig) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.desig = desig;
	}
	

}
