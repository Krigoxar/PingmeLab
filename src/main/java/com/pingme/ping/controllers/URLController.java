package com.pingme.ping.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.pingme.ping.daos.model.ObservedURL;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.services.ObservedService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class URLController {
 
	private ObservedService pingService;

	public URLController(ObservedService pingService) {
		this.pingService = pingService;
	}

	@GetMapping("/Pings")
	public ResponseEntity<List<ObservedURL>> getAllTutorials(@RequestParam(required = false) String url) {
		List<ObservedURL> observedURLs;
	
		if (url == null){
			observedURLs = pingService.getAllObservableURLs();
		}
		else{
			observedURLs = pingService.getObservableURLbyURL(url);
		}
	
		if (observedURLs.isEmpty()) {
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	
		return new ResponseEntity<>(observedURLs, HttpStatus.OK);
  	}
	
	@PostMapping("/Pings")
	public ObservedURL createNewObservableURL(@RequestBody(required = true) NewURL urlDto) {
		return pingService.addObservedURL(urlDto);
	}

	@DeleteMapping("/Pings/{id}")
	public HttpStatus deleteObservedURL(@PathVariable Long id) {
		if(pingService.deleteObservedURL(id)) {
			return HttpStatus.ACCEPTED;
		}
		return HttpStatus.NO_CONTENT;
	}

	@PutMapping("Pings/{id}")
	public ResponseEntity<ObservedURL> putMethodName(@PathVariable Long id, @RequestBody ObservedURL entity) {
		var res = pingService.updateObservedURL(entity, id);
		if(res == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}