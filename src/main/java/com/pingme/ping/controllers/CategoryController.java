package com.pingme.ping.controllers;

import com.pingme.ping.daos.model.Category;
import com.pingme.ping.dtos.CategoryName;
import com.pingme.ping.services.CategoryService;
import com.pingme.ping.services.ObservedUrlService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CategoryController {

  private CategoryService categoryService;
  private ObservedUrlService observedService;

  CategoryController(CategoryService categoryService, ObservedUrlService observedService) {
    this.categoryService = categoryService;
    this.observedService = observedService;
  }

  @GetMapping("/categorys")
  public ResponseEntity<List<Category>> getAllCategorys(
      @RequestParam(required = false) String name) {
    List<Category> categorys = new ArrayList<>();

    if (name == null) {
      categorys = categoryService.getAllCategorys();
    } else {

      var obj = categoryService.getCategoryByName(name);

      if (obj != null) {
        categorys.add(obj);
      }
    }

    if (categorys.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(categorys, HttpStatus.OK);
  }

  @PostMapping("/categorys")
  public Category createNewCategory(@RequestBody(required = true) CategoryName name) {
    return categoryService.addCategory(name);
  }

  @DeleteMapping("/categorys/{id}")
  public HttpStatus deleteCategory(@PathVariable Long id) {
    if (categoryService.deleteCategory(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }

  @PutMapping("/categorys/{id}")
  public ResponseEntity<Category> updateCategory(
      @PathVariable Long id, @RequestBody Category entity) {
    var res = categoryService.updateCategory(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PostMapping("/categorys/{id}/url")
  public ResponseEntity<Category> addUrlCategory(@PathVariable Long id, @RequestParam Long urlId) {

    var res = observedService.putToCategory(urlId, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @DeleteMapping("/categorys/{id}/url")
  public ResponseEntity<Category> removeUrlCategory(
      @PathVariable Long id, @RequestParam Long urlId) {

    var res = observedService.removeFromCategory(urlId, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
