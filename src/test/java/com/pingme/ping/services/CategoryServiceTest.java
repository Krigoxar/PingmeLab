package com.pingme.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.pingme.ping.components.Cache;
import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.model.Category;
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
import org.mockito.verification.VerificationMode;

/** The Tests. */
class CategoryServiceTest {

  @InjectMocks CategoryService service;

  @Mock CategoryRepository categoryRepository;

  @Mock Cache<String, Category> cache;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllTest() {
    List<Category> mCategorys = Arrays.asList(new Category("1"), new Category("2"));

    when(categoryRepository.findAll()).thenReturn(mCategorys);

    assertEquals(mCategorys, service.getAllCategorys());
  }

  @Test
  void findByNameTestInCache() {
    Category mCategory = new Category("Test");

    when(cache.containsKey(mCategory.getName())).thenReturn(true);
    when(cache.get(mCategory.getName())).thenReturn(mCategory);

    assertEquals(mCategory, service.getCategoryByName(mCategory.getName()));
  }

  @Test
  void findByNameTestNoInCache() {
    Category mCategory = new Category("Test");

    when(cache.containsKey(mCategory.getName())).thenReturn(false);
    when(categoryRepository.findByName(mCategory.getName())).thenReturn(Arrays.asList(mCategory));

    assertEquals(mCategory, service.getCategoryByName(mCategory.getName()));
    verify(cache, VerificationModeFactory.times(0)).get(any());

    when(categoryRepository.findByName(mCategory.getName())).thenReturn(new ArrayList<>());

    assertNull(service.getCategoryByName(mCategory.getName()));
  }

  @Test
  void addTest() {
    Category mCategory = new Category("Test");
    service.addCategory(new CategoryName(mCategory.getName()));
    verify(categoryRepository).save(any(Category.class));
  }

  @Test
  void deleteTestInCash() {
    Category mCategory = new Category("Test");
    Long mId = 1L;
    when(categoryRepository.findById(mId)).thenReturn(Optional.of(mCategory));

    assertTrue(service.deleteCategory(mId));

    verify(cache).remove(mCategory.getName());
    verify(categoryRepository).delete(mCategory);

    when(categoryRepository.findById(mId)).thenReturn(Optional.ofNullable(null));

    assertFalse(service.deleteCategory(mId));
  }

  @Test
  void updateTest() {
    Category mFromCategory = new Category("Test1");
    Long mFromId = 1L;

    Category mToCategory = new Category("Test2");

    when(categoryRepository.findById(mFromId)).thenReturn(Optional.ofNullable(null));

    assertNull(service.updateCategory(mToCategory, mFromId));

    when(categoryRepository.findById(mFromId)).thenReturn(Optional.ofNullable(mFromCategory));
    when(categoryRepository.save(mToCategory)).thenReturn(mToCategory);

    assertEquals(service.updateCategory(mToCategory, mFromId), mToCategory);

    verify(cache).remove(mFromCategory.getName());
  }
}
