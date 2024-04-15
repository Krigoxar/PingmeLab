package com.pingme.ping.daos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The `Observation` class represents an entity for storing observations with attributes such as id,
 * observation date, and response status.
 */
@Entity
@Table(name = "observations")
public class Observation {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observation_generator")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ObservedUrls_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private ObservedUrl observedUrl;

  @Column(name = "observationDate")
  private Date observationDate;

  @Column(name = "IsResponding")
  private boolean isResponding;

  public Observation() {}

  public Observation(ObservedUrl observedUrl, boolean isResponding) {
    this.observedUrl = observedUrl;
    this.isResponding = isResponding;
  }

  @Override
  public String toString() {
    return "Observation [id="
        + id
        + ", observationDate="
        + observationDate
        + ", isResponding="
        + isResponding
        + "]";
  }

  public Long getId() {
    return id;
  }

  public ObservedUrl getObservedUrl() {
    return observedUrl;
  }

  public void setObservedUrl(ObservedUrl observedurl) {
    this.observedUrl = observedurl;
  }

  public Date getObservationDate() {
    return observationDate;
  }

  public void setObservationDate(Date observationDate) {
    this.observationDate = observationDate;
  }

  public boolean isResponding() {
    return isResponding;
  }

  public void setResponding(boolean isResponding) {
    this.isResponding = isResponding;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, observedUrl, observationDate, isResponding);
  }
}
