package com.pingme.ping.daos.model;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "categorys")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "category_url", 
        joinColumns = @JoinColumn(name = "categorys_id"), 
        inverseJoinColumns = @JoinColumn(name = "ObservedURLs_id"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ObservedURL> urls;

    @Column(name = "name")
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

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
        return "Category [id=" + id + ", name=" + name + "]";
    }

    public Set<ObservedURL> getUrls() {
        return urls;
    }


}
