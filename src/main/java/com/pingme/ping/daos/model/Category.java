package com.pingme.ping.daos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The Category class represents a category entity with a name, id, and a set of ObservedUrl
 * entities associated through a many-to-many relationship.
 */
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
      inverseJoinColumns = @JoinColumn(name = "ObservedUrls_id"))
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Set<ObservedUrl> urls;

  @Column(name = "name")
  private String name;

  public Category() {}

  public Category(String name) {
    this.name = name;
    urls = new HashSet<>();
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

  public Set<ObservedUrl> getUrls() {
    return urls;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, urls, name);
  }
}
