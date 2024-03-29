package com.pingme.ping.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.CategoryRepo;
import com.pingme.ping.daos.URLRepo;
import com.pingme.ping.daos.model.*;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.dtos.ObservationsCount;

import java.util.Date;
import java.util.LinkedList;

@Service
public class ObservedURLService {

    private URLRepo observedURLRepo;
    private CategoryRepo categoryRepo;

    public ObservedURLService(URLRepo observedURLRepo, CategoryRepo categoryRepo) {
        this.observedURLRepo = observedURLRepo;
        this.categoryRepo = categoryRepo;
    }
    
    public Category putToCategory(Long uRLId, Long categoryId) {
        var bag = categoryRepo.findById(categoryId);
        var url = observedURLRepo.findById(uRLId);
        if(bag.isEmpty() || url.isEmpty()) {
            return null;
        }

        var category = bag.get();
        category.getUrls().add(url.get());
        
        return categoryRepo.save(category);
    }

    public Category removeFromCategory(Long uRLId, Long categoryId) {
        var bag = categoryRepo.findById(categoryId);
        var url = observedURLRepo.findById(uRLId);
        if(bag.isEmpty() || url.isEmpty()) {
            return null;
        }

        var category = bag.get();
        if(!category.getUrls().contains(url.get())) {
            return null;
        }

        category.getUrls().remove(url.get());
        
        return categoryRepo.save(category);
    }

    public List<ObservedURL> getAllObservableURLs() {
        return observedURLRepo.findAll();
    }

    public List<ObservedURL> getObservableURLbyURL(String url) {
        return observedURLRepo.findByUrl(url);
    }

    public ObservedURL addObservedURL(NewURL url) {
        return observedURLRepo.save(new ObservedURL(url.url(), new Date()));
    }

    public boolean deleteObservedURL(Long id) {
        var forDelete = observedURLRepo.findById(id);

        if(forDelete.isEmpty()) 
        {
            return false;
        }
        
        List<Category> categories = new LinkedList<Category>();

        forDelete.get().getBags().forEach(cat -> categories.add(cat));

        for(Category category : categories)
        {
            category.getUrls().remove(forDelete.get());
        }

        observedURLRepo.delete(forDelete.get());
        return true;
    }

    public ObservedURL updateObservedURL(ObservedURL entity, Long id) {

        var observedUrlDB = observedURLRepo.findById(id);
        if(!observedUrlDB.isPresent()){
            return null;
        }

        var observedURL = observedUrlDB.get();    
        observedURL.setDate(entity.getDate());
        observedURL.setURL(entity.getURL());
        return observedURL;
    }

    public ObservationsCount getObservatioinsCount(String url) {
        var target = observedURLRepo.findByUrl(url);
        if(target.isEmpty())
        {
            return null;
        }
        Long count = observedURLRepo.countObservations(target.get(0));
        return new ObservationsCount(count, url);
    }
}
