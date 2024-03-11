package com.pingme.ping.daos.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "ObservedURLs")
public class ObservedURL {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observedurls_generator")
  private long id;

  @OneToMany(mappedBy = "observedurl")
  @JsonIgnore
  private Set<Observation> observations;

  @OneToMany(mappedBy = "urls")
  @JsonIgnore
  private Set<Category> bags;

  @Column(name = "url")
  private String url;

  @Column(name = "observationStartDate")
  private Date observationStartDate;

  public ObservedURL() { }

  public ObservedURL(String url, Date observationStartDate) {
    this.url = url;
    this.observationStartDate = observationStartDate;
  }

  public long getId() {
    return id;
  }

  public String getURL() {
    return url;
  }

  public void setURL(String url) {
    this.url = url;
  }

  public Date getDate() {
    return observationStartDate;
  }

  public void setDate(Date date) {
    this.observationStartDate = date;
  }

  @Override
  public String toString() {
    return "ObservedURL [id =" + id +", url=" + url + ", date=" + observationStartDate + "]";
  }
}