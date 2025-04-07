package com.personalAssist.SDP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personalAssist.SDP.dto.ClientDTO;
import com.personalAssist.SDP.dto.ServiceRequestDTO;
import com.personalAssist.SDP.model.ServiceRequest;
import com.personalAssist.SDP.service.ClientService;

@RestController
@RequestMapping("/api")
public class ClientServiceController {

	@Autowired
	ClientService clientService;

	@PostMapping("/addClient")
	public ResponseEntity<?> addClient(@RequestBody ClientDTO clientDTO) {
		Boolean client = clientService.associateClient(clientDTO);

		if (client) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}

	@PostMapping("/serviceRequest")
	public ResponseEntity<?> initiateServiceRequest(@RequestBody ServiceRequestDTO dto) {
		boolean response = clientService.initiateServiceRequest(dto);

		if (response) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed");
	}

	@PatchMapping("/updateService")
	public ResponseEntity<?> updateServiceRequest(@RequestBody ServiceRequestDTO dto){
		ServiceRequest service = clientService.updateServiceRequest(dto);
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
	
	@GetMapping("/getAllServices")
	public List<ServiceRequestDTO> getAllServices(){
		return clientService.getAllServices();
	}
}
