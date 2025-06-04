package com.personalAssist.SDP.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.personalAssist.SDP.enums.DiscountType;

import jakarta.persistence.*;

@Entity
public class PromoCode {

	@Id
	private String code;

	private String description;
	private DiscountType type; // PERCENTAGE, FIXED_AMOUNT
	private double value;

	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime startDate;

	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime endDate;

	private int maxUses;
	private int usedCount;
	private boolean valid;

	@OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Booking> bookings;

	public PromoCode() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DiscountType getType() {
		return type;
	}

	public void setType(DiscountType type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public void setMaxUses(int maxUses) {
		this.maxUses = maxUses;
	}

	public int getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
