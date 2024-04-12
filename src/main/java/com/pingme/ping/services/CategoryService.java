package com.pingme.ping.services;

import com.pingme.ping.components.Cache;
import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.dtos.CategoryName;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The CategoryService class in Java provides methods for managing categories, utilizing a
 * repository and cache for efficient data retrieval and storage.
 */
@Service
public class CategoryService {

  private CategoryRepository categoryRepository;
  private Cache<String, Category> cache;

  public CategoryService(CategoryRepository categoryRepository, Cache<String, Category> cash) {
    this.categoryRepository = categoryRepository;
    this.cache = cash;
  }

  public List<Category> getAllCategorys() {
    return categoryRepository.findAll();
  }

  /**
   * The function `getCategoryByName` retrieves a Category object by name, first checking a cache
   * and then querying a repository if not found.
   *
   * @param name The `name` parameter in the `getCategoryByName` method is a String representing the
   *     name of the category that is being searched for. The method first checks if the category
   *     with the given name is present in the cache. If it is found in the cache, it returns the
   *     category directly from the
   * @return The method `getCategoryByName` returns a `Category` object with the specified name. If
   *     the category with the given name is found in the cache, it is returned directly from the
   *     cache. If not found in the cache, it is retrieved from the `categoryRepository` by calling
   *     `findByName(name)`. If the category is found in the repository, it is stored in the cache
   *     and returned
   */
  public Category getCategoryByName(String name) {
    if (cache.containsKey(name)) {
      return cache.get(name);
    }
    var obj = categoryRepository.findByName(name);

    if (obj.isEmpty()) {
      return null;
    }

    cache.put(name, obj.get(0));
    return obj.get(0);
  }

  /**
   * The `addCategory` function creates a new Category object, adds it to a cache, and saves it
   * using a repository.
   *
   * @param categoryName The `categoryName` parameter is an object of type `CategoryName` which
   *     represents the name of a category.
   * @return The method `addCategory` is returning an object of type `Category`.
   */
  public Category addCategory(CategoryName categoryName) {
    var cat = new Category(categoryName.name());
    cache.put(categoryName.name(), cat);
    return categoryRepository.save(cat);
  }

  /**
   * The function `deleteCategory` deletes a category by its ID from a repository and removes its
   * name from a cache.
   *
   * @param id The `id` parameter is a `Long` value representing the unique identifier of the
   *     category that needs to be deleted from the repository.
   * @return The `deleteCategory` method returns a boolean value - `true` if the category with the
   *     specified `id` was successfully deleted, and `false` if the category was not found in the
   *     repository.
   */
  public boolean deleteCategory(Long id) {
    var res = categoryRepository.findById(id);
    if (res.isEmpty()) {
      return false;
    }

    cache.remove(res.get().getName());

    categoryRepository.delete(res.get());
    return true;
  }

  /**
   * The function updates a category with the provided information and saves it to the repository
   * after removing the category name from the cache.
   *
   * @param category The `category` parameter is an object of type `Category` that contains the
   *     updated information for the category that needs to be updated in the database.
   * @param id The `id` parameter is a `Long` value that represents the unique identifier of the
   *     category that needs to be updated.
   * @return The `updateCategory` method returns a `Category` object after updating it in the
   *     database. If the category with the specified `id` is not found in the repository, it
   *     returns `null`.
   */
  public Category updateCategory(Category category, Long id) {
    var res = categoryRepository.findById(id);
    if (res.isEmpty()) {
      return null;
    }

    cache.remove(category.getName());

    var obj = res.get();
    obj.setName(category.getName());
    return categoryRepository.save(obj);
  }
}
