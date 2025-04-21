package com.personalAssist.SDP.service;

import java.util.List;

import com.personalAssist.SDP.dto.AddressDTO;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.ServiceRequest;

public interface ClientService {
	
	public boolean associateClient(ClientDTO clientDTO);
	
	public boolean initiateServiceRequest(ServiceRequestDTO dto);
	public ClientAddressProjection getClientById(Long userId);
	public ServiceRequest updateServiceRequest(ServiceRequestDTO dto);
	public List<ServiceRequestDTO> getAllServices(); 
	public List<ServiceRequestProjection> getServices(); 
	public List<ServiceRequestProjection> findAllServicesForClientId(Long id); 

	
	public long countServiceRequest();

	
	public boolean updateServiceStatus(Long serviceId, String status);

}
