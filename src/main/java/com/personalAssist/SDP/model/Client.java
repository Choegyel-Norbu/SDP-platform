package com.personalAssist.SDP.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	@Column(length = 20)
	private String phoneNumber;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<ServiceRequest> request;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
	private Review review;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval= true)
	private List<Booking> bookings;

	private boolean canReview;

	public Client() {
		super();
	}

	public Client(Long id, String firstName, String lastName, List<ServiceRequest> request) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.request = request;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCanReview() {
		return canReview;
	}

	public void setCanReview(boolean canReview) {
		this.canReview = canReview;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<ServiceRequest> getRequest() {
		return request;
	}

	public void setRequest(List<ServiceRequest> request) {
		this.request = request;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
