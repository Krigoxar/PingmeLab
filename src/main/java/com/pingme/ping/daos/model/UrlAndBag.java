package com.pingme.ping.daos.model;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
public class UrlAndBag {
    
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "ObservedURLs_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ObservedURL url;

    @ManyToOne
    @JoinColumn(name = "BagOfURLS_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BagOfURLS bag;

    public UrlAndBag() {
    }

    public UrlAndBag(ObservedURL url, BagOfURLS bag) {
        this.url = url;
        this.bag = bag;
    }

    public Long getId() {
        return id;
    }

    public ObservedURL getUrl() {
        return url;
    }

    public void setUrl(ObservedURL url) {
        this.url = url;
    }

    public BagOfURLS getBag() {
        return bag;
    }

    public void setBag(BagOfURLS bag) {
        this.bag = bag;
    }
}
