package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ObservationsController {

  private static final Logger logger = LoggerFactory.getLogger(ObservationsController.class);

  private ObservationService observationService;
  private ObservedUrlService observedUrlService;

  public ObservationsController(
      ObservationService observationService, ObservedUrlService observedUrlService) {
    this.observationService = observationService;
    this.observedUrlService = observedUrlService;
  }

  @GetMapping("/observation")
  public ResponseEntity<List<Observation>> getAllObservations(
      @RequestParam(required = false) String url) {
    logger.info("test");
    if (url == null) {
      var obs = observationService.getAllObservations();
      return new ResponseEntity<>(obs, HttpStatus.OK);
    }

    ObservedUrl urlObj = observedUrlService.getObservableUrlbyUrl(url).get(0);
    if (urlObj == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    var observations = observationService.getObservationsByUrl(urlObj);
    if (observations.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(observations, HttpStatus.OK);
  }

  @PostMapping("/observation")
  public ResponseEntity<Observation> createObservation(@RequestBody NewURL url) {
    return new ResponseEntity<>(observationService.addObservation(url), HttpStatus.OK);
  }

  @PutMapping("/observation/{id}")
  public ResponseEntity<Observation> updateObservation(
      @PathVariable Long id, @RequestBody Observation entity) {
    var res = observationService.updateObservation(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @DeleteMapping("/observation/{id}")
  public HttpStatus deleteObservation(@PathVariable Long id) {
    if (observationService.deleteObservation(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }
}
