package com.pingme.ping.controllers;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                var uRLs = observedURLService.getAllObservableURLs();
                observationService.checkConnections(uRLs);
            }
        };
        timer.schedule (hourlyTask, 10l, 1000*60*5L);
    }

    @GetMapping("/Stats")
	public ResponseEntity<URLStats> getAllTutorials(@RequestParam(required = true) String url) {

        List<ObservedURL> observedURLs = observedURLService.getObservableURLbyURL(url);
    
        if (observedURLs.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    
        var observedUrl = observedURLs.get(0);
        URLStats stats = new URLStats(observedUrl, observationService.getObservationsByURL(observedUrl));
        return new ResponseEntity<>(stats, HttpStatus.OK);
  	}

}
