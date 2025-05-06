package com.personalAssist.SDP.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class AddOn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private double price;
	private int durationMinutes;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	public AddOn() {
		super();
	}

	public AddOn(String name, String description, double price, int durationMinutes) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.durationMinutes = durationMinutes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

}
