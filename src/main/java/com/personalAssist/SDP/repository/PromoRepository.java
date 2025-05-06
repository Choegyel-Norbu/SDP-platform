package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.PromoCode;

@Repository
public interface PromoRepository extends JpaRepository<PromoCode, String>{

}
