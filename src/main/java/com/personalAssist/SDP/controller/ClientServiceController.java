package com.personalAssist.SDP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personalAssist.SDP.dto.BookingDTO;
import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ReviewDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ReviewProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.Review;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.repository.ServiceRequestRepository;
import com.personalAssist.SDP.service.ClientService;
import com.personalAssist.SDP.util.DiscountResult;

@RestController
@RequestMapping("/api")
public class ClientServiceController {

	@Autowired
	ClientService clientService;
	
	@Autowired
	ServiceRequestRepository serviceRepository;

	@PostMapping("/addClient")
	public ResponseEntity<?> addClient(@RequestBody ClientDTO clientDTO) {
		Boolean client = clientService.associateClient(clientDTO);

		if (client) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}
	
	@PutMapping("/updateClient")
	public ResponseEntity<?> updateClient(@RequestBody ClientDTO clientDTO) {
		Boolean client = clientService.updateClient(clientDTO);

		if (client) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Error updating client");
	}
	
	@GetMapping("getClientWithId/{id}")
	public ResponseEntity<ClientAddressProjection> fetchClientWithId(@PathVariable Long id) {
		ClientAddressProjection client = clientService.getClientById(id);
		if (client != null) {
			return ResponseEntity.ok().body(client);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		
	}
	
	@GetMapping("clientSet/{userId}")
	public ResponseEntity<?> isClientSet(@PathVariable Long userId){
		boolean clientSet = clientService.clientSet(userId);
		if(clientSet) {
			return ResponseEntity.ok().body(clientSet);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(clientSet);
	}

	@PostMapping("/serviceRequest")
	public ResponseEntity<?> initiateServiceRequest(@RequestBody ServiceRequestDTO dto) {
		boolean response = clientService.initiateServiceRequest(dto);

		if (response) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}

	@GetMapping("/getServicesForClient/{id}")
	public ResponseEntity<?> findAllServicesForClientId(@PathVariable Long id){
		List<ServiceRequestProjection> clientServices = clientService.findAllServicesForClientId(id);
		
		if(clientServices != null) {
			return ResponseEntity.ok().body(clientServices);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}
	
	@PutMapping("/updateService")
	public ResponseEntity<?> updateServiceRequest(@RequestBody ServiceRequestDTO dto) {
		ServiceRequestDTO service = clientService.updateServiceRequest(dto);
		if (service != null) {
			return ResponseEntity.ok().body(service);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateServiceStatus(@PathVariable Long id, @RequestParam("status") String status) {
		boolean response = clientService.updateServiceStatus(id, status);

		if (response) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}

	@GetMapping("/getServices")
	public List<ServiceRequestDTO> getServices() {
		return clientService.getAllServices();
	}
	
	@GetMapping("/getAllServices")
	public List<ServiceRequestProjection> getAllServices() {
		return clientService.getServices();
	}
	
	@GetMapping("/countServiceRequest")
	public long countServiceRequest() {
		return clientService.countServiceRequest();
	}
	
	@DeleteMapping("/deleteServiceRequest/{id}")
	public ResponseEntity<String> deleteServiceRequest(@PathVariable Long id) {
	    if (!serviceRepository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Service request with ID " + id + " not found.");
	    }

	    serviceRepository.deleteById(id);
	    return ResponseEntity.ok("Service request with ID " + id + " has been successfully deleted.");
	}
	
	@GetMapping("/sortServices")
	public List<ServiceRequestProjection> sortServices(@RequestParam String option){
		return clientService.sortServices(option);
	}
	
	@GetMapping("/client-address/service/{serviceId}")
	public ClientAddressProjection getClientAddressFromServiceId(@PathVariable Long serviceId) {
		return clientService.getClientAddressFromServiceId(serviceId);
	}
	
	@PostMapping("/clientReview/{clientId}")
	public ResponseEntity<Review> clientReview(@RequestBody ReviewDTO reviewDTO, @PathVariable Long clientId){
		Review review = clientService.clientReview(reviewDTO, clientId);
		if(review != null) {
			return ResponseEntity.ok(review);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

	}
	
	@GetMapping("/getReviews")
	public ResponseEntity<?> getReview(){
		List<ReviewProjection> reviews = clientService.getReview();
		if(!reviews.isEmpty()) {
			return ResponseEntity.ok(reviews);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
	
	@DeleteMapping("/deleteReview/{reviewId}")
	public void deleteReview(Long reviewId) {
		clientService.deleteReview(reviewId);
	}
	
	@PutMapping("/updateReview")
	public ResponseEntity<Review> updateReview(ReviewDTO reviewDTO) {
		Review review = clientService.updateReview(reviewDTO);
		if(review != null) {
			return ResponseEntity.ok(review);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null); 
	}
	
	@PostMapping("/booking")
	public ResponseEntity<?> scheduleBooking(@RequestBody BookingDTO dto) {
		DiscountResult booking = clientService.scheduleBooking(dto);
		if(booking != null) {
			return ResponseEntity.ok(booking);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(false); 
	}
	
	@PostMapping("/reviewBooking")
	public ResponseEntity<?> reviewBooking(@RequestBody BookingDTO dto) {
		DiscountResult booking = clientService.reviewBooking(dto);
		if(booking != null) {
			return ResponseEntity.ok(booking);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(false); 
	}
	
}
