package com.personalAssist.SDP.enums;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.personalAssist.SDP.model.ServiceRequest;

import jakarta.persistence.*;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int rating; // from 1 to 5, for example

	@Column(length = 1000)
	private String comment;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToOne
	@JoinColumn(name = "service_request_id")
	private ServiceRequest serviceRequest;

	public Review() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

}
