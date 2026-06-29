package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
	
	public static void main(String args[])
	{
		ApplicationContext ac= new ClassPathXmlApplicationContext("ApplicationContext.xml");
		Employee e=(Employee) ac.getBean("emp");
		
		System.out.println(e.getEmpid());
		System.out.println(e.getName());
		System.out.println(e.getSalary());
	}
	

}
