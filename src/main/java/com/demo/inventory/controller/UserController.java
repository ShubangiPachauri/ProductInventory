package com.demo.inventory.controller;

import com.demo.inventory.model.User;
import com.demo.inventory.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// Register endpoint
	@PostMapping("/register")
	public String registerUser(@RequestParam String username,
	                            @RequestParam String email,
	                            @RequestParam String password) {
	    return userService.register(username, email, password);
	}

	// Login endpoint
	@PostMapping("/login")
	public String loginUser(@RequestParam String username,
	                         @RequestParam String password) {
	    return userService.login(username, password);
   }
	// Get all users endpoint
	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userService.getAllUsers();
	}
}