package com.personalAssist.SDP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.interfaces.ReviewProjection;
import com.personalAssist.SDP.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(value = "select r.comment, r.rating, r.id, c.first_name, c.last_name from review r join client c on r.client_id = c.id limit 5;\n"
			+ "", nativeQuery = true)
	List<ReviewProjection> getReview();

}
