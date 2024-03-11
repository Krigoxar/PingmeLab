package com.pingme.ping.services;

import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Category> getCategoryByName(String name) {
        return categoryRepo.findByName(name);
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
        categoryRepo.delete(res.get());
        return true;
    }

    public Category updateCategory(Category category, Long id) {
        var res = categoryRepo.findById(id);
        if (res.isEmpty()) {
            return null;
        }
        var obj = res.get();
        obj.setName(category.getName());
        return categoryRepo.save(obj);
    }
}
