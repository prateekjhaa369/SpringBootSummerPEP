package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employer;

@Service
public class MyServices {
	private List<Employer> li = new ArrayList<>();

	public String add(Employer emp) {
		li.add(emp);
		return "Success";
	}

	public List<Employer> getAll() {
		return li;
	}

	public Employer getById(int id) {
		for (Employer e : li) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
	}

	public String update(int id, Employer emp) {
	    
	    for (int i = 0; i < li.size(); i++) {
	        Employer e = li.get(i);
	        
	        if (e.getId() == id) {
	            e.setAge(emp.getAge());
	            e.setDesign(emp.getDesign());
	            e.setName(emp.getName());
	            e.setSalary(emp.getSalary());
	               
	            return "Success";
	        }
	    }
	    return "Id Wrong";
	}
	
	public String delete(int id)
	{
boolean isRemoved = li.removeIf(e -> e.getId() == id);
        
        if (isRemoved) {
            return "Success";
        } else {
            return "Id Wrong"; 
        }
	}
}
