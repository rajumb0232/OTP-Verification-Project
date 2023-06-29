package com.smtp.otp_verification_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	
	@ExceptionHandler
	public ResponseEntity<String> UserNotFoundById(UserNotFoundByIdException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - User not found with the requested Id!!",
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> WrongPassword(WrongPasswordException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - Wrong Password!!",
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> UserNotFoundByEmail(UserNotFoundByEmailException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - User not found with the requested Email!!",
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> UserNotLoggedIn(UserNotLoggedInException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - User is not logged-in!!",
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> WrongOTP(WrongOTPException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - Wrong OTP!!",
				HttpStatus.BAD_REQUEST);
	}
}
