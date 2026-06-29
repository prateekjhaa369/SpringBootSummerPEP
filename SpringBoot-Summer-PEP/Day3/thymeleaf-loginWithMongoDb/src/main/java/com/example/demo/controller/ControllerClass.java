package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CheckEmp;
import com.example.demo.repo.MyRepo;

@Controller
public class ControllerClass {
	@Autowired
    private MyRepo repo;
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";	//login.html
	}
	
	@PostMapping("/register")
    public String register(@RequestBody CheckEmp emp) {

        repo.save(emp);
        return "redirect:/login";

    
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                         @RequestParam String password,
                         Model model) {

    	List<CheckEmp> users = repo.findAll();

        for (CheckEmp user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                model.addAttribute("username", username);
                return "welcome";
            }
        }

        model.addAttribute("error", "Invalid Username or Password");
        return "login";
    }

}
