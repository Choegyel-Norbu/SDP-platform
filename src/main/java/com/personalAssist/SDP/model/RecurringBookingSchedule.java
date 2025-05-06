package com.personalAssist.SDP.model;

import java.time.OffsetDateTime;

import com.personalAssist.SDP.enums.RepeatFrequency;

import jakarta.persistence.*;

@Entity
public class RecurringBookingSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Booking parentBooking; // Reference to the first booking

	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime nextScheduledDate;// When to create the next booking
	private int remainingOccurrences; // How many left to create
	private boolean isActive; // Is this series still active?

	@Enumerated(EnumType.STRING)
	private RepeatFrequency frequency;

	public RecurringBookingSchedule() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Booking getParentBooking() {
		return parentBooking;
	}

	public void setParentBooking(Booking parentBooking) {
		this.parentBooking = parentBooking;
	}

	public OffsetDateTime getNextScheduledDate() {
		return nextScheduledDate;
	}

	public void setNextScheduledDate(OffsetDateTime nextScheduledDate) {
		this.nextScheduledDate = nextScheduledDate;
	}

	public int getRemainingOccurrences() {
		return remainingOccurrences;
	}

	public void setRemainingOccurrences(int remainingOccurrences) {
		this.remainingOccurrences = remainingOccurrences;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public RepeatFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(RepeatFrequency frequency) {
		this.frequency = frequency;
	}

}
