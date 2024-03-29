package com.pingme.ping.services;

import org.springframework.stereotype.Service;
import java.util.*;

import com.pingme.ping.daos.*;
import com.pingme.ping.daos.model.*;
import com.pingme.ping.dtos.CategoryName;
import com.pingme.ping.components.*;

@Service
public class CategoryService {

    private CategoryRepo categoryRepo;
    
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategorys() {
        return categoryRepo.findAll();
    }

    Cache<String, Category> cash = new Cache<>();

    public Category getCategoryByName(String name) {
        if(cash.containsKey(name))
        {
            return cash.get(name);
        }
        var obj = categoryRepo.findByName(name);

        if (obj.isEmpty()) {
            return null;
        }

        cash.put(name, obj.get(0));
        return obj.get(0);
    }

    public Category addCategory(CategoryName categoryName) {
        var cat = new Category(categoryName.name());
        cash.put(categoryName.name(), cat);
        return categoryRepo.save(cat);
    }

    public boolean deleteCategory(Long id) {
        var res = categoryRepo.findById(id);
        if(res.isEmpty())
        {
            return false;
        }

        cash.remove(res.get().getName());

        categoryRepo.delete(res.get());
        return true;
    }

    public Category updateCategory(Category category, Long id) {
        var res = categoryRepo.findById(id);
        if (res.isEmpty()) {
            return null;
        }

        cash.clear();

        var obj = res.get();
        obj.setName(category.getName());
        return categoryRepo.save(obj);
    }
}
