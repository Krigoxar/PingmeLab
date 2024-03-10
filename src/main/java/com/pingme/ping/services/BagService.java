package com.pingme.ping.services;

import org.springframework.stereotype.Service;

import com.pingme.ping.daos.*;
import com.pingme.ping.daos.model.*;

@Service
public class BagService {

    private BagOfURLSRepo bagOfURLSRepo;
    
    public BagService(BagOfURLSRepo bagOfURLSRepo) {
        this.bagOfURLSRepo = bagOfURLSRepo;
    }

    public BagOfURLS getBagByName(String name) {
        return bagOfURLSRepo.findByName(name).get(0);
    }

    public BagOfURLS addBag(BagOfURLS bagOfURLS) {
        return bagOfURLSRepo.save(bagOfURLS);
    }

    public boolean deleteBag(Long id) {
        var res = bagOfURLSRepo.findById(id);
        if(res.isEmpty())
        {
            return false;
        }
        bagOfURLSRepo.delete(res.get());
        return true;
    }

    public BagOfURLS updateBag(BagOfURLS bag, Long id) {
        var res = bagOfURLSRepo.findById(id);
        if (res.isEmpty()) {
            return null;
        }
        var obj = res.get();
        obj.setName(bag.getName());
        return bagOfURLSRepo.save(obj);
    }
}
