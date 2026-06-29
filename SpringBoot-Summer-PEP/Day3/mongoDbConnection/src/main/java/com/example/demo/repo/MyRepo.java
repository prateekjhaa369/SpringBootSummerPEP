package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Employee;

public interface MyRepo extends MongoRepository<Employee,Integer>{

}
