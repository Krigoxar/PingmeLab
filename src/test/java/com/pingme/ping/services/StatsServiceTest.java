package com.pingme.ping.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pingme.ping.components.HourlyCheckTask;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.ObservedUrl;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

/** The Tests. */
class StatsServiceTest {

  StatsService service;

  @InjectMocks HourlyCheckTask checkTask;

  @Mock ObservationService observationService;

  @Mock UrlRepository observedUrlRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    checkTask = new HourlyCheckTask(observedUrlRepository, observationService);
    service = new StatsService(checkTask);
  }

  @Test
  void startCorutineTest() {
    when(observedUrlRepository.findAll())
        .thenReturn(Arrays.asList(new ObservedUrl("1"), new ObservedUrl("2")));

    checkTask.run();
    verify(observedUrlRepository, VerificationModeFactory.atLeast(1)).findAll();
    verify(observationService, VerificationModeFactory.atLeast(1)).addObservation(any());
  }
}
