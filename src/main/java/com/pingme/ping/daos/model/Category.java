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
import java.util.Set;

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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Category other = (Category) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }
}
