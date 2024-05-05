package com.pingme.ping.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.dtos.ObservationsCount;
import com.pingme.ping.services.ObservedUrlService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The Tests. */
class UrlControllerTest {

  @InjectMocks UrlController controller;

  @Mock ObservedUrlService pingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getObservatioinsCountTest() {
    assertNull(controller.getObservationsCount(null).getBody());

    String mstr = "Test";
    when(pingService.getObservatioinsCount(mstr)).thenReturn(null);
    assertNull(controller.getObservationsCount(mstr).getBody());

    when(pingService.getObservatioinsCount(mstr)).thenReturn(new ObservationsCount(2L, mstr));
    assertNotNull(controller.getObservationsCount(mstr).getBody());
  }

  @Test
  void getAllUrlsTest() {

    when(pingService.getAllObservableUrls()).thenReturn(Arrays.asList());
    assertNull(controller.getAllUrls(null).getBody());

    when(pingService.getAllObservableUrls()).thenReturn(Arrays.asList(new ObservedUrl("Test")));
    assertFalse(controller.getAllUrls(null).getBody().isEmpty());

    String mstr = "Test";
    when(pingService.getObservableUrlbyUrl(mstr)).thenReturn(Arrays.asList());
    assertNull(controller.getAllUrls(mstr).getBody());

    when(pingService.getObservableUrlbyUrl(mstr))
        .thenReturn(Arrays.asList(new ObservedUrl("Test")));
    assertFalse(controller.getAllUrls(mstr).getBody().isEmpty());
  }

  @Test
  void createNewObservableUrlTest() {
    NewUrl mock = new NewUrl("Test");
    when(controller.createNewObservableUrl(mock)).thenReturn(new ObservedUrl(mock.url()));
    assertNotNull(controller.createNewObservableUrl(mock));
  }

  @Test
  void createNewObservableUrlBulkTest() {
    assertNotNull(controller.createNewObservableUrlBulk(Arrays.asList()));
  }

  @Test
  void deleteObservedUrlTest() {
    when(pingService.deleteObservedUrl(anyLong())).thenReturn(true);
    assertNotNull(controller.deleteObservedUrl(1L));

    when(pingService.deleteObservedUrl(anyLong())).thenReturn(false);
    assertNotNull(controller.deleteObservedUrl(null));
  }

  @Test
  void updateObservedUrlTest() {

    ObservedUrl mcategory = new ObservedUrl();
    Long id = 1L;
    when(pingService.updateObservedUrl(any(), any())).thenReturn(null);
    assertNull(controller.updateUrl(id, mcategory).getBody());
    when(pingService.updateObservedUrl(any(), any())).thenReturn(mcategory);
    assertNotNull(controller.updateUrl(id, mcategory).getBody());
  }
}
