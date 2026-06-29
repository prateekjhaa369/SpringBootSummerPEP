package com.example.demo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	List<Student> student=new ArrayList<>();
	public StudentController() {
		student.add(new Student(1,"Anish"));
		student.add(new Student(2,"Roy"));
	}

	
	@GetMapping("/student/{id}")
	public Student getStudentById(@PathVariable int id)
	{
		for(Student s:student)
		{
			if(s.getId()==id)
				return s;
		}
		throw new StudentNotFoundException("Student not found with id "+id);
	}
	
}
