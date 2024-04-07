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

@CrossOrigin(origins = "http://localhost:8080")
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

  @GetMapping("/stats")
  public ResponseEntity<UrlStats> getAllStats(@RequestParam(required = true) String url) {

    List<ObservedUrl> observedUrlObservedUrls =
        observedUrlObservedUrlService.getObservableUrlbyUrl(url);

    if (observedUrlObservedUrls.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    var observedUrlObservedUrl = observedUrlObservedUrls.get(0);
    UrlStats stats =
        new UrlStats(
            observedUrlObservedUrl,
            observationService.getObservationsByUrl(observedUrlObservedUrl));
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }
}
