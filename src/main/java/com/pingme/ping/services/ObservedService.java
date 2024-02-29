package com.pingme.ping.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.ObservedURLRepo;
import com.pingme.ping.daos.model.*;
import com.pingme.ping.dtos.NewURL;

import java.util.Date;

@Service
public class ObservedService {

    private ObservedURLRepo observedURLRepo;

    public ObservedService(ObservedURLRepo observedURLRepo) {
        this.observedURLRepo = observedURLRepo;
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
}
