package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.ObservationRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
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
public class ObservedUrlServiceTest {
    @InjectMocks
    ObservedUrlService service;

    @Mock 
    UrlRepository observedUrlRepo;
    
    @Mock 
    CategoryRepository categoryRepo;

    @BeforeEach
    void setUp() {
      MockitoAnnotations.openMocks(this);
    }

    @Test
    void putToCategoryTest() {
        Category mCategory = new Category("TestCat");
        Long catId = 1L;
        ObservedUrl mObservedUrl = new ObservedUrl("TestUrl");
        Long obsId = 1L;

        assertNull(service.putToCategory(null, catId));
        assertNull(service.putToCategory(obsId, null));
        
        when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(null));
        when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mCategory));
        assertNull(service.putToCategory(obsId, catId));

        when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mObservedUrl));
        when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(null));
        assertNull(service.putToCategory(obsId, catId));

        when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mObservedUrl));
        when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mCategory));
        when(categoryRepo.save(any(Category.class))).thenReturn(mCategory);

        assertEquals(service.putToCategory(obsId, catId), mCategory);

        verify(categoryRepo).save(any());
    }

    @Test
    void removeFromCategoryTest() {
      Category mCategory = new Category("TestCat");
      Long catId = 1L;
      ObservedUrl mObservedUrl = new ObservedUrl("TestUrl");
      Long obsId = 1L;


      assertNull(service.removeFromCategory(null, catId));
      assertNull(service.removeFromCategory(obsId, null));

      when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mObservedUrl));
      when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(null));
      assertNull(service.removeFromCategory(obsId, catId));

      when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(null));
      when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mCategory));
      assertNull(service.removeFromCategory(obsId, catId));

      when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mObservedUrl));
      when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mCategory));
      assertNull(service.removeFromCategory(obsId, catId));

      mCategory.getUrls().add(mObservedUrl);
      Category mCategoryNoUrl = new Category("TestCat");
      when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mObservedUrl));
      when(categoryRepo.findById(catId)).thenReturn(Optional.ofNullable(mCategory));
      when(categoryRepo.save(mCategory)).thenReturn(mCategoryNoUrl);
      assertEquals(service.removeFromCategory(obsId, catId), mCategoryNoUrl);
    }

    @Test
    void getAllObservableUrlsTest() {
      List<ObservedUrl> mObservedUrls = Arrays.asList(new ObservedUrl("Test1"), new ObservedUrl("Test2"));
      when(observedUrlRepo.findAll()).thenReturn(mObservedUrls);
      assertEquals(service.getAllObservableUrls(), mObservedUrls);
    }
}
