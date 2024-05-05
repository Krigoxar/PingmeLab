package com.pingme.ping.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.services.ObservationService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The Tests. */
class StatsControllerTest {

  @InjectMocks StatsController controller;

  @Mock ObservationService observationService;

  @Mock ObservedUrlService observedUrlObservedUrlService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllStatsTest() {
    when(observedUrlObservedUrlService.getObservableUrlbyUrl(any())).thenReturn(Arrays.asList());
    assertNull(controller.getAllStats("Test").getBody());
    when(observedUrlObservedUrlService.getObservableUrlbyUrl(any()))
        .thenReturn(Arrays.asList(new ObservedUrl()));
    assertNotNull(controller.getAllStats("Test").getBody());
  }

  @Test
  void getAllStatsTestid() {
    when(observedUrlObservedUrlService.getObservableUrlById(any())).thenReturn(null);
    assertNull(controller.getAllStatsById(1L).getBody());
    when(observedUrlObservedUrlService.getObservableUrlById(any()))
        .thenReturn(new ObservedUrl());
    assertNotNull(controller.getAllStatsById(1L).getBody());
  }
}
