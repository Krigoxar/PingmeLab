package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.ObservationRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The Tests. */
public class ObservationServiceTest {

  @InjectMocks ObservationService service;

  @Mock ObservationRepository observationRepository;

  @Mock UrlRepository urlRepository;

  private ObservedUrl observedUrl = new ObservedUrl("url");

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllTest() {
    List<Observation> mUrls =
        Arrays.asList(new Observation(observedUrl, true), new Observation(observedUrl, false));

    when(observationRepository.findAll()).thenReturn(mUrls);

    assertEquals(mUrls, service.getAllObservations());
  }

  @Test
  void findAllByNameTest() {
    List<Observation> mUrls =
        Arrays.asList(new Observation(observedUrl, true), new Observation(observedUrl, false));

    when(observationRepository.findByObservedUrl(observedUrl)).thenReturn(mUrls);

    assertEquals(mUrls, service.getObservationsByUrl(observedUrl));
  }

  @Test
  void addTest() {

    when(urlRepository.findByUrl(anyString())).thenReturn(Arrays.asList(observedUrl));

    Observation obs = new Observation();
    when(observationRepository.save(any(Observation.class))).thenReturn(obs);

    assertEquals(obs, service.addObservation(new NewUrl("google.com")));

    when(urlRepository.findByUrl(anyString())).thenReturn(new LinkedList<>());

    assertNull(service.addObservation(new NewUrl("google.com")));
  }

  @Test
  void isRespTest() {
    assertFalse(service.isResponding(new NewUrl("s")));
  }

  @Test
  void deleteTest() {
    Long mId = 1L;
    when(observationRepository.findById(mId)).thenReturn(Optional.of(new Observation()));
    assertTrue(service.deleteObservation(mId));

    when(observationRepository.findById(mId)).thenReturn(Optional.ofNullable(null));
    assertFalse(service.deleteObservation(mId));
  }

  @Test
  void updateTest() {
    Observation mFromObs = new Observation();
    Long mId = 1L;

    Observation mToObs = new Observation();

    when(observationRepository.findById(mId)).thenReturn(Optional.ofNullable(null));

    assertNull(service.updateObservation(mToObs, mId));

    when(observationRepository.findById(mId)).thenReturn(Optional.of(mFromObs));
    when(observationRepository.save(any(Observation.class))).thenReturn(mToObs);

    assertEquals(service.updateObservation(mToObs, mId), mToObs);
  }
}
