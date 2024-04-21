package com.pingme.ping.daos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The `ObservedUrl` class represents an entity for storing observed URLs with associated
 * observations and categories in a Java application.
 */
@Entity
@Table(name = "ObservedUrl")
public class ObservedUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observed_url_generator")
  private long id;

  @OneToMany(mappedBy = "observedUrl")
  @JsonIgnore
  private Set<Observation> observations;

  @ManyToMany(mappedBy = "urls")
  @JsonIgnore
  private Set<Category> bags = new HashSet<>();

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

  public ObservedUrl() {}

  /** The Constructor. */
  public ObservedUrl(String url) {
    this.url = url;
    this.observationStartDate = new Date();
  }

  /** The Constructor. */
  public ObservedUrl(String url, Date observationStartDate) {
    this.url = url;
    this.observationStartDate = observationStartDate;
  }

  public long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
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
    return "ObservedUrl [id =" + id + ", url=" + url + ", date=" + observationStartDate + "]";
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, observationStartDate);
  }
}
