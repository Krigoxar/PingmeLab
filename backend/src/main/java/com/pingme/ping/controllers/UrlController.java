package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.dtos.ObservationsCount;
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
 * The UrlController class in Java defines REST endpoints for managing observed URLs with CRUD
 * operations.
 */
@CrossOrigin(origins = {"http://localhost:8081", "https://alluring-healing-production.up.railway.app"})
@RestController
@RequestMapping("/api")
public class UrlController {

  private ObservedUrlService pingService;

  public UrlController(ObservedUrlService pingService) {
    this.pingService = pingService;
  }

  /**
   * This Java function retrieves the count of observations for a given URL and returns it in a
   * ResponseEntity.
   *
   * @param url The `url` parameter in the `getObservationsCount` method is a required query
   *     parameter that specifies the URL for which you want to retrieve the observations count.
   *     This method is a GET mapping that returns the observations count for the specified URL in
   *     the response body.
   * @return The method is returning a ResponseEntity object containing either the ObservationsCount
   *     object with HTTP status OK (200) if observationsCount is not null, or a ResponseEntity with
   *     HTTP status NO_CONTENT (204) if observationsCount is null.
   */
  @GetMapping("/pings/observationscount")
  public ResponseEntity<ObservationsCount> getObservationsCount(
      @RequestParam(required = true) String url) {
    ObservationsCount observationsCount = pingService.getObservatioinsCount(url);

    if (observationsCount == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(observationsCount, HttpStatus.OK);
  }

  /**
   * This Java function retrieves a list of observed URLs based on a query parameter and returns
   * them in a ResponseEntity.
   *
   * @param url The `@RequestParam` annotation in Spring MVC is used to bind a web request parameter
   *     to a method parameter in a controller. In this case, the `url` parameter is optional and
   *     can be provided as part of the request to the `/pings` endpoint.
   * @return The method is returning a `ResponseEntity` object containing a list of `ObservedUrl`
   *     objects. If the `observedUrls` list is empty, it returns a response with HTTP status code
   *     `NO_CONTENT`. Otherwise, it returns a response with HTTP status code `OK` and the list of
   *     `ObservedUrl` objects.
   */
  @GetMapping("/pings")
  public ResponseEntity<List<ObservedUrl>> getAllUrls(@RequestParam(required = false) String url) {
    List<ObservedUrl> observedUrls;

    if (url == null) {
      observedUrls = pingService.getAllObservableUrls();
    } else {
      observedUrls = pingService.getObservableUrlbyUrl(url);
    }

    if (observedUrls.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(observedUrls, HttpStatus.OK);
  }

  @PostMapping("/pings")
  public ObservedUrl createNewObservableUrl(@RequestBody(required = true) NewUrl urlDto) {
    return pingService.addObservedUrl(urlDto);
  }

  @PostMapping("/pings/bulk")
  public List<ObservedUrl> createNewObservableUrlBulk(
      @RequestBody(required = true) List<NewUrl> urlDto) {
    return pingService.addObservedUrls(urlDto);
  }

  /**
   * This Java function deletes an observed URL by its ID and returns either a status of NO_CONTENT
   * if successful or NOT_FOUND if the URL was not found.
   *
   * @param id The `id` parameter in the `@DeleteMapping` annotation represents the unique
   *     identifier of the observed URL that needs to be deleted. This identifier is extracted from
   *     the URL path `/pings/{id}` using the `@PathVariable` annotation in the method signature.
   * @return The deleteObservedUrl method returns an HTTP status code. If the observed URL with the
   *     specified id is successfully deleted, it returns HttpStatus.NO_CONTENT (204). If the
   *     observed URL is not found or cannot be deleted, it returns HttpStatus.NOT_FOUND (404).
   */
  @DeleteMapping("/pings/{id}")
  public HttpStatus deleteObservedUrl(@PathVariable Long id) {
    if (pingService.deleteObservedUrl(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }

  /**
   * This Java function updates an ObservedUrl entity with a specific ID and returns a
   * ResponseEntity with the updated entity or a NOT_FOUND status if the entity was not found.
   *
   * @param id The `id` parameter in the `updateUrl` method represents the unique identifier of the
   *     ObservedUrl entity that you want to update. This identifier is typically used to locate the
   *     specific entity in the database that needs to be updated.
   * @param entity The `entity` parameter in the `updateUrl` method represents the data that will be
   *     used to update an observed URL in the system. It is annotated with `@RequestBody`,
   *     indicating that the data will be extracted from the request body. This data is expected to
   *     be in the format of the `
   * @return The method is returning a `ResponseEntity` object. If the
   *     `pingService.updateObservedUrl` method returns `null`, then a `ResponseEntity` with
   *     `HttpStatus.NOT_FOUND` is returned. Otherwise, a `ResponseEntity` with the updated
   *     `ObservedUrl` object and `HttpStatus.OK` is returned.
   */
  @PutMapping("pings/{id}")
  public ResponseEntity<ObservedUrl> updateUrl(
      @PathVariable Long id, @RequestBody ObservedUrl entity) {
    ObservedUrl res = pingService.updateObservedUrl(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
