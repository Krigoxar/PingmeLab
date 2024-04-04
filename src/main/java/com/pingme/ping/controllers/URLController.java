package com.pingme.ping.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.pingme.ping.daos.model.ObservedURL;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.dtos.ObservationsCount;
import com.pingme.ping.services.ObservedURLService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class URLController {

	private ObservedURLService pingService;

	public URLController(ObservedURLService pingService) {
		this.pingService = pingService;
	}

	@GetMapping("/pings/observationscount")
	public ResponseEntity<ObservationsCount> getObservationsCount(@RequestParam(required = true) String url) {
		ObservationsCount observationsCount = pingService.getObservatioinsCount(url);

		if (observationsCount == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(observationsCount, HttpStatus.OK);
	}

	@GetMapping("/pings")
	public ResponseEntity<List<ObservedURL>> getAllUrls(@RequestParam(required = false) String url) {
		List<ObservedURL> observedURLs;

		if (url == null) {
			observedURLs = pingService.getAllObservableURLs();
		} else {
			observedURLs = pingService.getObservableURLbyURL(url);
		}

		if (observedURLs.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(observedURLs, HttpStatus.OK);
	}

	@PostMapping("/pings")
	public ObservedURL createNewObservableURL(@RequestBody(required = true) NewURL urlDto) {
		return pingService.addObservedURL(urlDto);
	}

	@DeleteMapping("/pings/{id}")
	public HttpStatus deleteObservedURL(@PathVariable Long id) {
		if (pingService.deleteObservedURL(id)) {
			return HttpStatus.NO_CONTENT;
		}
		return HttpStatus.NOT_FOUND;
	}

	@PutMapping("pings/{id}")
	public ResponseEntity<ObservedURL> updateUrl(@PathVariable Long id, @RequestBody ObservedURL entity) {
		var res = pingService.updateObservedURL(entity, id);
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}