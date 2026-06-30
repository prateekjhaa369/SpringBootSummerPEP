package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductAsyncServices {
	@Autowired
	private RestTemplate restTemplate;
	
	@Async
	public void fetchProduct()
	{
		String response=restTemplate.getForObject("http://localhost:8081/laptop", String.class);
		System.out.println(response);
	}
	public String getAllProduct()
	{
		String response=restTemplate.getForObject("http://localhost:8081/getAllProduct", String.class);
		return response;
	}
}
