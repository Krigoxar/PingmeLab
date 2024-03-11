package com.pingme.ping.services;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.ObservationRepo;
import com.pingme.ping.daos.ObservedURLRepo;
import com.pingme.ping.daos.model.*;

import java.net.InetAddress;
import java.util.Date;

@Service
public class StatsService {

    private ObservationRepo observationRepo;
    private ObservedURLRepo observedURLRepo;
    
    public StatsService(ObservedURLRepo observedURLRepo, ObservationRepo observationRepo) {
        this.observedURLRepo = observedURLRepo;
        this.observationRepo = observationRepo;
        startCorutine();
    }

    public void startCorutine() {
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                var uRLs = observedURLRepo.findAll();
                for (ObservedURL observedURL : uRLs) {
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
                    res.setResponding(isResponding);
                    res.setObservationDate(new Date());
                    res.setObservedurl(observedURL);
                    observationRepo.save(res);
                }
            }
        };
        timer.schedule (hourlyTask, 10l, 1000*60*5L);
    }
}
