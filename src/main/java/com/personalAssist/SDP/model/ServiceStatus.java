package com.personalAssist.SDP.model;

import java.util.List;

import com.personalAssist.SDP.enums.Status;

import jakarta.persistence.*;

@Entity
@Table(name = "service_status")
public class ServiceStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServiceRequest> serviceRequests;

	public ServiceStatus(Status status) {
		super();
		this.status = status;
	}

	public ServiceStatus() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<ServiceRequest> getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

}
