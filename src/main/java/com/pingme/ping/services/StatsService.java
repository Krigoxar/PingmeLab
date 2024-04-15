package com.pingme.ping.services;

import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Service;

/**
 * The `StatsService` class in Java periodically checks the reachability of observed URLs and saves
 * the observation results.
 */
@Service
public class StatsService {

  private UrlRepository observedUrlRepository;
  private ObservationService observationService;
  private Timer timer;

  /**
   * This code snippet is from a Java class `StatsService` that is responsible for periodically
   * checking the reachability of observed URLs and saving the observation results. Let's break down
   * the important parts of the code:
   */
  public StatsService(
      UrlRepository observedUrlRepo, ObservationService observationService, Timer timer) {
    this.observedUrlRepository = observedUrlRepo;
    this.observationService = observationService;
    this.timer = timer;
    startCorutine();
  }

  /**
   * The `startCorutine` function uses a Timer to periodically check the reachability of observed
   * URLs and save the observations in a repository.
   */
  public void startCorutine() {
    TimerTask hourlyTask =
        new TimerTask() {
          @Override
          public void run() {
            var urls = observedUrlRepository.findAll();
            for (ObservedUrl observedUrl : urls) {
              observationService.addObservation(new NewUrl(observedUrl.getUrl()));
            }
          }
        };
    timer.schedule(hourlyTask, 10L, 1000 * 60 * 5L);
  }
}
