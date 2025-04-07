package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.ServiceStatus;

@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Long>{

	@Query(value ="SELECT * from service_status ss limit 1", nativeQuery = true)
	ServiceStatus fetchPending();
	
	@Query(value = "SELECT * FROM service_status ss WHERE ss.status = :status", nativeQuery = true)
	ServiceStatus fetchStatusByName(@Param("status") String status);

}
