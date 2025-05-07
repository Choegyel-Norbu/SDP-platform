package com.personalAssist.SDP.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.personalAssist.SDP.enums.Priority;
import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.ServiceType;

public class ServiceRequestDTO {

	private Long userId;
	private Long serviceRequestId;
	private String serviceName;
	private String description;
	private int rate;
	private OffsetDateTime requestedDate;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private String priority;
	private String repeatFrequency;
	private String serviceType;
	private double itemPrice;

//	for customize response
	private Priority responsePriority;
	private RepeatFrequency responseFrequency;

	public ServiceRequestDTO() {
		super();
	}

	public ServiceRequestDTO(Long serviceRequestId, String serviceName, String description, int rate,
			OffsetDateTime requestedDate, OffsetDateTime createdAt, Priority responsePriority,
			RepeatFrequency responseFrequency, String serviceType) {
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

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Priority getResponsePriority() {
		return responsePriority;
	}

	public void setResponsePriority(Priority responsePriority) {
		this.responsePriority = responsePriority;
	}

	public RepeatFrequency getResponseFrequency() {
		return responseFrequency;
	}

	public void setResponseFrequency(RepeatFrequency responseFrequency) {
		this.responseFrequency = responseFrequency;
	}

	public ServiceRequestDTO(Long serviceRequestId, String serviceName, String description,
			OffsetDateTime requestedDate, Priority responsePriority, RepeatFrequency responseFrequency,
			String serviceType) {
		super();
		this.serviceRequestId = serviceRequestId;
		this.serviceName = serviceName;
		this.description = description;
		this.requestedDate = requestedDate;
		this.serviceType = serviceType;
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

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getRepeatFrequency() {
		return repeatFrequency;
	}

	public void setRepeatFrequency(String repeatFrequency) {
		this.repeatFrequency = repeatFrequency;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
