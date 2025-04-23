package com.personalAssist.SDP.interfaces;

import java.time.LocalDateTime;

public interface ServiceRequestProjection {
	Long getId();
	String getServiceName();
    String getPriority();
    String getServiceType();
    String getRepeatFrequency();
    LocalDateTime getRequestedDate();
    String getStatus();
}
