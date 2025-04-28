package com.personalAssist.SDP.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.dto.UserResponseDTO;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.service.EmailService;
import com.personalAssist.SDP.service.UserService;
import com.personalAssist.SDP.util.JwtUtil;
import com.personalAssist.SDP.wrapper.LoginApiResponse;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

		if (checkUserEmail(userDTO.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
		}

		UserDTO result = userService.register(userDTO);
		UserResponseDTO dto = new UserResponseDTO();
		dto.setEmail(result.getEmail());
		dto.setId(result.getId());
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}

	@PostMapping("/sendOtp")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
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

		String token = JwtUtil.generateToken(user.getEmail());
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		return ResponseEntity.ok(new LoginApiResponse(token, dto));
	}

	@GetMapping("/getUsers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/getUser/{id}")
	public User getById(@PathVariable Long id) {
		return userService.getById(id);
	}

	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@PatchMapping("/updateUser/{id}")
	public Optional<User> updateUser(@PathVariable Long id, @RequestBody User product) {
		return userService.updateUser(id, product);
	}

	private boolean checkUserEmail(String email) {
		return userRepository.findByEmail(email) != null;
	}

}
