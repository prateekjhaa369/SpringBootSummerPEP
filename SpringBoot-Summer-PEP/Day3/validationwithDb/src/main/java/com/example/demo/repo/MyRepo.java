package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Entity;

public interface MyRepo extends MongoRepository<Entity, Integer>{

}
