package com.personalAssist.SDP.util;

public class DiscountResult {

	private double totalAmount;
	private double discountedAmount;
	private double amountAfterDiscount;
	private String description;

	public DiscountResult(double discountedAmount, String description) {
		this.discountedAmount = discountedAmount;
		this.description = description;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDiscountedPrice() {
		return discountedAmount;
	}

	public String getDescription() {
		return description;
	}

	public double getAmountAfterDiscount() {
		return amountAfterDiscount;
	}

	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}

	
}
