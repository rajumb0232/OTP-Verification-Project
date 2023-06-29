package com.smtp.otp_verification_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class WrongPasswordException extends RuntimeException {
private String message;
}
