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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
  void putToCategoryTest() {
    final Category mcategory = new Category("TestCat");
    Long catId = 1L;
    final ObservedUrl mobservedUrl = new ObservedUrl("TestUrl");
    Long obsId = 1L;

    assertNull(service.putToCategory(null, catId));
    assertNull(service.putToCategory(obsId, null));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(null));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.putToCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(null));
    assertNull(service.putToCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    when(categoryRepo.save(any(Category.class))).thenReturn(mcategory);

    assertEquals(service.putToCategory(obsId, catId), mcategory);

    verify(categoryRepo).save(any());
  }

  @Test
  void removeFromCategoryTest() {
    final Category mcategory = new Category("TestCat");
    Long catId = 1L;
    ObservedUrl mobservedUrl = new ObservedUrl("TestUrl");
    Long obsId = 1L;

    assertNull(service.removeFromCategory(null, catId));
    assertNull(service.removeFromCategory(obsId, null));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(null));
    assertNull(service.removeFromCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(null));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.removeFromCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.removeFromCategory(obsId, catId));

    mcategory.getUrls().add(mobservedUrl);
    Category mcategoryNoUrl = new Category("TestCat");
    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    when(categoryRepo.save(mcategory)).thenReturn(mcategoryNoUrl);
    assertEquals(service.removeFromCategory(obsId, catId), mcategoryNoUrl);
  }

  @Test
  void getAllObservableUrlsTest() {
    List<ObservedUrl> mobservedUrls =
        Arrays.asList(new ObservedUrl("Test1"), new ObservedUrl("Test2"));
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
}
