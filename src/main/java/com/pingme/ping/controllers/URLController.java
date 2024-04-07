package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewURL;
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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UrlController {

  private ObservedUrlService pingService;

  public UrlController(ObservedUrlService pingService) {
    this.pingService = pingService;
  }

  @GetMapping("/pings/observationscount")
  public ResponseEntity<ObservationsCount> getObservationsCount(
      @RequestParam(required = true) String url) {
    ObservationsCount observationsCount = pingService.getObservatioinsCount(url);

    if (observationsCount == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(observationsCount, HttpStatus.OK);
  }

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
  public ObservedUrl createNewObservableUrl(@RequestBody(required = true) NewURL urlDto) {
    return pingService.addObservedUrl(urlDto);
  }

  @DeleteMapping("/pings/{id}")
  public HttpStatus deleteObservedUrl(@PathVariable Long id) {
    if (pingService.deleteObservedUrl(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }

  @PutMapping("pings/{id}")
  public ResponseEntity<ObservedUrl> updateUrl(
      @PathVariable Long id, @RequestBody ObservedUrl entity) {
    var res = pingService.updateObservedUrl(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
