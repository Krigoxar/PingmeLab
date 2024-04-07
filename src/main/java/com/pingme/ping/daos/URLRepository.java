package com.pingme.ping.daos;

import com.pingme.ping.daos.model.ObservedUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<ObservedUrl, Long> {
  List<ObservedUrl> findByUrl(String url);

  @Query("SELECT COUNT(o) FROM Observation o WHERE o.observedurl = :url")
  Long countObservations(ObservedUrl url);
}
