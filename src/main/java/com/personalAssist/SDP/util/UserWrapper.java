package com.personalAssist.SDP.util;

import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.model.User;

public class UserWrapper {

	public static User toEntity(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		return user;
	}

	public static UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
	}

	public static ServiceRequestDTO toServiceDTO(ServiceRequest service) {
		return new ServiceRequestDTO(service.getId(), service.getServiceName(), service.getDescription(),
				service.getRate(), service.getRequestedDate(), service.getCreatedAt(), service.getPriority(),
				service.getRepeatFrequency(), service.getServiceType());
	}
}
