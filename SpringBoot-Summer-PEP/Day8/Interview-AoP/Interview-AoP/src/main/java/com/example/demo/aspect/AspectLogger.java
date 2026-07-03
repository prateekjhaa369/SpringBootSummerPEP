package com.example.demo.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {
	@Before("execution(* com.example.demo.service.MyService.display(..))")
	public void beforAdvice()
	{
		System.out.println("Prepare for the interview");
	}
	@After("execution(* com.example.demo.service.MyService.display(..))")
	public void afterAdvice()
	{
		System.out.println("Aftermath of the interview");
	}
}
