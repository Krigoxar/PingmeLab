package com.pingme.ping.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.model.Observation;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The Tests. */
class ObservationControllerTest {

  @InjectMocks ObservationsController controller;

  @Mock ObservationService observationService;
  @Mock ObservedUrlService observedUrlService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllObservationsTest() {

    when(observationService.getAllObservations()).thenReturn(Arrays.asList(new Observation()));
    assertFalse(controller.getAllObservations(null).getBody().isEmpty());

    when(observationService.getObservationsByUrl(any())).thenReturn(null);
    assertNull(controller.getAllObservations("Test").getBody());

    when(observationService.getObservationsByUrl(any()))
        .thenReturn(Arrays.asList(new Observation()));
    when(observedUrlService.getObservableUrlbyUrl(any()))
        .thenReturn(Arrays.asList(new ObservedUrl()));
    assertFalse(controller.getAllObservations("Test").getBody().isEmpty());

    when(observationService.getObservationsByUrl(any()))
        .thenReturn(Arrays.asList(new Observation()));
    when(observedUrlService.getObservableUrlbyUrl(any())).thenReturn(Arrays.asList());
    assertNull(controller.getAllObservations("Test").getBody());
  }

  @Test
  void createObservationTest() {
    when(observationService.addObservation(any(NewUrl.class))).thenReturn(new Observation());
    assertNotNull(controller.createObservation(new NewUrl("sss")).getBody());
    when(observationService.addObservation(any(NewUrl.class))).thenReturn(null);
    assertNull(controller.createObservation(new NewUrl(null)).getBody());
  }

  @Test
  void updateObservationTest() {
    Observation mcategory = new Observation();
    Long id = 1L;
    when(observationService.updateObservation(any(), any())).thenReturn(null);
    assertNull(controller.updateObservation(id, mcategory).getBody());
    when(observationService.updateObservation(any(), any())).thenReturn(mcategory);
    assertNotNull(controller.updateObservation(id, mcategory).getBody());
  }

  @Test
  void deleteCategoryTest() {
    when(observationService.deleteObservation(anyLong())).thenReturn(true);
    assertNotNull(controller.deleteObservation(1L));
    when(observationService.deleteObservation(anyLong())).thenReturn(false);
    assertNotNull(controller.deleteObservation(null));
  }
}
