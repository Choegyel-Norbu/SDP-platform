package com.personalAssist.SDP.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.personalAssist.SDP.dto.LoginRequestDTO;
import com.personalAssist.SDP.dto.UserResponseDTO;
import com.personalAssist.SDP.enums.UserRole;
import com.personalAssist.SDP.model.Client;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.ClientRepository;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.service.EmailService;
import com.personalAssist.SDP.service.UserService;
import com.personalAssist.SDP.util.JwtUtil;
import com.personalAssist.SDP.util.PasswordEncoder;
import com.personalAssist.SDP.wrapper.LoginApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByEmail(loginRequestDTO.getEmail());

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}

//		if (!PasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credetials");
//		}

		String token = JwtUtil.generateToken(user.getEmail());
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

	@PostMapping("/google")
	public ResponseEntity<?> gogleAuthLogin(@RequestBody Map<String, String> request) {
		String googleToken = request.get("credential");

		User user = userService.verifyGoogleToken(googleToken);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
		}
		String token = JwtUtil.generateToken(user.getEmail());
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		dto.setName(user.getGoogleName());
		dto.setPictureURL(user.getGooglePictureUrl());
		boolean state = clientDetailSet(user.getId());
		if(clientDetailSet(user.getId())) {
			dto.setDetailSet(true);
		}else {
			dto.setDetailSet(false);
		}

		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

	@PostMapping("/login_google")
	public ResponseEntity<?> googleAuthFirebaseLogin(@RequestBody Map<String, String> request) {
		String googleToken = request.get("idToken");
		System.out.println("Token - " + googleToken);

		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(googleToken);
			String email = decodedToken.getEmail();

			User user = userRepository.findByEmail(email);
			if (user == null) {
				user = new User();
				user.setEmail(email);
				user.setPassword("Default-password");
				user.setGoogleName(decodedToken.getName());
				user.setRole(UserRole.USER);
				user.setRegisterFlag(true);
				user.setGooglePictureUrl(decodedToken.getPicture());
				userRepository.save(user);
			}

			String token = JwtUtil.generateToken(user.getEmail());
			UserResponseDTO dto = new UserResponseDTO();
			dto.setId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setRole(user.getRole());
			dto.setName(user.getGoogleName());
			dto.setRegisterFlag(true);
			dto.setPictureURL(user.getGooglePictureUrl());
			if(clientDetailSet(user.getId())) {
				dto.setDetailSet(true);
			}else {
				dto.setDetailSet(false);
			}

			return ResponseEntity.ok(new LoginApiResponse(token, dto));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Firebase token");

		}
	}

	@PostMapping("/sendOtp")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
//		User user = userRepository.findByEmail(email);
//		if(user != null) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exits");
//		}
//		
		emailService.generateOtpAndSendOtp(email);
		return ResponseEntity.status(HttpStatus.CREATED).body("OTP sent to email: " + email);
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		boolean isValid = emailService.validateOtp(email, otp);
		if (!isValid) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");

		}
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setEmail(email);
			user.setPassword("Default-password");
			user.setRole(UserRole.USER);
			user.setRegisterFlag(false);
			userRepository.save(user);
		}
		String token = JwtUtil.generateToken(email);
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		dto.setRegisterFlag(false);
		if(clientDetailSet(user.getId())) {
			dto.setDetailSet(true);
		}else {
			dto.setDetailSet(false);
		}
		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

	private boolean clientDetailSet(Long userId) {
		return clientRepository.loadClientByUserId(userId) != null;
	}


}
