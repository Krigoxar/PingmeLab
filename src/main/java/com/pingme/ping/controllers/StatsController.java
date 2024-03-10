package com.pingme.ping.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.pingme.ping.daos.model.ObservedURL;
import com.pingme.ping.dtos.URLStats;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StatsController {
    
    private ObservationService observationService;
    private ObservedService observedURLService;
    
    public StatsController(ObservationService observationService, ObservedService observedURLService) {
        this.observationService = observationService;
        this.observedURLService = observedURLService;
    }

    @GetMapping("/stats")
	public ResponseEntity<URLStats> getAllStats(@RequestParam(required = true) String url) {

        List<ObservedURL> observedURLs = observedURLService.getObservableURLbyURL(url);
    
        if (observedURLs.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        var observedUrl = observedURLs.get(0);
        URLStats stats = new URLStats(observedUrl, observationService.getObservationsByURL(observedUrl));
        return new ResponseEntity<>(stats, HttpStatus.OK);
  	}

}
