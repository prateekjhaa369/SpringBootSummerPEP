package com.example.demo;

import org.springframework.boot.SpringApplication;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.EmployeeService;


@SpringBootApplication
public class TaskOnDay5Application  implements CommandLineRunner{

	  @Autowired
	    private EmployeeService service;

	    Scanner sc = new Scanner(System.in);
	    
	public static void main(String[] args) {
		SpringApplication.run(TaskOnDay5Application.class, args);
	}
	
	 @Override
	    public void run(String... args) throws Exception {

	        while (true) {

	            System.out.println("\n=================================");
	            System.out.println(" Employee Management System");
	            System.out.println("=================================");
	            System.out.println("1. Create Employee");
	            System.out.println("2. Display Employees");
	            System.out.println("3. Raise Salary");
	            System.out.println("4. Exit");
	            System.out.print("Enter Choice : ");

	            int choice = sc.nextInt();

	            switch (choice) {

	                case 1:

	                    while (true) {

	                        sc.nextLine(); 

	                        System.out.print("\nEnter Name : ");
	                        String name = sc.nextLine();

	                        System.out.print("Enter Age : ");
	                        int age = sc.nextInt();

	                        sc.nextLine();

	                        System.out.print("Enter Designation (Programmer/Manager/Tester): ");
	                        String design = sc.nextLine();

	                        service.createEmployee(name, age, design);

	                        System.out.print("\nDo you want to create another employee? (Yes/No): ");
	                        String ans = sc.nextLine();

	                        if (ans.equalsIgnoreCase("Yes")) {
	                            continue;
	                        } else if (ans.equalsIgnoreCase("No")) {
	                            break;
	                        } else {
	                            System.out.println("Invalid Input! Returning to Main Menu...");
	                            break;
	                        }
	                    }

	                    break;

	                case 2:

	                    service.displayEmployees();
	                    break;

	                case 3:

	                    System.out.print("Enter Employee ID : ");
	                    int id = sc.nextInt();

	                    System.out.print("Enter Increment Amount : ");
	                    double amount = sc.nextDouble();

	                    service.raiseSalary(id, amount);

	                    break;

	                case 4:

	                    System.out.println("Thank You!");
	                    System.exit(0);

	                default:

	                    System.out.println("Invalid Choice.");
	            }
	        }
	    }

}
