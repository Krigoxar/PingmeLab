package com.pingme.ping.services;

import com.pingme.ping.daos.ObservationRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The `ObservationService` class in Java provides methods for managing observations related to
 * URLs, including adding, retrieving, updating, and deleting observations.
 */
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

  /**
   * The function `addObservation` checks if a given URL is reachable, creates an observation object
   * with the result, and saves it to a repository.
   *
   * @param url The `url` parameter in the `addObservation` method is of type `NewUrl`.
   * @return The method `addObservation` is returning an `Observation` object after performing some
   *     operations like checking if a URL is reachable, setting the response status, setting the
   *     observation date, and saving the observation in the repository. If the observed URL is not
   *     found in the repository, it returns `null`.
   */
  public Observation addObservationByUrl(NewUrl url) {
    var obsurl = observedUrlRepository.findByUrl(url.url());
    if (obsurl.isEmpty()) {
      return null;
    }

    boolean isResponding = isResponding(url.url());
    var res = new Observation();
    res.setResponding(isResponding);
    res.setObservationDate(new Date());
    res.setObservedUrl(obsurl.get(0));
    return observationRepository.save(res);
  }

  /** The function. */
  public Observation addObservationById(Long id) {
    var obsurl = observedUrlRepository.findById(id);
    if (obsurl.isEmpty()) {
      return null;
    }

    boolean isResponding = isResponding(obsurl.get().getUrl());
    var res = new Observation();
    res.setResponding(isResponding);
    res.setObservationDate(new Date());
    res.setObservedUrl(obsurl.get());
    return observationRepository.save(res);
  }

  /** The function. */
  public boolean isResponding(String url) {
    try {
      return InetAddress.getByName(url).isReachable(1000);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * The function `deleteObservation` deletes an observation by its ID from the repository and
   * returns true if successful, false if the observation does not exist.
   *
   * @param id The `id` parameter in the `deleteObservation` method is of type `Long` and represents
   *     the unique identifier of the observation that needs to be deleted from the repository.
   * @return The method `deleteObservation` returns a boolean value - `true` if the observation with
   *     the given `id` was successfully deleted, and `false` if the observation was not found in
   *     the repository.
   */
  public boolean deleteObservation(Long id) {
    var obs = observationRepository.findById(id);
    if (obs.isEmpty()) {
      return false;
    }

    observationRepository.delete(obs.get());
    return true;
  }

  /**
   * The function updates an existing observation entity with new data based on the provided ID.
   *
   * @param entity The `entity` parameter in the `updateObservation` method represents the updated
   *     observation object that contains the new values to be set for an existing observation
   *     entity in the database. It includes attributes such as `responding`, `observationDate`, and
   *     `observedurl` which are used to update the
   * @param id The `id` parameter is a `Long` value that represents the unique identifier of the
   *     observation entity that needs to be updated.
   * @return The method is returning the updated Observation entity after saving it to the
   *     observationRepository.
   */
  public Observation updateObservation(Observation entity, Long id) {
    var obs = observationRepository.findById(id);
    if (obs.isEmpty()) {
      return null;
    }

    var temp = obs.get();
    temp.setResponding(entity.isResponding());
    temp.setObservationDate(entity.getObservationDate());
    temp.setObservedUrl(entity.getObservedUrl());
    return observationRepository.save(entity);
  }
}
