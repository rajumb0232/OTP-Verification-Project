package com.smtp.otp_verification_project.service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smtp.otp_verification_project.dao.UserDao;
import com.smtp.otp_verification_project.dto.UserResponse;
import com.smtp.otp_verification_project.entity.User;
import com.smtp.otp_verification_project.exception.UserNotFoundByEmailException;
import com.smtp.otp_verification_project.exception.UserNotLoggedInException;
import com.smtp.otp_verification_project.exception.WrongOTPException;
import com.smtp.otp_verification_project.exception.WrongPasswordException;
import com.smtp.otp_verification_project.utility.EncryptDecrypt;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ResponseEntity<User> saveUser(User user) throws Exception {
		String encryptedPassword = EncryptDecrypt.encryptPassword(user.getUserPassword());
		String encryptedEmail = EncryptDecrypt.encryptEmail(user.getUserEmail());
		user.setUserPassword(encryptedPassword);
		user.setUserEmail(encryptedEmail);
		user = userDao.saveUser(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	public ResponseEntity<UserResponse> findUserById(int userId, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			UserResponse response = modelMapper.map(user, UserResponse.class);
			return new ResponseEntity<UserResponse>(response, HttpStatus.FOUND);
		} else {
			throw new UserNotLoggedInException("User is not logged-in!!");
		}
	}

	public ResponseEntity<Object> findUserByEmailByPassword(String userEmail, String userPassword,
			HttpSession session) throws Exception{
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		} else {
			String encryptedEmail = EncryptDecrypt.encryptEmail(userEmail);
			Optional<User> optional = userDao.findUserByEmail(encryptedEmail);
			if (optional.isEmpty()) {
				throw new UserNotFoundByEmailException("Failed to find the User!!");
			} else {
				User exUser = optional.get();
				if (EncryptDecrypt.verifyPassword(userPassword, exUser.getUserPassword())) {
					String otpToken = otpGenerator();
					System.err.println("recieved OTP:"+ otpToken);
					session.setAttribute("otp", otpToken);
					System.err.println("OTP in session: "+session.getAttribute("otp"));
					try {
						session.removeAttribute("userEmail");
					}finally {
						session.setAttribute("userEmail", exUser.getUserEmail());
						sendOtpEmail(userEmail, otpToken);
						System.err.println("OTP sent!!");
					} 
					return new ResponseEntity<>("Please send back the request to verify OTP sent on your mail.",
							HttpStatus.OK);
				} else
					throw new WrongPasswordException("Failed to find the User!!");
			}
		}

	}

	public ResponseEntity<Object> verifyOTP(String otpToken, HttpSession session) {
		String otp = (String) session.getAttribute("otp");
		System.err.println(otp);
		if(otp==null) {
			throw new UserNotLoggedInException("Failed to verify OTP!!");
		}else {
			Optional<User> optional = userDao.findUserByEmail((String)session.getAttribute("userEmail"));
			if(optional.isPresent()) {
				if(otp.equals(otpToken)) {
					session.removeAttribute("userEmail");
					session.removeAttribute("otp");
					session.setAttribute("user", optional.get());
					return new ResponseEntity<>(optional.get(), HttpStatus.OK);
				}else {
					throw new WrongOTPException("Failed to verify OTP!!");
					}
			}
			throw new UserNotFoundByEmailException("Failed to verify OTP!!");
		}
		
	}
	
	public ResponseEntity<User> userLogout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null) {
			session.removeAttribute("user");
			return new ResponseEntity<User> (user, HttpStatus.OK);
		}else {
			return null;
		}
	}

	private void sendOtpEmail(String recipientEmail, String otpToken) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipientEmail);
		message.setSubject("OTP Verification");
		message.setText("Your OTP: " + otpToken);
		message.setSentDate(new Date());
		javaMailSender.send(message);
	}

	private static String otpGenerator() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}



}
