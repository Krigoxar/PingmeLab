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

@Service
public class StatsService {

  private ObservationRepository observationRepo;
  private UrlRepository observedUrlRepository;

  public StatsService(UrlRepository observedUrlRepo, ObservationRepository observationRepo) {
    this.observedUrlRepository = observedUrlRepo;
    this.observationRepo = observationRepo;
    startCorutine();
  }

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
              res.setObservedurl(observedUrl);
              observationRepo.save(res);
            }
          }
        };
    timer.schedule(hourlyTask, 10L, 1000 * 60 * 5L);
  }
}
