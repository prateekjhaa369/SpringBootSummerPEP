package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employer;
import com.example.demo.services.MyServices;

@RestController
public class MyController {
	@Autowired
	private MyServices services;
	
	@GetMapping("/getAll")
	public List<Employer> getAll()
	{
		return services.getAll();
	}
	@GetMapping("/getById/{id}")
	public Employer getAll(@PathVariable int id)
	{
		return services.getById(id);
	}
	@PostMapping("/addEmp")
	public String addEmp(@RequestBody Employer emp)
	{
		return services.add(emp);
	}
	@PutMapping("/updateEmp/{id}")
	public String updateEmp(@PathVariable int id, @RequestBody Employer emp)
	{
		return services.update(id, emp);
	}
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id)
	{
		return services.delete(id);
	}
}
