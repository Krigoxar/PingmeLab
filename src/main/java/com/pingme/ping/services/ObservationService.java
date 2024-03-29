package com.pingme.ping.services;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.*;
import com.pingme.ping.daos.model.*;
import com.pingme.ping.dtos.NewURL;

@Service
public class ObservationService {

    private ObservationRepo observationRepo;
    private URLRepo observedURLRepo;

    public ObservationService(ObservationRepo observationRepo, URLRepo observedURLRepo) {
        this.observationRepo = observationRepo;
        this.observedURLRepo = observedURLRepo;
    }

    public List<Observation> getAllObservations() {
        return observationRepo.findAll();
    }

    public List<Observation> getObservationsByURL(ObservedURL url) {
        return observationRepo.findByObservedurl(url);
    }

    public Observation addObservation(NewURL url) {boolean isResponding = false;
        try
        {
            isResponding = InetAddress.getByName(url.url()).isReachable(1000);
        }
        catch(Exception e)
        {
            isResponding = false;
        }
        var res = new Observation();
        res.setResponding(isResponding);
        res.setObservationDate(new Date());
        var obsurl = observedURLRepo.findByUrl(url.url());
        if(obsurl.isEmpty()) {
            return null;
        }
        res.setObservedurl(obsurl.get(0));
        return observationRepo.save(res);
    }

    public boolean deleteObservation(Long id) {
        var obs = observationRepo.findById(id);
        if(obs.isEmpty()) {
            return false;
        }

        observationRepo.delete(obs.get());
        return true;
    }

    public Observation updateObservation(Observation entity, Long id) {
        var obs = observationRepo.findById(id);
        if(obs.isEmpty()){
            return null;
        }

        var temp = obs.get();
        temp.setResponding(entity.isResponding());
        temp.setObservationDate(entity.getObservationDate());
        temp.setObservedurl(entity.getObservedurl());
        return observationRepo.save(entity);
    }
}
