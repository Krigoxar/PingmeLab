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

  @ManyToMany(mappedBy = "urls")
  @JsonIgnore
  private Set<Category> bags;

  public Set<Category> getBags() {
    return bags;
  }

  public void setBags(Set<Category> bags) {
    this.bags = bags;
  }

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    result = prime * result + ((observationStartDate == null) ? 0 : observationStartDate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObservedURL other = (ObservedURL) obj;
    if (id != other.id)
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    if (observationStartDate == null) {
      if (other.observationStartDate != null)
        return false;
    } else if (!observationStartDate.equals(other.observationStartDate))
      return false;
    return true;
  }
}