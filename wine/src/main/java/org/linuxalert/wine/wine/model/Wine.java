package org.linuxalert.wine.wine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class Wine {

  @Length(min = 1, max = 300) private String id;

  private String name;
  private URL wineURL;

  private Collection<String> grapes;

  private String winery;
  private String wineryRef;

  private String region;
  private String regionRef;

  private String wineMaker;
  private String wineMakerRef;

  private Integer vintage;

  private Collection<String> tags; // TODO: define wellknown tags (red, white, rose, sparkling, desert, fruit)

  // Description? How to handle different vintages?

  public Wine() {}

  public Wine(String id, String name, Collection<String> grapes) {
    this.id = id;
    this.name = name;
    this.grapes = grapes;
  }

  @JsonProperty public String getId() {
    return id;
  }

  @JsonProperty public String getName() {
    return name;
  }

  @JsonProperty public Collection<String> getGrapes() {
    return grapes;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Wine wine = (Wine) o;
    return Objects.equals(id, wine.id) &&
        Objects.equals(name, wine.name) &&
        grapes.containsAll(wine.grapes);
  }

  @Override public int hashCode() {
    return Objects.hash(id, name, grapes);
  }
}
