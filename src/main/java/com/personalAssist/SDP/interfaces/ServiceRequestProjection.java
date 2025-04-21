package com.personalAssist.SDP.interfaces;

import java.time.LocalDate;

public interface ServiceRequestProjection {
	Long getId();
	String getServiceName();
    String getPriority();
    String getServiceType();
    String getRepeatFrequency();
    LocalDate getRequestedDate();
    String getStatus();
}
