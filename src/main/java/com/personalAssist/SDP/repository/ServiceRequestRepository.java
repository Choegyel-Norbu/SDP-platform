package com.personalAssist.SDP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.interfaces.ClientAddressProjection;
import com.personalAssist.SDP.interfaces.ServiceRequestProjection;
import com.personalAssist.SDP.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

	@Query(value = """
			    SELECT
			      sr.id AS id,
			      sr.service_name AS serviceName,
			      sr.priority AS priority,
			      sr.service_type AS serviceType,
			      sr.repeat_frequency AS repeatFrequency,
			      sr.requested_date AS requestedDate,
			      ss.status AS status
			    FROM
			      service_request sr
			    INNER JOIN
			      service_status ss
			    ON
			      sr.status = ss.id
			""", nativeQuery = true)
	List<ServiceRequestProjection> findAllServiceRequestsWithStatus();

	@Query(value = "select c.id, c.first_name, c.last_name, c.phone_number, a.post_code, a.state, a.street_address, a.street_type, a.subarb, a.unit, a.unit_number \n"
			+ "from client c join address a \n" + "on c.address_id = a.id \n" + "join users u \n"
			+ "on u.id = c.user_id \n" + "where u.id = :id", nativeQuery = true)
	ClientAddressProjection fetchClientWithId(@Param("id") Long id);

	@Query(value = "SELECT sr.id, sr.priority, sr.repeat_frequency, sr.requested_date, "
			+ "sr.service_name, sr.service_type, ss.status " + "FROM service_request sr "
			+ "INNER JOIN client c ON sr.client_id = c.id " + "JOIN users u ON c.user_id = u.id "
			+ "JOIN service_status ss ON ss.status = sr.status " + "WHERE u.id = :id", nativeQuery = true)
	List<ServiceRequestProjection> findAllServicesForClientId(@Param("id") Long id);

}
