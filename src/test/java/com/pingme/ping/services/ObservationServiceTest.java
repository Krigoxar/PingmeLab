package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
class ObservationServiceTest {

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
    List<Observation> murls =
        Arrays.asList(new Observation(observedUrl, true), new Observation(observedUrl, false));

    when(observationRepository.findAll()).thenReturn(murls);

    assertEquals(murls, service.getAllObservations());
  }

  @Test
  void findAllByNameTest() {
    List<Observation> murls =
        Arrays.asList(new Observation(observedUrl, true), new Observation(observedUrl, false));

    when(observationRepository.findByObservedUrl(observedUrl)).thenReturn(murls);

    assertEquals(murls, service.getObservationsByUrl(observedUrl));
  }

  @Test
  void addTest() {

    when(urlRepository.findByUrl(anyString())).thenReturn(Arrays.asList(observedUrl));

    Observation obs = new Observation();
    when(observationRepository.save(any(Observation.class))).thenReturn(obs);

    assertEquals(obs, service.addObservationByUrl(new NewUrl("google.com")));

    when(urlRepository.findByUrl(anyString())).thenReturn(new LinkedList<>());

    assertNull(service.addObservationByUrl(new NewUrl("google.com")));
  }

  @Test
  void addIdTest() {

    when(urlRepository.findById(anyLong())).thenReturn(Optional.of(observedUrl));

    Observation obs = new Observation();
    when(observationRepository.save(any(Observation.class))).thenReturn(obs);

    assertEquals(obs, service.addObservationById(1L));

    when(urlRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

    assertNull(service.addObservationById(1L));
  }

  @Test
  void isRespTest() {
    assertFalse(service.isResponding("s"));
  }

  @Test
  void deleteTest() {
    Long id = 1L;
    when(observationRepository.findById(id)).thenReturn(Optional.of(new Observation()));
    assertTrue(service.deleteObservation(id));

    when(observationRepository.findById(id)).thenReturn(Optional.ofNullable(null));
    assertFalse(service.deleteObservation(id));
  }

  @Test
  void updateTest() {
    Observation mfromObs = new Observation();
    Long mid = 1L;

    Observation mtoObs = new Observation();

    when(observationRepository.findById(mid)).thenReturn(Optional.ofNullable(null));

    assertNull(service.updateObservation(mtoObs, mid));

    when(observationRepository.findById(mid)).thenReturn(Optional.of(mfromObs));
    when(observationRepository.save(any(Observation.class))).thenReturn(mtoObs);

    assertEquals(service.updateObservation(mtoObs, mid), mtoObs);
    
  }
}
