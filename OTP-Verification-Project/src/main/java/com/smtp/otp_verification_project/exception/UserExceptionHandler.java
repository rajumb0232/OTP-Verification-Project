package com.smtp.otp_verification_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smtp.otp_verification_project.utility.ErrorStructure;

@RestControllerAdvice
public class UserExceptionHandler {

	
	@ExceptionHandler
	public ResponseEntity<String> UserNotFoundById(UserNotFoundByIdException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - User not found with the requested Id!!",
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> WrongPassword(WrongPasswordException ex){
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setMessage(ex.getMessage());
		structure.setRootCause("Invalid Password!!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> UserNotFoundByEmail(UserNotFoundByEmailException ex){
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setMessage(ex.getMessage());
		structure.setRootCause("Invalid Email!!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> UserNotLoggedIn(UserNotLoggedInException ex){
		return new ResponseEntity<String> (ex.getMessage()+
				"\nRootCause - User is not logged-in!!",
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> WrongOTP(WrongOTPException ex){
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setMessage(ex.getMessage());
		structure.setRootCause("Invalid OTP!!");
		return new ResponseEntity<ErrorStructure>(structure, HttpStatus.BAD_REQUEST);
	}
}
