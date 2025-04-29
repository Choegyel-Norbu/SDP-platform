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
	
	@Query(value = "select sr.id, sr.priority, sr.repeat_frequency, sr.requested_date, sr.service_name, sr.service_type, ss.status from service_request sr join service_status ss on sr.status = ss.id \n"
			+ "join client c on c.id = sr.client_id \n"
			+ "join users u on u.id = c.user_id \n"
			+ "where u.id = :id", nativeQuery = true)
	List<ServiceRequestProjection> findAllServicesForClientId(@Param("id") Long id);
	
	@Query(value = "SELECT sr.id, sr.service_name, sr.priority, sr.service_type, sr.repeat_frequency, sr.requested_date, ss.status \n"
			+ "FROM service_request sr join service_status ss on ss.id = sr.status \n"
			+ "ORDER BY \n"
			+ "  CASE priority\n"
			+ "    WHEN 'HIGH' THEN 1\n"
			+ "    WHEN 'NORMAL' THEN 2\n"
			+ "    WHEN 'LOW' THEN 3\n"
			+ "    ELSE 4\n"
			+ "  END ASC", nativeQuery = true)
	List<ServiceRequestProjection> priorityHighToLow();
	
	@Query(value = "SELECT sr.id, sr.service_name, sr.priority, sr.service_type, sr.repeat_frequency, sr.requested_date, ss.status \n"
			+ "FROM service_request sr join service_status ss on ss.id = sr.status \n"
			+ "ORDER BY \n"
			+ "  CASE ss.status\n"
			+ "    WHEN 'PENDING' THEN 1\n"
			+ "    WHEN 'ASSIGNED' THEN 2\n"
			+ "    WHEN 'COMPLETED' THEN 3\n"
			+ "    ELSE 4\n"
			+ "  END ASC", nativeQuery = true)
	List<ServiceRequestProjection> status();
	
	@Query(value = "SELECT sr.id, sr.service_name, sr.priority, sr.service_type, sr.repeat_frequency, sr.requested_date, ss.status \n"
			+ "FROM service_request sr join service_status ss on ss.id = sr.status \n"
			+ "ORDER BY requested_date DESC", nativeQuery = true)
	List<ServiceRequestProjection> closestDate();
	
	@Query(value = "SELECT sr.id, sr.service_name, sr.priority, sr.service_type, sr.repeat_frequency, sr.requested_date, ss.status \n"
			+ "FROM service_request sr join service_status ss on ss.id = sr.status \n"
			+ "ORDER BY requested_date ASC", nativeQuery = true)
	List<ServiceRequestProjection> oldestDate();
	
	
}
