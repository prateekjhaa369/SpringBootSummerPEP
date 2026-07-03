package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.MyService;

@SpringBootApplication
public class AspectAoPApplication implements CommandLineRunner {
	@Autowired
    private MyService myServices;
	public static void main(String[] args) {
		SpringApplication.run(AspectAoPApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		myServices.display();
	}
	

}
