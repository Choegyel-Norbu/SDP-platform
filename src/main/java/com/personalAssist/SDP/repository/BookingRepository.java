package com.personalAssist.SDP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.interfaces.BookingClientProjection;
import com.personalAssist.SDP.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query(value = "select b.id,sr.service_type, sr.service_name, b.booking_time, b.discount_amount, b.end_time, b.frequency, b.amount_after_discount ,\n"
			+ "	b.start_time, b.status \n"
			+ "	from booking b \n"
			+ "	join service_request sr on b.service_request_id = sr.id\n"
			+ "	join client c on c.id = b.client_id\n"
			+ "	join users u on u.id = c.user_id\n"
			+ "	WHERE u.id = :userId", nativeQuery = true)
	List<BookingClientProjection> findBookingClientDetails(@Param("userId") Long userId);

	@Query(value = "select b.id,sr.service_type, sr.service_name, b.booking_time, b.discount_amount, b.end_time, b.frequency, b.amount_after_discount ,\n"
			+ "	b.start_time, b.status, b.special_instructions \n"
			+ "	from booking b \n"
			+ "	join service_request sr on b.service_request_id = sr.id\n"
			+ "	join client c on c.id = b.client_id\n"
			+ "	join users u on u.id = c.user_id\n"
			, nativeQuery = true)
	List<BookingClientProjection> getAllBookings();
}
