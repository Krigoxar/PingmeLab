package com.pingme.ping.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import com.pingme.ping.daos.model.*;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
  List<Observation> findByObservedurl(ObservedURL observedURL);
}