package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Entity;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repo.MyRepo;

@Service
public class MyServices {
	
	@Autowired
	private MyRepo repo;
	
	public List<Entity> getAllEmp(){
		return repo.findAll();
		
	}
	public Entity getEmpId(int id) {
		return repo.findById(id)
				.orElseThrow(()-> new EmployeeNotFoundException("Employee not found"+ id));
	}
	
	public String addEmployee(Entity employee) {
		repo.save(employee);
		return "Employee added Successfully";
	}
	
	public String updateEmployee(int id , Entity employee)
	{
		Optional<Entity> exist=repo.findById(id);
		if(exist.isPresent())
		{
			Entity emp=exist.get();
			emp.setName(employee.getName());
			emp.setAge(employee.getAge());
			emp.setSalary(emp.getSalary());
			emp.setDesig(employee.getDesig());
			repo.save(emp);
			
			return "Employee updated";
		}
		else
		{
			return "employee not found"+id;
		}
	}
	
	public String deleteEmployee(int id)
	{
		if(repo.existsById(id))
		{
			repo.deleteById(id);
			return "Employee deleted";
		}
		else
		{
			return "Employee not found with id"+id;
		}
	}
	
	public String deleteAllEmployee()
	{
		repo.deleteAll();
		return "All data Deleted";
	}
	

}
