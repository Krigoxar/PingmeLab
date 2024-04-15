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

/**
 * The CategoryController class in Java defines RESTful endpoints for managing categories and URLs
 * within a web application.
 */
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

  /**
   * This Java function retrieves all categories or a specific category by name based on the
   * provided query parameter.
   *
   * @param name The `name` parameter in the `getAllCategorys` method is used as a query parameter
   *     to filter the list of categories. If a value is provided for the `name` parameter, the
   *     method will retrieve the category with that specific name using the
   *     `categoryService.getCategoryByName(name)` method and
   * @return A ResponseEntity object containing a list of Category objects is being returned. The
   *     HTTP status code of the response is either HttpStatus.NO_CONTENT if the list is empty, or
   *     HttpStatus.OK if the list contains Category objects.
   */
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

  /**
   * This Java function deletes a category by ID and returns HTTP status code 204 if successful, or
   * 404 if the category is not found.
   *
   * @param id The `id` parameter in the `@DeleteMapping` annotation represents the unique
   *     identifier of the category that you want to delete. This identifier is typically used to
   *     locate and delete the specific category from the database or any other data source.
   * @return The deleteCategory method returns an HTTP status code. If the category with the
   *     specified id is successfully deleted, it returns HttpStatus.NO_CONTENT (204). If the
   *     category is not found or cannot be deleted, it returns HttpStatus.NOT_FOUND (404).
   */
  @DeleteMapping("/categorys/{id}")
  public HttpStatus deleteCategory(@PathVariable Long id) {
    if (categoryService.deleteCategory(id)) {
      return HttpStatus.NO_CONTENT;
    }
    return HttpStatus.NOT_FOUND;
  }

  /**
   * This Java function updates a category with the specified ID and returns a response entity with
   * the updated category or a NOT_FOUND status if the category is not found.
   *
   * @param id The `id` parameter in the `updateCategory` method represents the unique identifier of
   *     the category that you want to update. This identifier is typically used to locate the
   *     specific category in the database that needs to be updated.
   * @param entity The `entity` parameter in the `updateCategory` method represents the updated
   *     category object that is sent in the request body when updating a category. This object
   *     contains the new information that will be used to update the category with the specified
   *     `id`.
   * @return The `updateCategory` method returns a `ResponseEntity` object. If the `res` object is
   *     null, a response with status code `NOT_FOUND` is returned. Otherwise, a response with the
   *     updated `Category` object and status code `OK` is returned.
   */
  @PutMapping("/categorys/{id}")
  public ResponseEntity<Category> updateCategory(
      @PathVariable(required = true) Long id, @RequestBody(required = true) Category entity) {
    var res = categoryService.updateCategory(entity, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  /**
   * This Java function adds a URL to a category and returns the updated category object or a
   * NOT_FOUND response if the operation fails.
   *
   * @param id The `id` parameter in the `addUrlCategory` method represents the ID of the category
   *     to which you want to add a URL.
   * @param urlId The `urlId` parameter in the `addUrlCategory` method represents the ID of the URL
   *     that you want to associate with a specific category. This method is designed to add a URL
   *     to a category based on the provided `id` of the category and the `urlId` of the URL
   * @return The method is returning a ResponseEntity object containing either a Category object
   *     with HTTP status OK (200) if the observedService.putToCategory method returns a non-null
   *     result, or a ResponseEntity object with HTTP status NOT_FOUND (404) if the
   *     observedService.putToCategory method returns null.
   */
  @PostMapping("/categorys/{id}/url")
  public ResponseEntity<Category> addUrlCategory(@PathVariable Long id, @RequestParam Long urlId) {

    var res = observedService.putToCategory(urlId, id);
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  /**
   * This Java function removes a URL from a category and returns a response entity with the updated
   * category or a not found status.
   *
   * @param id The `id` parameter in the `@DeleteMapping` annotation refers to the identifier of the
   *     category that you want to remove a URL from. It is extracted from the path variable in the
   *     URL.
   * @param urlId The `urlId` parameter in the `removeUrlCategory` method is a `Long` type parameter
   *     that is passed as a request parameter in the URL. It is used to identify the specific URL
   *     that needs to be removed from the category with the given `id`.
   * @return The method `removeUrlCategory` is returning a `ResponseEntity Category`. If the `res`
   *     is `null`, it returns a `ResponseEntity` with `HttpStatus.NOT_FOUND`. Otherwise, it returns
   *     a `ResponseEntity` with the result `res` and `HttpStatus.OK`.
   */
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
