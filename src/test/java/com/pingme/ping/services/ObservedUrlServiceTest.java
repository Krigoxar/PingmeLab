package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

/** The Tests. */
class ObservedUrlServiceTest {
  @InjectMocks ObservedUrlService service;

  @Mock UrlRepository observedUrlRepo;

  @Mock CategoryRepository categoryRepo;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllObservableUrlsTest() {
    List<ObservedUrl> mobservedUrls =
        Arrays.asList(new ObservedUrl("Test1"), new ObservedUrl("Test22"));
    when(observedUrlRepo.findAll()).thenReturn(mobservedUrls);
    assertEquals(service.getAllObservableUrls(), mobservedUrls);
  }

  @Test
  void getObservableUrlbyUrlTest() {
    assertTrue(service.getObservableUrlbyUrl(null).isEmpty());

    var observedUrl = Arrays.asList(new ObservedUrl());
    when(observedUrlRepo.findByUrl(anyString())).thenReturn(observedUrl);
    assertEquals(service.getObservableUrlbyUrl("Test"), observedUrl);
  }

  @Test
  void addObservedUrlTest() {
    assertNull(service.addObservedUrl(null));

    when(observedUrlRepo.save(any())).thenReturn(new ObservedUrl("Test"));
    assertNotNull(service.addObservedUrl(new NewUrl("Test")));
  }

  @Test
  void deleteObservedUrlTest() {
    assertFalse(service.deleteObservedUrl(null));

    when(observedUrlRepo.findById(anyLong())).thenReturn(Optional.ofNullable(null));
    assertFalse(service.deleteObservedUrl(null));

    ObservedUrl mobservedUrl = new ObservedUrl("Test");
    Long mid = 1L;
    when(observedUrlRepo.findById(anyLong())).thenReturn(Optional.ofNullable(mobservedUrl));
    assertTrue(service.deleteObservedUrl(mid));
    verify(observedUrlRepo).delete(mobservedUrl);

    Category category1 = new Category("Test1");
    Category category2 = new Category("Test2");
    ObservedUrl mobservedUrl2 = new ObservedUrl("Test2");
    when(observedUrlRepo.findById(anyLong())).thenReturn(Optional.ofNullable(mobservedUrl2));
    mobservedUrl2.getBags().add(category1);
    mobservedUrl2.getBags().add(category2);

    assertTrue(service.deleteObservedUrl(mid));

    verify(observedUrlRepo).delete(mobservedUrl2);
  }

  @Test
  void updateObservedUrlTest() {
    final Long id = 1L;
    ObservedUrl murl = new ObservedUrl("Tests");
    when(observedUrlRepo.save(any())).thenReturn(murl);
    when(observedUrlRepo.findById(anyLong())).thenReturn(Optional.ofNullable(null));
    assertNull(service.updateObservedUrl(null, null));
    assertNull(service.updateObservedUrl(murl, null));
    assertNull(service.updateObservedUrl(null, id));

    assertNull(service.updateObservedUrl(murl, id));

    when(observedUrlRepo.findById(anyLong())).thenReturn(Optional.ofNullable(murl));
    assertNotNull(service.updateObservedUrl(murl, id));
  }

  @Test
  void getObservatioinsCountTest() {
    when(observedUrlRepo.findByUrl(any())).thenReturn(new ArrayList<>());
    assertNull(service.getObservatioinsCount(null));
    assertNull(service.getObservatioinsCount("Test"));
    when(observedUrlRepo.findByUrl(any())).thenReturn(Arrays.asList(new ObservedUrl("Test")));
    assertNotNull(service.getObservatioinsCount("Test"));
  }

  @Test
  void addObservedUrlsTest() {
    ObservedUrl observedUrl = new ObservedUrl();
    when(observedUrlRepo.save(any())).thenReturn(observedUrl);
    assertTrue(service.addObservedUrls(null).isEmpty());
    assertTrue(service.addObservedUrls(new ArrayList<>()).isEmpty());

    NewUrl murl1 = new NewUrl("Tests1");
    NewUrl murl2 = new NewUrl("Tests2");
    NewUrl murl3 = new NewUrl("Tests2");

    List<NewUrl> murls1 = Arrays.asList(murl1, murl2, murl3);
    assertFalse(service.addObservedUrls(murls1).isEmpty());
    verify(observedUrlRepo, VerificationModeFactory.times(2)).save(any(ObservedUrl.class));

    List<NewUrl> murls2 = Arrays.asList(murl1, murl2, murl2);
    assertFalse(service.addObservedUrls(murls2).isEmpty());
    verify(observedUrlRepo, VerificationModeFactory.times(4)).save(any(ObservedUrl.class));

    List<NewUrl> murls3 = Arrays.asList(murl1);
    assertFalse(service.addObservedUrls(murls3).isEmpty());
    verify(observedUrlRepo, VerificationModeFactory.times(5)).save(any(ObservedUrl.class));

    List<NewUrl> murls4 = Arrays.asList(murl1, murl2);
    assertFalse(service.addObservedUrls(murls4).isEmpty());
    verify(observedUrlRepo, VerificationModeFactory.times(7)).save(any(ObservedUrl.class));
  }
}
