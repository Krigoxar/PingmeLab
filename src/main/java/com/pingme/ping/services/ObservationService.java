package com.pingme.ping.services;

import com.pingme.ping.daos.ObservationRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewURL;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ObservationService {

  private ObservationRepository observationRepository;
  private UrlRepository observedUrlRepository;

  public ObservationService(
      ObservationRepository observationRepository, UrlRepository observedUrlRepository) {
    this.observationRepository = observationRepository;
    this.observedUrlRepository = observedUrlRepository;
  }

  public List<Observation> getAllObservations() {
    return observationRepository.findAll();
  }

  public List<Observation> getObservationsByUrl(ObservedUrl url) {
    return observationRepository.findByObservedUrl(url);
  }

  public Observation addObservation(NewURL url) {
    boolean isResponding = false;
    try {
      isResponding = InetAddress.getByName(url.url()).isReachable(1000);
    } catch (Exception e) {
      isResponding = false;
    }
    var res = new Observation();
    res.setResponding(isResponding);
    res.setObservationDate(new Date());
    var obsurl = observedUrlRepository.findByUrl(url.url());
    if (obsurl.isEmpty()) {
      return null;
    }
    res.setObservedurl(obsurl.get(0));
    return observationRepository.save(res);
  }

  public boolean deleteObservation(Long id) {
    var obs = observationRepository.findById(id);
    if (obs.isEmpty()) {
      return false;
    }

    observationRepository.delete(obs.get());
    return true;
  }

  public Observation updateObservation(Observation entity, Long id) {
    var obs = observationRepository.findById(id);
    if (obs.isEmpty()) {
      return null;
    }

    var temp = obs.get();
    temp.setResponding(entity.isResponding());
    temp.setObservationDate(entity.getObservationDate());
    temp.setObservedurl(entity.getObservedurl());
    return observationRepository.save(entity);
  }
}
