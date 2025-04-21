package com.personalAssist.SDP.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personalAssist.SDP.dto.LoginRequestDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.dto.UserResponseDTO;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.service.EmailService;
import com.personalAssist.SDP.util.JwtUtil;
import com.personalAssist.SDP.util.PasswordEncoder;
import com.personalAssist.SDP.util.UserWrapper;
import com.personalAssist.SDP.wrapper.LoginApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByEmail(loginRequestDTO.getEmail());

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}

		if (!PasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credetials");
		}

		String token = JwtUtil.generateToken(user.getEmail());
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
//		User user = userRepository.findByEmail(loginRequestDTO.getEmail());
//
//		if (user == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
//		}
//
//		if (!PasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credetials");
//		}
//
//		emailService.generateOtpAndSendOtp(user.getEmail());
//
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password verified. OTP sent to email.");
//
//	}

	@PostMapping("/sendOtp")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
		emailService.generateOtpAndSendOtp(email);
		return ResponseEntity.ok("OTP sent to email.");
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		boolean isValid = emailService.validateOtp(email, otp);
		if (!isValid) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");

		}
		User user = userRepository.findByEmail(email);

		String token = JwtUtil.generateToken(user.getEmail());
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

}
