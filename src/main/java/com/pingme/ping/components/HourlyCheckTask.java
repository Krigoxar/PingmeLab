package com.pingme.ping.components;

import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.services.ObservationService;
import java.util.TimerTask;
import org.springframework.stereotype.Component;

/** The HourlyTask class. */
@Component
public class HourlyCheckTask extends TimerTask {

  private UrlRepository observedUrlRepository;
  private ObservationService observationService;

  public HourlyCheckTask(
      UrlRepository observedUrlRepository, ObservationService observationService) {
    this.observedUrlRepository = observedUrlRepository;
    this.observationService = observationService;
  }

  @Override
  public void run() {
    var urls = observedUrlRepository.findAll();
    for (ObservedUrl observedUrl : urls) {
      observationService.addObservation(new NewUrl(observedUrl.getUrl()));
    }
  }
}
