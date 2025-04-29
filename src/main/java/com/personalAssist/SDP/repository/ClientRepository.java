package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.model.Client;

import jakarta.persistence.Entity;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	@Query(value = "SELECT * FROM client WHERE user_id = :userId", nativeQuery = true)
	Client loadClientByUserId(@Param("userId") Long userId);
	
	@Query(value = "select * from client c where user_id = :userId", nativeQuery = true)
	Client clientSet(@Param("userId") Long userId);
	
	@Query(value = "SELECT c.first_name, c.last_name, a.unit, a.street_address, a.subarb, a.state from client c join address a on c.address_id = a.id \n"
			+ "join service_request sr on c.id = sr.client_id\n"
			+ "where sr.id = :serviceId", nativeQuery = true)
	ClientAddressProjection getClientAddressFromServiceId(@Param("serviceId") Long serviceId);
}
