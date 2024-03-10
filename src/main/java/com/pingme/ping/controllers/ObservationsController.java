package com.pingme.ping.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedURL;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ObservationsController {

    private ObservationService observationService;
    private ObservedService observedURLService;
    
    public ObservationsController(ObservationService observationService, ObservedService observedURLService) {
        this.observationService = observationService;
        this.observedURLService = observedURLService;
    }

    @GetMapping("/observation")
    public ResponseEntity<List<Observation>> getAllObservations(@RequestParam(required = false) String url) {

        if(url == null)
        {
            return new ResponseEntity<>(observationService.getAllObservations(), HttpStatus.OK);
        }

        ObservedURL uRLObj = observedURLService.getObservableURLbyURL(url).get(0);
        if(uRLObj == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var observations = observationService.getObservationsByURL(uRLObj);
        if(observations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(observations, HttpStatus.OK);
    }

    @PostMapping("/observation")
    public ResponseEntity<Observation> createObservation(@RequestBody NewURL url) {
        return new ResponseEntity<>(observationService.addObservation(url), HttpStatus.OK);
    }

    @PutMapping("/observation/{id}")
    public ResponseEntity<Observation> updateObservation(@PathVariable Long id, @RequestBody Observation entity) {
		var res = observationService.updateObservation(entity, id);
		if(res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    @DeleteMapping("/observation/{id}")
    public HttpStatus deleteObservation(@PathVariable Long id) {
		if(observationService.deleteObservation(id)) {
			return HttpStatus.NO_CONTENT;
		}
		return HttpStatus.NOT_FOUND;
    }
}
