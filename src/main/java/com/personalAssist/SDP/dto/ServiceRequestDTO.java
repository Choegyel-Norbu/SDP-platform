package com.personalAssist.SDP.dto;

import java.time.LocalDateTime;

import com.personalAssist.SDP.enums.Priority;
import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.ServiceType;

public class ServiceRequestDTO {

	private Long userId;
	private Long serviceRequestId;
	private String serviceName;
	private String description;
	private int rate;
	private LocalDateTime requestedDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String priority;
	private String repeatFrequency;
	private String serviceType;
	
//	for customize response
	private Priority responsePriority;
	private RepeatFrequency responseFrequency;

	public ServiceRequestDTO(Long serviceRequestId, String serviceName, String description, int rate,
			LocalDateTime requestedDate, LocalDateTime createdAt, Priority responsePriority, RepeatFrequency responseFrequency, String serviceType) {
		super();
		this.serviceRequestId = serviceRequestId;
		this.serviceName = serviceName;
		this.description = description;
		this.rate = rate;
		this.requestedDate = requestedDate;
		this.createdAt = createdAt;
		this.responsePriority = responsePriority;
		this.responseFrequency = responseFrequency;
		this.serviceType = serviceType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public Long getServiceRequestId() {
		return serviceRequestId;
	}

	public void setServiceRequestId(Long serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
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

	public LocalDateTime getRequestedDate() {
		return requestedDate;
	}

	public String getRepeatFrequency() {
		return repeatFrequency;
	}

	public void setRepeatFrequency(String repeatFrequency) {
		this.repeatFrequency = repeatFrequency;
	}

	public void setRequestedDate(LocalDateTime requestedDate) {
		this.requestedDate = requestedDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
