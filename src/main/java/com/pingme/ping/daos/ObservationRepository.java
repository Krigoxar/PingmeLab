package com.pingme.ping.daos;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This code snippet is defining a Spring Data JPA repository interface called
 * `ObservationRepository`.
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
  List<Observation> findByObservedUrl(ObservedUrl observedUrl);
}
