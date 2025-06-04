package com.personalAssist.SDP.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class BookingDTO {

	private Long id;
	private Long clientId;
	private Long userId;
	private ServiceRequestDTO serviceRequest;
	private OffsetDateTime bookingTime;
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;
	private String status;
	private List<AddOnDTO> addOns;
	private double subtotal;
	private double discountAmount;
	private double taxAmount;
	private double totalAmount;
	private String frequency;
	private int recurrenceCount;
	private boolean isRecurringParent;
	private Long recurringParentId;
	private String specialInstructions;
	private String promoCode;
	private String cancellationReason;
	private int numberOfBedrooms;
	private int numberOfBathrooms;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public ServiceRequestDTO getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequestDTO serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public OffsetDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(OffsetDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public OffsetDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(OffsetDateTime startTime) {
		this.startTime = startTime;
	}

	public OffsetDateTime getEndTime() {
		return endTime;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}

	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}

	public void setEndTime(OffsetDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AddOnDTO> getAddOns() {
		return addOns;
	}

	public void setAddOns(List<AddOnDTO> addOns) {
		this.addOns = addOns;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getRecurrenceCount() {
		return recurrenceCount;
	}

	public void setRecurrenceCount(int recurrenceCount) {
		this.recurrenceCount = recurrenceCount;
	}

	public boolean isRecurringParent() {
		return isRecurringParent;
	}

	public void setRecurringParent(boolean isRecurringParent) {
		this.isRecurringParent = isRecurringParent;
	}

	public Long getRecurringParentId() {
		return recurringParentId;
	}

	public void setRecurringParentId(Long recurringParentId) {
		this.recurringParentId = recurringParentId;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

}
