package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.Client;

import jakarta.persistence.Entity;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	@Query(value = "SELECT * FROM client WHERE user_id = :userId", nativeQuery = true)
	Client loadClientByUserId(@Param("userId") Long userId);
}
