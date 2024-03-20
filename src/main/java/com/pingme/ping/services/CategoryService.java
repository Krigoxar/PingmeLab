package com.pingme.ping.services;

import org.springframework.stereotype.Service;
import java.util.*;

import com.pingme.ping.daos.*;
import com.pingme.ping.daos.model.*;
import com.pingme.ping.dtos.CategoryName;

@Service
public class CategoryService {

    private CategoryRepo categoryRepo;
    
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategorys() {
        return categoryRepo.findAll();
    }

    Map<String, Category> cash = new HashMap<>();

    public Category getCategoryByName(String name) {
        if(cash.containsKey(name))
        {
            return cash.get(name);
        }
        var obj = categoryRepo.findByName(name).get(0);
        cash.put(name, obj);
        return obj;
    }

    public Category addCategory(CategoryName categoryName) {
        return categoryRepo.save(new Category(categoryName.name()));
    }

    public boolean deleteCategory(Long id) {
        var res = categoryRepo.findById(id);
        if(res.isEmpty())
        {
            return false;
        }

        cash.clear();

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
