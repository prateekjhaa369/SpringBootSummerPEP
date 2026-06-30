package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncProductController {

	@GetMapping("/laptop")
	public String getProduct () throws Exception
	{
		Thread.sleep(5000);
		return "Laptop price = Rupee-650000";
	}
	
	@GetMapping("/getAllProduct")
	public String getAllProduct ()
	{
		return "Laptop, Mobile, Smartphone";
	}
}
