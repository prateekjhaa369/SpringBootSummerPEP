package com.example.spring_security2.repository;

import com.example.spring_security2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

}