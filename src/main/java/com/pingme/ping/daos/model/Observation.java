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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "observations")
public class Observation {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observation_generator")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ObservedURLs_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private ObservedUrl observedurl;

  @Column(name = "observationDate")
  private Date observationDate;

  @Column(name = "IsResponding")
  private boolean isResponding;

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

  public ObservedUrl getObservedurl() {
    return observedurl;
  }

  public void setObservedurl(ObservedUrl observedurl) {
    this.observedurl = observedurl;
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((observationDate == null) ? 0 : observationDate.hashCode());
    result = prime * result + (isResponding ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Observation other = (Observation) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (observationDate == null) {
      if (other.observationDate != null) {
        return false;
      }
    } else if (!observationDate.equals(other.observationDate)) {
      return false;
    } else if (isResponding != other.isResponding) {
      return false;
    }
    return true;
  }
}
