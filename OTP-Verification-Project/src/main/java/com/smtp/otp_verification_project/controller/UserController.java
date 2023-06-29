package com.smtp.otp_verification_project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smtp.otp_verification_project.entity.User;
import com.smtp.otp_verification_project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user){
		return userService.saveUser(user);
	}
	
	@CrossOrigin
	@GetMapping("/userId/{userId}")
	public ResponseEntity<User> findUserById(@PathVariable int userId, HttpSession session){
		return userService.findUserById(userId, session);
	}
	
	@CrossOrigin
	@GetMapping("/userEmail/{userEmail}/userPassword/{userPassword}")
	public ResponseEntity<Object> userLogin(@PathVariable String userEmail,
			@PathVariable String userPassword, HttpSession session){
		return userService.findUserByEmailByPassword(userEmail, userPassword, session);
	}
	
	@CrossOrigin
	@GetMapping("/otp/{otpToken}")
	public ResponseEntity<User> verifyOTP(@PathVariable String otpToken, HttpSession session){
		return userService.verifyOTP(otpToken, session);
	}
	
	@CrossOrigin
	@GetMapping("/logout")
	public ResponseEntity<User> userLogout(HttpSession session){
		return userService.userLogout(session);
	}
}
