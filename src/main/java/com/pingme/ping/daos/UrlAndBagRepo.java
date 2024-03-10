package com.pingme.ping.daos;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

import com.pingme.ping.daos.model.*;

@Repository
public interface UrlAndBagRepo extends JpaRepository<UrlAndBag, Long> {
    
}