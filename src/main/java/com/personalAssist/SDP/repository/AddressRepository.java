package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
