package com.pingme.ping.services;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.*;
import com.pingme.ping.daos.model.*;

@Service
public class ObservationService {

    private ObservationRepo observationRepo;

    public void checkConnections(List<ObservedURL> ursl){
        for (ObservedURL observedURL : ursl) {
            boolean isResponding = false;
            try
            {
                isResponding = InetAddress.getByName(observedURL.getURL()).isReachable(1000);
            }
            catch(Exception e)
            {
                isResponding = false;
            }
            var res = new Observation();
            res.setIsResponding(isResponding);
            res.setObservationDate(new Date());
            res.setObservedURL(observedURL);
            observationRepo.save(res);
        }
    }

    public ObservationService(ObservationRepo observationRepo) {
        this.observationRepo = observationRepo;
    }

    public List<Observation> getAllObservations() {
        return observationRepo.findAll();
    }

    public List<Observation> getObservationsByURL(ObservedURL url) {
        return observationRepo.findByObservedURL(url);
    }

    public Observation addObservation(Observation obs) {
        return observationRepo.save(obs);
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
        temp.setIsResponding(entity.getIsResponding());
        temp.setObservationDate(entity.getObsertvationDate());
        temp.setObservedURL(entity.getobservedURL());
        return observationRepo.save(entity);
    }
}
