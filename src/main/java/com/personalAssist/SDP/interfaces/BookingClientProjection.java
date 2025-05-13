package com.personalAssist.SDP.interfaces;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface BookingClientProjection {
	
	Long getId();
	LocalDateTime getBookingTime();
    Double getDiscountAmount();
    LocalDateTime getEndTime();
    String getFrequency();
    Double getAmountAfterDiscount();
    LocalDateTime getStartTime();
    String getStatus();
    String getServiceType();
    String getSpecialInstructions();
    String getServiceName();
}
