package com.smtp.otp_verification_project.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smtp.otp_verification_project.entity.User;
import com.smtp.otp_verification_project.repository.UserRepo;

@Repository
public class UserDao {
	
	@Autowired
	private UserRepo userRepo;
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	
	public Optional<User> findUser(int userId) {
		return userRepo.findById(userId);
	}
	
	public Optional<User> findUserByEmail(String userEmail){
		return  userRepo.findByUserEmail(userEmail);
	}
}
