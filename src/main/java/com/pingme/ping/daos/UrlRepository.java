package com.pingme.ping.daos;

import com.pingme.ping.daos.model.ObservedUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** This code snippet is defining a Spring Data JPA repository interface named `UrlRepository`. */
@Repository
public interface UrlRepository extends JpaRepository<ObservedUrl, Long> {
  List<ObservedUrl> findByUrl(String url);

  @Query("SELECT COUNT(o) FROM Observation o WHERE o.observedUrl = :url")
  Long countObservations(ObservedUrl url);
}
