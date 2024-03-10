package com.pingme.ping.daos.model;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "urlsbags")
public class BagOfURLS {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "urlsbag_generator")
    private Long id;

    @OneToMany(mappedBy = "bag")
    @JsonIgnore
    private Set<UrlAndBag> urls;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BagOfURLS [id=" + id + ", name=" + name + "]";
    }


}
