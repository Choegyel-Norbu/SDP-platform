package com.personalAssist.SDP.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.personalAssist.SDP.enums.Status;
import com.personalAssist.SDP.enums.TypeEnum;
import com.personalAssist.SDP.model.ServiceStatus;
import com.personalAssist.SDP.repository.ServiceStatusRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	ServiceStatusRepository serviceStatusRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("DataInitializer running...");
		// your logic here
		if (serviceStatusRepository.count() == 0) {
			serviceStatusRepository.save(new ServiceStatus(Status.PENDING));
			serviceStatusRepository.save(new ServiceStatus(Status.ASSIGNED));
			serviceStatusRepository.save(new ServiceStatus(Status.COMPLETED));
		}
	}
}
