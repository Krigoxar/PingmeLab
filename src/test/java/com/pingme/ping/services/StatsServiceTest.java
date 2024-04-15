package com.pingme.ping.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

import com.pingme.ping.daos.UrlRepository;
import java.util.Timer;
import java.util.TimerTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

/** The Tests. */
class StatsServiceTest {

  @InjectMocks StatsService service;

  @Mock ObservationService observationService;

  @Mock UrlRepository observedUrlRepository;

  @Mock Timer timer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void startCorutineTest() {
    service.startCorutine();
    verify(timer, VerificationModeFactory.times(2))
        .schedule(any(TimerTask.class), anyLong(), anyLong());
  }
}
