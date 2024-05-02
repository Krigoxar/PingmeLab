package com.pingme.ping.services;

import com.pingme.ping.components.Cache;
import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.CategoryName;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * The CategoryService class in Java provides methods for managing categories, utilizing a
 * repository and cache for efficient data retrieval and storage.
 */
@Service
public class CategoryService {

  private CategoryRepository categoryRepository;
  private UrlRepository observedUrlRepo;
  private Cache<String, Category> cache;

  /** The Constructor. */
  public CategoryService(
      CategoryRepository categoryRepository,
      Cache<String, Category> cash,
      UrlRepository observedUrlRepo) {
    this.categoryRepository = categoryRepository;
    this.observedUrlRepo = observedUrlRepo;
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
    List<Category> obj = categoryRepository.findByName(name);

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
    if (categoryName.name().isEmpty()) {
      return null;
    }
    Category cat = new Category(categoryName.name());
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
    Optional<Category> res = categoryRepository.findById(id);
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
    Optional<Category> res = categoryRepository.findById(id);
    if (res.isEmpty()) {
      return null;
    }

    cache.remove(category.getName());

    Category obj = res.get();
    obj.setName(category.getName());
    return categoryRepository.save(obj);
  }

  /**
   * This Java function adds a URL to a category by retrieving the category and URL objects from
   * repositories and updating the category's list of URLs.
   *
   * @param urlId The `urlId` parameter is the unique identifier of the URL that you want to
   *     associate with a category.
   * @param categoryId The `categoryId` parameter represents the unique identifier of the category
   *     to which you want to add the URL.
   * @return The method `putToCategory` is returning an instance of the `Category` class.
   */
  public Category putToCategory(Long urlId, Long categoryId) {
    Optional<Category> bag = categoryRepository.findById(categoryId);
    Optional<ObservedUrl> url = observedUrlRepo.findById(urlId);
    if (bag.isEmpty() || url.isEmpty()) {
      return null;
    }

    Category category = bag.get();
    category.getUrls().add(url.get());

    return categoryRepository.save(category);
  }

  /**
   * The function removes a URL from a category and saves the updated category.
   *
   * @param urlId The `urlId` parameter is the unique identifier of the URL that you want to remove
   *     from a specific category.
   * @param categoryId The `categoryId` parameter represents the unique identifier of the category
   *     from which you want to remove a URL.
   * @return The `removeFromCategory` method returns a `Category` object after removing a specific
   *     URL from the category's list of URLs and saving the updated category in the repository. If
   *     the category or URL is not found, or if the URL is not present in the category's list, the
   *     method returns `null`.
   */
  public Category removeFromCategory(Long urlId, Long categoryId) {
    Optional<Category> bag = categoryRepository.findById(categoryId);
    Optional<ObservedUrl> url = observedUrlRepo.findById(urlId);
    if (bag.isEmpty() || url.isEmpty()) {
      return null;
    }

    Category category = bag.get();
    if (!category.getUrls().contains(url.get())) {
      return null;
    }

    category.getUrls().remove(url.get());

    return categoryRepository.save(category);
  }
}
