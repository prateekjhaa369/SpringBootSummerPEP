package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductAsyncServices;

@RestController
public class AsyncProductClientController {
	@Autowired
	private ProductAsyncServices product;
	
	@GetMapping("/getLaptop")
	public String getLaptop()
	{
		product.fetchProduct();
		return "Request Accepted. product is being fetched";
	}
	
	@GetMapping("/getAllProduct")
	public String getAllProduct()
	{
		return product.getAllProduct();
	}
	
}
