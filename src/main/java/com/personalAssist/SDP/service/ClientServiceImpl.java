package com.personalAssist.SDP.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalAssist.SDP.dto.AddressDTO;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.enums.Priority;
import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.Status;
import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.Address;
import com.personalAssist.SDP.model.Client;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.model.ServiceStatus;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.AddressRepository;
import com.personalAssist.SDP.repository.ClientRepository;
import com.personalAssist.SDP.repository.ServiceRequestRepository;
import com.personalAssist.SDP.repository.ServiceStatusRepository;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.util.UserWrapper;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	ServiceStatusRepository serviceStatusRepository;

	@Override
	public boolean associateClient(ClientDTO clientDTO) {
		User user = userRepository.findById(clientDTO.getUserId()).orElse(null);
		Client client = new Client();
		client.setFirstName(clientDTO.getFirstName());
		client.setLastName(clientDTO.getLastName());
		client.setPhoneNumber(clientDTO.getPhoneNumber());
		client.setUser(user);
		client.setAddress(saveAddress(clientDTO.getAddressDTO()));

		Client savedClient = clientRepository.save(client);
		return savedClient != null;
	}

	@Override
	public boolean initiateServiceRequest(ServiceRequestDTO dto) {
		Client client = clientRepository.loadClientByUserId(dto.getUserId());

		ServiceRequest service = new ServiceRequest();
		service.setClient(client);
		service.setDescription(dto.getDescription());
		service.setRequestedDate(dto.getRequestedDate());
		service.setServiceName(dto.getServiceName());
		service.setRate(dto.getRate());
		withPriority(service, dto.getPriority());
		service.setStatus(associateStatus(service));
		serviceFrequency(service, dto.getRepeatFrequency());
		service.setServiceType(dto.getServiceType());

		return serviceRequestRepository.save(service) != null;
	}

	private void withPriority(ServiceRequest service, String priority) {
		switch (priority.toLowerCase()) {
		case "low":
			service.setPriority(Priority.LOW);
			break;
		case "normal":
			service.setPriority(Priority.NORMAL);
			break;
		case "high":
			service.setPriority(Priority.HIGH);
			break;
		default:
			service.setPriority(Priority.LOW);
			break;
		}
	}

	private void serviceFrequency(ServiceRequest service, String frequency) {
		switch (frequency.toLowerCase()) {
		case "daily":
			service.setRepeatFrequency(RepeatFrequency.DAILY);
			break;
		case "weekly":
			service.setRepeatFrequency(RepeatFrequency.WEEKLY);
			break;
		case "fortnightly":
			service.setRepeatFrequency(RepeatFrequency.FORTNIGHTLY);
			break;
		}
	}

	@Override
	public boolean updateServiceStatus(Long serviceId, String status) {
		ServiceStatus serviceStatus = serviceStatusRepository.fetchStatusByName(status);

		ServiceRequest service = serviceRequestRepository.findById(serviceId).orElse(null);
		service.setStatus(serviceStatus);
		return serviceRequestRepository.save(service) != null;
	}

	private ServiceStatus associateStatus(ServiceRequest service) {
		return serviceStatusRepository.fetchPending();
	}

	private Address saveAddress(AddressDTO dto) {
		Address address = new Address();
		address.setState(dto.getState());
		address.setStreetAddress(dto.getStreetAddress());
		address.setStreetType(dto.getStreetType());
		address.setSubarb(dto.getSubarb());
		address.setUnit(dto.getUnit());
//		address.setUnitNumber(dto.getUnitNumber());

		return addressRepository.save(address);
	}

	@Override
	public ServiceRequest updateServiceRequest(ServiceRequestDTO dto) {
		ServiceRequest service = serviceRequestRepository.findById(dto.getServiceRequestId()).orElse(null);
		service.setDescription(dto.getDescription());
		service.setRequestedDate(dto.getRequestedDate());
		service.setServiceName(dto.getServiceName());
		service.setRate(dto.getRate());
		withPriority(service, dto.getPriority());
		serviceFrequency(service, dto.getRepeatFrequency());

		return serviceRequestRepository.save(service);
	}

	@Override
	public List<ServiceRequestDTO> getAllServices() {
		List<ServiceRequest> dto = serviceRequestRepository.findAll();
		List<ServiceRequestDTO> responseDTO = new ArrayList<>();
		for (ServiceRequest service : dto) {
			ServiceRequestDTO convert = UserWrapper.toServiceDTO(service);
			responseDTO.add(convert);
		}

		return responseDTO;
	}

	@Override
	public List<ServiceRequestProjection> getServices() {
		return serviceRequestRepository.findAllServiceRequestsWithStatus();

	}

	@Override
	public long countServiceRequest() {
		return serviceRequestRepository.count();
	}

	@Override
	public ClientAddressProjection getClientById(Long userId) {
		return serviceRequestRepository.fetchClientWithId(userId);
	}

	@Override
	public List<ServiceRequestProjection> findAllServicesForClientId(Long id) {
		return serviceRequestRepository.findAllServicesForClientId(id);
	}

}
