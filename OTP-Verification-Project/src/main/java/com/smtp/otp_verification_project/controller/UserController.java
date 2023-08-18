package com.smtp.otp_verification_project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smtp.otp_verification_project.dto.UserResponse;
import com.smtp.otp_verification_project.entity.User;
import com.smtp.otp_verification_project.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", originPatterns = "http://127.0.0.1:5501")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user) throws Exception{
		return userService.saveUser(user);
	}
	
	@GetMapping("/userId/{userId}")
	public ResponseEntity<UserResponse> findUserById(@PathVariable int userId, HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession(false);
		return userService.findUserById(userId, session);
	}
	
	@GetMapping("/userEmail/{userEmail}/userPassword/{userPassword}")
	public ResponseEntity<Object> userLogin(@PathVariable String userEmail,
			@PathVariable String userPassword, HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession(true);
		return userService.findUserByEmailByPassword(userEmail, userPassword, session);
	}
	
	@GetMapping("/otp/{otpToken}")
	public ResponseEntity<Object> verifyOTP(@PathVariable String otpToken, HttpServletRequest request){
//		HttpSession session = request.getSession(false);
//		if(session!=null) {
//			System.err.println("session otp in controller: "+session.getAttribute("otp"));
			return userService.verifyOTP(otpToken, request.getSession());
//		}else {
//			System.err.println("No session found!!");
//			return new ResponseEntity<Object> ("No session found!!", HttpStatus.BAD_REQUEST);
//		}
		
	}
	
//	@CrossOrigin
	@GetMapping("/logout")
	public ResponseEntity<User> userLogout(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		return userService.userLogout(session);
	}
}
