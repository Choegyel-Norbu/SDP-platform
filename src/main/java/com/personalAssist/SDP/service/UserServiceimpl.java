package com.personalAssist.SDP.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.enums.UserRole;
import com.personalAssist.SDP.model.Client;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.ClientRepository;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.util.PasswordEncoder;
import com.personalAssist.SDP.util.RoleType;
import com.personalAssist.SDP.util.UserWrapper;

@Service
public class UserServiceimpl implements UserService {

//	@Value("${app.default-password:OAUTH_DEFAULT}") 
	private String defaultPassword = "password@123";

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClientRepository clientRepository;

	@Override
	public UserDTO register(UserDTO userDTO) {
		User user = UserWrapper.toEntity(userDTO);
		user.setPassword(PasswordEncoder.encode(userDTO.getPassword()));
		user.setRole(UserRole.USER);

		return UserWrapper.toDTO(userRepository.save(user));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> updateUser(Long id, User updateUser) {
		return null;
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public String deleteUser(Long id) {
		User checkProduct = userRepository.findById(id).orElse(null);
		if (checkProduct != null) {
			userRepository.deleteById(id);
			return "Product id " + id + " deleted";
		}
		return "The product id " + id + " doesnot exist";
	}

	@Override
	public UserDTO addRole(String email, List<String> roleNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User verifyGoogleToken(String idTokenString) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
				GsonFactory.getDefaultInstance())
				.setAudience(Collections
						.singletonList("758224301470-h9ptb8u5m4vjinsm62acn2lqjjr04e2a.apps.googleusercontent.com"))
				.build();

		try {
			GoogleIdToken idToken = verifier.verify(idTokenString);
			if (idToken != null) {
				GoogleIdToken.Payload payload = idToken.getPayload();

				String email = payload.getEmail();
				String name = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");

				User user = userRepository.findByEmail(email);
				if (user == null) {
					user = new User();
					user.setEmail(email);
					user.setGoogleName(name);
					user.setGooglePictureUrl(pictureUrl);
					user.setPassword(PasswordEncoder.encode(defaultPassword));
					user.setRole(UserRole.USER);
					userRepository.save(user);
				} else {
					user.setGoogleName(name);
					user.setGooglePictureUrl(pictureUrl);
					userRepository.save(user);
				}

				return user;
			}
		} catch (Exception e) {
			System.out.println("Error @@@ " + e);
			e.printStackTrace(); // Show full stack trace
			throw new RuntimeException("User save failed", e);
		}
		return null;
	}

}
