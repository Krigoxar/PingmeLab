package com.pingme.ping.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pingme.ping.daos.model.*;

@Repository
public interface URLRepository extends JpaRepository<ObservedURL, Long> {
  List<ObservedURL> findByUrl(String url);

  @Query("SELECT COUNT(o) FROM Observation o WHERE o.observedurl = :url")
  Long countObservations(ObservedURL url);
}