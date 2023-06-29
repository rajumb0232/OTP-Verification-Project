package com.smtp.otp_verification_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smtp.otp_verification_project.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public Optional<User> findByUserEmail(String userEmail);

}
