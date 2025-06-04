package com.personalAssist.SDP.model;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.personalAssist.SDP.enums.RepeatFrequency;
import com.personalAssist.SDP.enums.Status;

import jakarta.persistence.*;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Client client;

	@OneToOne
	@JoinColumn(name = "service_request_id", unique = true)
	private ServiceRequest serviceRequest;

	@Column(unique = true)
	private String bookingId;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime bookingTime;

	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime startTime;

	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime endTime;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime createdAt;

	private LocalTime timeStart;

	private LocalTime timeEnd;

	private int duration;

	@Enumerated(EnumType.STRING)
	private Status status; // PENDING, CONFIRMED, COMPLETED, CANCELLED

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AddOn> addOns;

	@OneToOne
	@JoinColumn(name = "status_id", unique = true)
	private ServiceStatus bookingStatus;

	private double AmountAfterDiscount;
	private double discountAmount;
	private double taxAmount;
	private double totalAmount;

	@Enumerated(EnumType.STRING)
	private RepeatFrequency frequency;
	private int recurrenceCount;
	private boolean isRecurringParent; // Is this the first in a series?
	private Long recurringParentId; // Links to parent booking (if not parent)

	private String specialInstructions;

	@ManyToOne
	@JoinColumn(name = "promo_code_id")
	private PromoCode promoCode;

	private String cancellationReason;
	private int numberOfBedrooms;
	private int numberOfBathrooms;

	public Booking() {
		super();
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public ServiceStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(ServiceStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}

	public LocalTime getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
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

	public Long getId() {
		return id;
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

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public RepeatFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(RepeatFrequency frequency) {
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

	public void setServiceRequest(ServiceRequest serviceRequest) {
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

	public void setEndTime(OffsetDateTime endTime) {
		this.endTime = endTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<AddOn> getAddOns() {
		return addOns;
	}

	public void setAddOns(List<AddOn> addOns) {
		this.addOns = addOns;
	}

	public double getAmountAfterDiscount() {
		return AmountAfterDiscount;
	}

	public void setAmountAfterDiscount(double amountAfterDiscount) {
		AmountAfterDiscount = amountAfterDiscount;
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

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}

}
