package com.personalAssist.SDP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.model.User;

import ch.qos.logback.core.net.server.Client;

@Component
public interface UserService {

	public UserDTO register(UserDTO userDTO);
	public List<User> getAllUsers();
	public User getById(Long id);
	public String deleteUser(Long id);
	public Optional<User> updateUser(Long id, User updateUser);
	public UserDTO addRole(String email, List<String> roleNames);
	
	
}
	