package com.personalAssist.SDP.service;

import java.util.List;

import com.personalAssist.SDP.dto.AddressDTO;
import com.personalAssist.SDP.dto.BookingDTO;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ReviewDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ReviewProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.Review;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.util.DiscountResult;

public interface ClientService {

	public boolean associateClient(ClientDTO clientDTO);

	public boolean initiateServiceRequest(ServiceRequestDTO dto);

	public ClientAddressProjection getClientById(Long userId);

	public ServiceRequestDTO updateServiceRequest(ServiceRequestDTO dto);

	public List<ServiceRequestDTO> getAllServices();

	public List<ServiceRequestProjection> getServices();

	public List<ServiceRequestProjection> findAllServicesForClientId(Long id);

	public boolean clientSet(Long userId);

	public boolean updateClient(ClientDTO clientDTO);

	public List<ServiceRequestProjection> sortServices(String option);

	public ClientAddressProjection getClientAddressFromServiceId(Long serviceId);

	public long countServiceRequest();

	public Review clientReview(ReviewDTO review, Long clientId);

	public List<ReviewProjection> getReview();

	public void deleteReview(Long reviewId);

	public Review updateReview(ReviewDTO reviewDTO);

	public boolean updateServiceStatus(Long serviceId, String status);
	
	public DiscountResult scheduleBooking(BookingDTO bookngDTO);
	
	public DiscountResult reviewBooking(BookingDTO dto);
	
	public boolean confirmBooking(Long id, String status);

}
