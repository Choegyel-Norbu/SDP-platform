package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.PromoCode;

@Repository
public interface PromoRepository extends JpaRepository<PromoCode, String>{
	
	@Query(value = "select * from promo_code pc where pc.code = :promoCode", nativeQuery = true)
	boolean existsPromo(@Param("promoCode") String promoCode);

}
