package com.pingme.ping.services;

import com.pingme.ping.daos.ObservationRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import java.net.InetAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Service;

/**
 * The `StatsService` class in Java periodically checks the reachability of observed URLs and saves
 * the observation results.
 */
@Service
public class StatsService {

  private ObservationRepository observationRepo;
  private UrlRepository observedUrlRepository;

  /**
   * This code snippet is from a Java class `StatsService` that is responsible for periodically
   * checking the reachability of observed URLs and saving the observation results. Let's break down
   * the important parts of the code:
   */
  public StatsService(UrlRepository observedUrlRepo, ObservationRepository observationRepo) {
    this.observedUrlRepository = observedUrlRepo;
    this.observationRepo = observationRepo;
    startCorutine();
  }

  /**
   * The `startCorutine` function uses a Timer to periodically check the reachability of observed
   * URLs and save the observations in a repository.
   */
  public void startCorutine() {
    Timer timer = new Timer();
    TimerTask hourlyTask =
        new TimerTask() {
          @Override
          public void run() {
            var urls = observedUrlRepository.findAll();
            for (ObservedUrl observedUrl : urls) {
              boolean isResponding = false;
              try {
                isResponding = InetAddress.getByName(observedUrl.getUrl()).isReachable(1000);
              } catch (Exception e) {
                isResponding = false;
              }
              var res = new Observation();
              res.setResponding(isResponding);
              res.setObservationDate(new Date());
              res.setObservedUrl(observedUrl);
              observationRepo.save(res);
            }
          }
        };
    timer.schedule(hourlyTask, 10L, 1000 * 60 * 5L);
  }
}
