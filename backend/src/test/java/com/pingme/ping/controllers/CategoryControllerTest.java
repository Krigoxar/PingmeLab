package com.pingme.ping.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.pingme.ping.daos.model.Category;
import com.pingme.ping.dtos.CategoryName;
import com.pingme.ping.services.CategoryService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The Tests. */
class CategoryControllerTest {

  @InjectMocks CategoryController controller;

  @Mock CategoryService categoryService;

  @Mock ObservedUrlService observedService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllCategorysTest() {
    when(categoryService.getAllCategorys()).thenReturn(Arrays.asList(new Category("Test")));
    assertFalse(controller.getAllCategorys(null).getBody().isEmpty());

    when(categoryService.getCategoryByName(anyString())).thenReturn(null);
    assertNull(controller.getAllCategorys("Test").getBody());

    when(categoryService.getCategoryByName(anyString())).thenReturn(new Category("Test"));
    assertFalse(controller.getAllCategorys("Test").getBody().isEmpty());
  }

  @Test
  void createNewCategoryTest() {
    when(categoryService.addCategory(any(CategoryName.class))).thenReturn(new Category("cum"));
    assertNotNull(controller.createNewCategory(new CategoryName("sss")));
    when(categoryService.addCategory(any(CategoryName.class))).thenReturn(null);
    assertNull(controller.createNewCategory(new CategoryName(null)).getBody());
  }

  @Test
  void deleteCategoryTest() {
    when(categoryService.deleteCategory(anyLong())).thenReturn(true);
    assertNotNull(controller.deleteCategory(1L));
    when(categoryService.deleteCategory(anyLong())).thenReturn(false);
    assertNotNull(controller.deleteCategory(null));
  }

  @Test
  void updateCategoryTest() {
    Category mcategory = new Category();
    Long id = 1L;
    when(categoryService.updateCategory(any(), any())).thenReturn(null);
    assertNull(controller.updateCategory(id, mcategory).getBody());
    when(categoryService.updateCategory(any(), any())).thenReturn(mcategory);
    assertNotNull(controller.updateCategory(id, mcategory).getBody());
  }

  @Test
  void addUrlCategoryTest() {
    Category mcategory = new Category();
    Long id1 = 1L;
    Long id2 = 1L;
    when(categoryService.putToCategory(any(), any())).thenReturn(null);
    assertNull(controller.addUrlCategory(id1, id2).getBody());
    when(categoryService.putToCategory(any(), any())).thenReturn(mcategory);
    assertNotNull(controller.addUrlCategory(id1, id2).getBody());
  }

  @Test
  void deleteUrlCategoryTest() {
    Category mcategory = new Category();
    Long id1 = 1L;
    Long id2 = 1L;
    when(categoryService.removeFromCategory(any(), any())).thenReturn(null);
    assertNull(controller.removeUrlCategory(id1, id2).getBody());
    when(categoryService.removeFromCategory(any(), any())).thenReturn(mcategory);
    assertNotNull(controller.removeUrlCategory(id1, id2).getBody());
  }
}
