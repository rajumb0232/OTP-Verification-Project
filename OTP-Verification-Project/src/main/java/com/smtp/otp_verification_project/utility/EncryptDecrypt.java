package com.smtp.otp_verification_project.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptDecrypt {
	
	// Password encryption and verification
	
	public static String encryptPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
	
	public static Boolean verifyPassword(String plainPassword, String encryptedPassword) {
		return BCrypt.checkpw(plainPassword, encryptedPassword);
	}
	
	
	// Email encryption and decryption 
	
    private static final String secretKey = "ShriKrishnaIskon";

    public static SecretKeySpec initialize() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128, new SecureRandom());
//        SecretKey secretKey = keyGenerator.generateKey();
       return new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    public static String encryptEmail(String plainEmail) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, initialize());
        byte[] encryptedBytes = cipher.doFinal(plainEmail.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptEmail(String encryptedEmail) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, initialize());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedEmail));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
