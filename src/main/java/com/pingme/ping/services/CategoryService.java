package com.pingme.ping.services;

import com.pingme.ping.components.Cache;
import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.dtos.CategoryName;
import java.util.List;
import org.springframework.stereotype.Service;

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

  public Category addCategory(CategoryName categoryName) {
    var cat = new Category(categoryName.name());
    cache.put(categoryName.name(), cat);
    return categoryRepository.save(cat);
  }

  public boolean deleteCategory(Long id) {
    var res = categoryRepository.findById(id);
    if (res.isEmpty()) {
      return false;
    }

    cache.remove(res.get().getName());

    categoryRepository.delete(res.get());
    return true;
  }

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
