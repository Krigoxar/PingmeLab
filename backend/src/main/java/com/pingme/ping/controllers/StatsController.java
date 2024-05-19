package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.UrlStats;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The StatsController class in Java defines a REST API endpoint for retrieving statistics related
 * to a specified URL.
 */
@CrossOrigin(origins = {"http://localhost:8081", "https://alluring-healing-production.up.railway.app", "https://pingmelab-production.up.railway.app"})
@RestController
@RequestMapping("/api")
public class StatsController {

  private ObservationService observationService;
  private ObservedUrlService observedUrlObservedUrlService;

  public StatsController(
      ObservationService observationService, ObservedUrlService observedUrlObservedUrlService) {
    this.observationService = observationService;
    this.observedUrlObservedUrlService = observedUrlObservedUrlService;
  }

  /**
   * This Java function retrieves statistics for a given URL by querying observed URLs and
   * observations related to it.
   *
   * @param url The code snippet you provided is a Spring MVC controller method that handles a GET
   *     request mapping to "/stats". The method takes a required request parameter "url" and
   *     returns statistics (UrlStats) for the specified URL.
   * @return The `getAllStats` method returns a `ResponseEntity` containing the `UrlStats` object
   *     for the specified URL. If the URL is not found in the list of observed URLs, a `NOT_FOUND`
   *     status is returned. If the URL is found, the method constructs the `UrlStats` object using
   *     the observed URL and its associated observations, and returns it with an `OK` status.
   */
  @GetMapping("/stats/url")
  public ResponseEntity<UrlStats> getAllStats(@RequestParam(required = true) String url) {

    List<ObservedUrl> observedUrlObservedUrls =
        observedUrlObservedUrlService.getObservableUrlbyUrl(url);

    if (observedUrlObservedUrls.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ObservedUrl observedUrlObservedUrl = observedUrlObservedUrls.get(0);
    UrlStats stats =
        new UrlStats(
            observedUrlObservedUrl,
            observationService.getObservationsByUrl(observedUrlObservedUrl));
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }

  /** The function. */
  @GetMapping("/stats/id")
  public ResponseEntity<UrlStats> getAllStatsById(@RequestParam(required = true) Long id) {

    ObservedUrl observedUrlObservedUrls = observedUrlObservedUrlService.getObservableUrlById(id);

    if (observedUrlObservedUrls == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    UrlStats stats =
        new UrlStats(
          observedUrlObservedUrls,
            observationService.getObservationsByUrl(observedUrlObservedUrls));
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }
}
