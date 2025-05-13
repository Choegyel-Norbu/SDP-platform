package com.personalAssist.SDP.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalAssist.SDP.dto.AddOnDTO;
import com.personalAssist.SDP.dto.AddressDTO;
import com.personalAssist.SDP.dto.BookingDTO;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ReviewDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.enums.Priority;
import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.Status;
import com.personalAssist.SDP.interfaces.BookingClientProjection;
import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ReviewProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.AddOn;
import com.personalAssist.SDP.model.Address;
import com.personalAssist.SDP.model.Booking;
import com.personalAssist.SDP.model.Client;
import com.personalAssist.SDP.model.Review;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.model.ServiceStatus;
import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.repository.AddOnRepository;
import com.personalAssist.SDP.repository.AddressRepository;
import com.personalAssist.SDP.repository.BookingRepository;
import com.personalAssist.SDP.repository.ClientRepository;
import com.personalAssist.SDP.repository.ReviewRepository;
import com.personalAssist.SDP.repository.ServiceRequestRepository;
import com.personalAssist.SDP.repository.ServiceStatusRepository;
import com.personalAssist.SDP.repository.UserRepository;
import com.personalAssist.SDP.util.DiscountResult;
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

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	AddOnRepository addOnRepository;

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
		ServiceRequest serviceRequest = serviceRequestRepository.save(service);

		if (serviceRequest != null) {
			if (status.equalsIgnoreCase("completed")) {
				Client client = serviceRequestRepository.findClientByServiceRequestId(serviceId);
				client.setCanReview(true);
				clientRepository.save(client);
				return true;
			}
		}
		return false;
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
		switch (option) {
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

	@Override
	public Review clientReview(ReviewDTO reviewDTO, Long clientId) {
		Client client = clientRepository.findById(clientId).orElse(null);

		Review review = new Review();
		review.setClient(client);
		review.setComment(reviewDTO.getComment());
		review.setRating(reviewDTO.getRating());

		return reviewRepository.save(review);
	}

	@Override
	public List<ReviewProjection> getReview() {
		return reviewRepository.getReview();
	}

	@Override
	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}

	@Override
	public Review updateReview(ReviewDTO reviewDTO) {
		Review review = reviewRepository.findById(reviewDTO.getId()).orElse(null);
		review.setComment(reviewDTO.getComment());
		review.setRating(reviewDTO.getRating());

		return reviewRepository.save(review);
	}

	@Override
	public DiscountResult scheduleBooking(BookingDTO bookingDTO) {
		Client client = clientRepository.loadClientByUserId(bookingDTO.getUserId());
		Booking booking = new Booking();
		List<AddOn> dtoToAddOn = new ArrayList<>();

		booking.setClient(client);
		ServiceRequest service = serviceRequestRepository
				.save(UserWrapper.toServiceRequest(bookingDTO.getServiceRequest()));
		booking.setServiceRequest(service);
		bookingFrequency(booking, bookingDTO.getFrequency());

		booking.setStatus(Status.PENDING);
		booking.setSpecialInstructions(bookingDTO.getSpecialInstructions());
		booking.setStartTime(bookingDTO.getStartTime());
		booking.setEndTime(bookingDTO.getEndTime());
		booking.setNumberOfBedrooms(bookingDTO.getNumberOfBedrooms());
		booking.setNumberOfBathrooms(bookingDTO.getNumberOfBathrooms());
		booking.setDiscountAmount(0);

		for (AddOnDTO addon : bookingDTO.getAddOns()) {
			dtoToAddOn.add(UserWrapper.toAddOn(addon));
		}

		double totalAmount = calcTotalPrice(bookingDTO, dtoToAddOn);
		booking.setTotalAmount(totalAmount);

		double discountAmount = calcDiscount(bookingDTO.getFrequency(), totalAmount).getDiscountedPrice();
		booking.setDiscountAmount(discountAmount);
		booking.setAmountAfterDiscount(totalAmount - discountAmount);

		Booking saveBooking = bookingRepository.save(booking);

		for (AddOn addon : dtoToAddOn) {
			addon.setBooking(booking);
			addOnRepository.save(addon);
		}
		DiscountResult obj = calcDiscount(bookingDTO.getFrequency(), totalAmount);
		obj.setAmountAfterDiscount(booking.getAmountAfterDiscount());
		return obj;
	}

	@Override
	public DiscountResult reviewBooking(BookingDTO dto) {
		List<AddOn> dtoToAddOn = new ArrayList<>();

		for (AddOnDTO addon : dto.getAddOns()) {
			dtoToAddOn.add(UserWrapper.toAddOn(addon));
		}
		double totalAmount = calcTotalPrice(dto, dtoToAddOn);
		DiscountResult obj = calcDiscount(dto.getFrequency(), totalAmount);
		obj.setAmountAfterDiscount(totalAmount - obj.getDiscountedPrice());
		obj.setTotalAmount(totalAmount);
		return obj;
	}

	private double calcTotalPrice(BookingDTO booking, List<AddOn> addons) {
		double totalAddOnAmount = 0;
		for (AddOn addon : addons) {
			totalAddOnAmount += addon.getPrice();
		}
		return booking.getServiceRequest().getItemPrice() + totalAddOnAmount;
	}

	private DiscountResult calcDiscount(String freq, double totalAmount) {
		double discoutedPrice;
		switch (freq.toLowerCase()) {
		case "daily":
			return new DiscountResult(0, "There is no discount for daily serivces");
		case "weekly":
			discoutedPrice = totalAmount * 0.15;
			return new DiscountResult(discoutedPrice, "15% off for weekly service");
		case "fortnightly":
			discoutedPrice = totalAmount * 0.10;
			return new DiscountResult(discoutedPrice, "10% off for fortnightly service");
		case "monthly":
			discoutedPrice = totalAmount * 0.05;
			return new DiscountResult(discoutedPrice, "5% off for monthly service");
		default:
			return new DiscountResult(0, "No discount applied");
		}

	}

	private void bookingFrequency(Booking booking, String frequency) {
		switch (frequency.toLowerCase()) {
		case "daily":
			booking.setFrequency(RepeatFrequency.DAILY);
			break;
		case "weekly":
			booking.setFrequency(RepeatFrequency.WEEKLY);
			break;
		case "fortnightly":
			booking.setFrequency(RepeatFrequency.FORTNIGHTLY);
			break;
		case "monthly":
			booking.setFrequency(RepeatFrequency.MONTHLY);
			break;
		}
	}

	@Override
	public List<BookingClientProjection> getBookingsForClient(Long userId) {
		return bookingRepository.findBookingClientDetails(userId);
	}

	@Override
	public boolean updateBookingDetails(BookingDTO bookingDTO) {
		Booking booking = bookingRepository.findById(bookingDTO.getId()).orElse(null);

		bookingFrequency(booking, bookingDTO.getFrequency());
		booking.setStartTime(bookingDTO.getStartTime());
		booking.setSpecialInstructions(bookingDTO.getSpecialInstructions());

		return bookingRepository.save(booking) != null;
	}

	@Override
	public List<BookingClientProjection> getAllBookings() {
		return bookingRepository.getAllBookings();
	}

	@Override
	public boolean updateAdminBookingStatus(Long bookingId, String status, String cancellationReason) {
		Booking booking = bookingRepository.findById(bookingId).orElse(null);

		return bookingRepository.save(bookingConfirmation(status, booking, cancellationReason)) != null;
	}

	public Booking bookingConfirmation(String status, Booking booking, String cancellationReason) {

		switch (status.toLowerCase()) {
		case ("cancelled"):
			booking.setStatus(Status.CANCELLED);
			booking.setCancellationReason(cancellationReason);
			break;
		case ("confirmed"):
			booking.setStatus(Status.CONFIRMED);
			break;
		case ("completed"):
			booking.setStatus(Status.COMPLETED);
			break;
		case ("assigned"):
			booking.setStatus(Status.ASSIGNED);
			break;
		}
		return booking;
	}

}
