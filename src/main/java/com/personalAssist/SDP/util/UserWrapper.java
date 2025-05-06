package com.personalAssist.SDP.util;

import com.personalAssist.SDP.dto.AddOnDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.dto.UserDTO;
import com.personalAssist.SDP.model.AddOn;
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

	public static ServiceRequestDTO toServiceResponseDTO(ServiceRequest service) {
		return new ServiceRequestDTO(service.getId(), service.getServiceName(), service.getDescription(),
				service.getRequestedDate(), service.getPriority(), service.getRepeatFrequency(),
				service.getServiceType()

		);
	}
	
	public static ServiceRequest toServiceRequest(ServiceRequestDTO dto) {
		return new ServiceRequest(dto.getServiceName(), dto.getDescription(), dto.getServiceType());
	}
	
	public static AddOn toAddOn(AddOnDTO dto) {
		return new AddOn(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getDurationMinutes());
	}
}
