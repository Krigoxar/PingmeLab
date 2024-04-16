package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.List;
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

/**
 * The ObservationsController class in Java defines REST endpoints for managing observations with
 * CRUD operations.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ObservationsController {

  private ObservationService observationService;
  private ObservedUrlService observedUrlService;

  public ObservationsController(
      ObservationService observationService, ObservedUrlService observedUrlService) {
    this.observationService = observationService;
    this.observedUrlService = observedUrlService;
  }

  /**
   * This Java function retrieves observations based on a specified URL or returns all observations
   * if no URL is provided.
   *
   * @param url The `url` parameter in the `getAllObservations` method is used to filter
   *     observations based on a specific URL. If the `url` parameter is provided in the request,
   *     the method retrieves observations associated with that URL. If the `url` parameter is not
   *     provided, the method retrieves all observations
   * @return The method `getAllObservations` returns a `ResponseEntity` containing a list of
   *     `Observation` objects. The response status code is either `HttpStatus.OK` if observations
   *     are found, or `HttpStatus.NO_CONTENT` if no observations are found or if the provided URL
   *     is not valid.
   */
  @GetMapping("/observation")
  public ResponseEntity<List<Observation>> getAllObservations(
      @RequestParam(required = false) String url) {
    if (url == null) {
      var obs = observationService.getAllObservations();
      return new ResponseEntity<>(obs, HttpStatus.OK);
    }

    var urlObjs = observedUrlService.getObservableUrlbyUrl(url);
    if (urlObjs.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    var urlObj = urlObjs.get(0);
    var observations = observationService.getObservationsByUrl(urlObj);
    if (observations.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(observations, HttpStatus.OK);
  }

  @PostMapping("/observation/url")
  public ResponseEntity<Observation> createObservation(@RequestBody NewUrl url) {
    return new ResponseEntity<>(observationService.addObservationByUrl(url), HttpStatus.OK);
  }

  @PostMapping("/observation/id")
  public ResponseEntity<Observation> createObservationById(
      @RequestParam(required = true) Long id) {
    return new ResponseEntity<>(observationService.addObservationById(id), HttpStatus.OK);
  }

  /**
   * This Java function updates an observation entity with a specific ID and returns a
   * ResponseEntity with the updated entity or a NOT_FOUND status if the entity is not found.
   *
   * @param id The `id` parameter in the `@PutMapping` annotation represents the unique identifier
   *     of the observation that is being updated. It is extracted from the URL path
   *     `/observation/{id}` where `{id}` is a path variable.
   * @param entity The `entity` parameter in the `updateObservation` method represents the updated
   *     observation object that is sent in the request body when making a PUT request to update an
   *     observation with a specific `id`. This object contains the new data that should replace the
   *     existing observation data in the system.
   * @return The method `updateObservation` is returning a `ResponseEntity` object. If the update is
   *     successful, it returns a `ResponseEntity` containing the updated `Observation` object with
   *     HTTP status code 200 (OK). If the update is not successful (e.g., if the observation with
   *     the specified ID is not found), it returns a `ResponseEntity` with HTTP status code 404
   */
  @PutMapping("/observation/{id}")
  public ResponseEntity<Observation> updateObservation(
      @PathVariable Long id, @RequestBody Observation entity) {
    var res = observationService.updateObservation(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  /**
   * This Java function deletes an observation by ID and returns the appropriate HTTP status code
   * based on the success of the operation.
   *
   * @param id The `id` parameter in the `@DeleteMapping` annotation represents the unique
   *     identifier of the observation that needs to be deleted. This identifier is passed as a path
   *     variable in the URL when making a DELETE request to the specified endpoint
   *     `/observation/{id}`.
   * @return The `deleteObservation` method returns an HTTP status code. If the observation with the
   *     specified `id` is successfully deleted, it returns `HttpStatus.NO_CONTENT`. If the
   *     observation is not found (i.e., unable to delete), it returns `HttpStatus.NOT_FOUND`.
   */
  @DeleteMapping("/observation/{id}")
  public HttpStatus deleteObservation(@PathVariable Long id) {
    if (observationService.deleteObservation(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }
}
