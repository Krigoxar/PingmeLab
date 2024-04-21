package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pingme.ping.components.Cache;
import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.CategoryName;
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
class CategoryServiceTest {

  @InjectMocks CategoryService service;

  @Mock CategoryRepository categoryRepository;

  @Mock UrlRepository observedUrlRepo;

  @Mock Cache<String, Category> cache;

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
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.putToCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(null));
    assertNull(service.putToCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    when(categoryRepository.save(any(Category.class))).thenReturn(mcategory);

    assertEquals(service.putToCategory(obsId, catId), mcategory);

    verify(categoryRepository).save(any());
  }

  @Test
  void removeFromCategoryTest() {
    final Category mcategory = new Category("TestCat1");
    Long catId = 1L;
    ObservedUrl mobservedUrl = new ObservedUrl("TestUrl");
    Long obsId = 1L;

    assertNull(service.removeFromCategory(null, catId));
    assertNull(service.removeFromCategory(obsId, null));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(null));
    assertNull(service.removeFromCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(null));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.removeFromCategory(obsId, catId));

    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    assertNull(service.removeFromCategory(obsId, catId));

    mcategory.getUrls().add(mobservedUrl);
    Category mcategoryNoUrl = new Category("TestCat");
    when(observedUrlRepo.findById(obsId)).thenReturn(Optional.ofNullable(mobservedUrl));
    when(categoryRepository.findById(catId)).thenReturn(Optional.ofNullable(mcategory));
    when(categoryRepository.save(mcategory)).thenReturn(mcategoryNoUrl);
    assertEquals(service.removeFromCategory(obsId, catId), mcategoryNoUrl);
  }

  @Test
  void findAllTest() {
    List<Category> mcategorys = Arrays.asList(new Category("1"), new Category("2"));

    when(categoryRepository.findAll()).thenReturn(mcategorys);

    assertEquals(mcategorys, service.getAllCategorys());
  }

  @Test
  void findByNameTestInCache() {
    Category mcategory = new Category("Test");

    when(cache.containsKey(mcategory.getName())).thenReturn(true);
    when(cache.get(mcategory.getName())).thenReturn(mcategory);

    assertEquals(mcategory, service.getCategoryByName(mcategory.getName()));
  }

  @Test
  void findByNameTestNoInCache() {
    Category mcategory = new Category("Test");

    when(cache.containsKey(mcategory.getName())).thenReturn(false);
    when(categoryRepository.findByName(mcategory.getName())).thenReturn(Arrays.asList(mcategory));

    assertEquals(mcategory, service.getCategoryByName(mcategory.getName()));
    verify(cache, VerificationModeFactory.times(0)).get(any());

    when(categoryRepository.findByName(mcategory.getName())).thenReturn(new ArrayList<>());

    assertNull(service.getCategoryByName(mcategory.getName()));
  }

  @Test
  void addTest() {
    Category mcategory = new Category("Test");
    service.addCategory(new CategoryName(mcategory.getName()));
    verify(categoryRepository).save(any(Category.class));
  }

  @Test
  void deleteTestInCash() {
    Category mcategory = new Category("Test");
    Long mid = 1L;
    when(categoryRepository.findById(mid)).thenReturn(Optional.of(mcategory));

    assertTrue(service.deleteCategory(mid));

    verify(cache).remove(mcategory.getName());
    verify(categoryRepository).delete(mcategory);

    when(categoryRepository.findById(mid)).thenReturn(Optional.ofNullable(null));

    assertFalse(service.deleteCategory(mid));
  }

  @Test
  void updateTest() {
    Category mfromCategory = new Category("Test1");
    Long mfromId = 1L;

    Category mtoCategory = new Category("Test2");

    when(categoryRepository.findById(mfromId)).thenReturn(Optional.ofNullable(null));

    assertNull(service.updateCategory(mtoCategory, mfromId));

    when(categoryRepository.findById(mfromId)).thenReturn(Optional.ofNullable(mfromCategory));
    when(categoryRepository.save(mtoCategory)).thenReturn(mtoCategory);

    assertEquals(service.updateCategory(mtoCategory, mfromId), mtoCategory);

    verify(cache).remove(mfromCategory.getName());
  }
}
