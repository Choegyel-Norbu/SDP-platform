package com.personalAssist.SDP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer{

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:8081", "http://192.168.235.149:8081")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedHeaders("*");
	}
}
