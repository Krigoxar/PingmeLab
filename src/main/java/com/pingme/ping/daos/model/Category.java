package com.pingme.ping.daos.model;

import jakarta.persistence.CascadeType;
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
@Table(name = "Category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
  private Long id;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
      name = "category_url",
      joinColumns = @JoinColumn(name = "category_id"),
      inverseJoinColumns = @JoinColumn(name = "observed_urls_id"))
  private Set<ObservedUrl> urls = new HashSet<>();

  @Column(name = "name")
  private String name;

  public Category() {}

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

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, urls, name);
  }

  public Set<ObservedUrl> getUrls() {
    return urls;
  }

  public void setUrls(Set<ObservedUrl> urls) {
    this.urls = urls;
  }
}
