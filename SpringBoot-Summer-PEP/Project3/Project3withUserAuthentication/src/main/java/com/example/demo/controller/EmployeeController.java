package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.service.EmployeeService;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private UserRepository userRepository;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // --- AUTH ROUTES ---
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user, HttpSession session, Model model) {
        Optional<User> dbUser = userRepository.findById(user.getUsername());
        if (dbUser.isPresent() && dbUser.get().getPassword().equals(user.getPassword())) {
            session.setAttribute("loggedInUser", user.getUsername());
            return "redirect:/employees?action=display";
        }
        model.addAttribute("loginError", "Invalid username or password.");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) return "register";
        if (userRepository.existsById(user.getUsername())) {
            model.addAttribute("registerError", "Username already taken!");
            return "register";
        }
        userRepository.save(user);
        return "redirect:/employees/login?registered=true";
    }

    // --- PROTECTED EMPLOYEE ENTITY CORE OPERATIONS ---
    @GetMapping
    public String showDashboard(@RequestParam(value = "action", required = false, defaultValue = "display") String action, 
                                HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/employees/login";

        model.addAttribute("currentAction", action);
        // Only fetch employees that belong to this specific user context
        model.addAttribute("employees", service.getEmployeesByUser(username));
        
        if (!model.containsAttribute("newEmployee")) {
            model.addAttribute("newEmployee", new Employee());
        }
        return "index";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute("newEmployee") Employee employee, BindingResult result, 
                                 HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/employees/login";

        if (result.hasErrors()) {
            model.addAttribute("currentAction", "create");
            model.addAttribute("employees", service.getEmployeesByUser(username));
            return "index";
        }
        try {
            User creator = userRepository.findById(username).orElseThrow();
            service.saveEmployee(employee, creator);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("currentAction", "create");
            return "index";
        }
        return "redirect:/employees?action=display";
    }

    @PostMapping("/raise-salary")
    public String raiseSalary(@RequestParam("id") Long id, @RequestParam("percentage") double percentage, 
                              HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/employees/login";

        try {
            boolean success = service.raiseSalary(id, percentage, username);
            if (!success) return "redirect:/employees?action=raise&error=RecordNotFound";
        } catch (IllegalArgumentException e) {
            return "redirect:/employees?action=raise&error=" + e.getMessage().replace(" ", "%20");
        }
        return "redirect:/employees?action=display";
    }

    @GetMapping("/exit")
    public String exitApplication(HttpSession session) {
        session.invalidate();
        return "exit";
    }
}