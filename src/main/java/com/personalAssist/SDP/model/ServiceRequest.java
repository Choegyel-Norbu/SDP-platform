package com.personalAssist.SDP.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personalAssist.SDP.enums.Priority;
import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.Status;
import com.personalAssist.SDP.enums.ServiceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class ServiceRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	private String serviceName;
	private String description;
	private int rate;


	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime requestedDate;
	
	private String serviceType;

	@Enumerated(EnumType.STRING)
	private RepeatFrequency repeatFrequency;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime createdAt;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime updatedAt;

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private ServiceStatus status;

	public ServiceRequest() {
		super();
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Long getId() {
		return id;
	}

	public RepeatFrequency getRepeatFrequency() {
		return repeatFrequency;
	}

	public void setRepeatFrequency(RepeatFrequency repeatFrequency) {
		this.repeatFrequency = repeatFrequency;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public OffsetDateTime getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(OffsetDateTime requestedDate) {
		this.requestedDate = requestedDate;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
