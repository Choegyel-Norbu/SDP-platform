package com.personalAssist.SDP.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personalAssist.SDP.dto.LoginRequestDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.dto.UserResponseDTO;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.util.JwtUtil;
import com.personalAssist.SDP.util.PasswordEncoder;
import com.personalAssist.SDP.util.UserWrapper;
import com.personalAssist.SDP.wrapper.LoginApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;

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

}
