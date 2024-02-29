package com.pingme.ping.daos.model;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Observation {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ObservedURLs_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private ObservedURL observedURL;

  @Column(name = "observationDate")
  private Date observationDate;

  @Column(name = "IsResponding")
  private boolean isResponding;

  public Long getId() {
    return id;
  }

  public ObservedURL getobservedURL() {
    return observedURL;
  }

  public void setObservedURL(ObservedURL observedURL) {
    this.observedURL = observedURL;
  }

  public Date getObsertvationDate() {
    return observationDate;
  }

  public void setObservationDate(Date observationDate) {
    this.observationDate = observationDate;
  }

  public boolean getIsResponding() {
    return isResponding;
  }

  public void setIsResponding(boolean isResponding) {
    this.isResponding = isResponding;
  }

  @Override
  public String toString() {
      return "Observation [id=" + id + ", observationDate=" + observationDate + ", observedURL=" + observedURL + ", isResponding=" + isResponding +"]";
  }
}