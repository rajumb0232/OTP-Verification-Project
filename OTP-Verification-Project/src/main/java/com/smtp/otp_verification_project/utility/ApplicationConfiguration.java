package com.smtp.otp_verification_project.utility;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
	
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Set the mapping path for which CORS configuration should apply
                .allowedOrigins("http://127.0.0.1:5500") // Set the allowed origin(s) for requests
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Set the allowed HTTP method(s)
                .allowedHeaders("*") // Set the allowed request header(s)
                .allowCredentials(true); // Allow inclusion of cookies in CORS requests
    }
}
