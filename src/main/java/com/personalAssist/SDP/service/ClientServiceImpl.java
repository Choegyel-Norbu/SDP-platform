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

		return addressRepository.save(address);
	}

	@Override
	public ServiceRequestDTO updateServiceRequest(ServiceRequestDTO dto) {
		ServiceRequest service = serviceRequestRepository.findById(dto.getServiceRequestId()).orElse(null);
		service.setDescription(dto.getDescription());
		service.setRequestedDate(dto.getRequestedDate());
		service.setServiceName(dto.getServiceName());
		service.setRate(dto.getRate());
		service.setServiceType(dto.getServiceType());
		withPriority(service, dto.getPriority());
		serviceFrequency(service, dto.getRepeatFrequency());

		ServiceRequest request = serviceRequestRepository.save(service);
		
		return UserWrapper.toServiceResponseDTO(service);
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
	
	@Override
	public boolean clientSet(Long userId) {
		return clientRepository.clientSet(userId) != null;
	}

	@Override
	public boolean updateClient(ClientDTO clientDTO) {
		Client client = clientRepository.loadClientByUserId(clientDTO.getUserId()); 
		Address address = updateAddress(client.getAddress(), clientDTO.getAddressDTO());
		client.setFirstName(clientDTO.getFirstName());
		client.setLastName(clientDTO.getLastName());
		client.setPhoneNumber(clientDTO.getPhoneNumber());
		client.setAddress(address);
		return clientRepository.save(client) != null;
	}
	
	private Address updateAddress(Address clientAddress, AddressDTO DtoAddress) {
		clientAddress.setState(DtoAddress.getState());
		clientAddress.setStreetAddress(DtoAddress.getStreetAddress());
		clientAddress.setStreetType(DtoAddress.getStreetType());
		clientAddress.setSubarb(DtoAddress.getSubarb());
		clientAddress.setUnit(DtoAddress.getUnit());
		
		return clientAddress;
	}

	@Override
	public List<ServiceRequestProjection> sortServices(String option) {
		switch (option){
		case "date-asc":
			return serviceRequestRepository.oldestDate();
		case "date-desc":
			return serviceRequestRepository.closestDate();
		case "priority-high":
			return serviceRequestRepository.priorityHighToLow();
		case "status":
			return serviceRequestRepository.status();
		}
		return null;
	}

	@Override
	public ClientAddressProjection getClientAddressFromServiceId(Long serviceId) {
		return clientRepository.getClientAddressFromServiceId(serviceId);
	}
}











