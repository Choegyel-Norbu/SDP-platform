package com.personalAssist.SDP.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

	
}

