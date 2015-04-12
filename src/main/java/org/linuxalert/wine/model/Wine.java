package org.linuxalert.wine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class Wine {

  private long id;

  @Length(min = 1, max = 300) private String name;

  public Wine() {}

  public Wine(long id, String name) {
    this.id = id;
    this.name = name;
  }

  @JsonProperty public long getId() {
    return id;
  }

  @JsonProperty public String getName() {
    return name;
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Wine wine = (Wine) obj;
    return Objects.equals(id, wine.id) && Objects.equals(name, wine.name);
  }

  @Override public int hashCode() {
    return Objects.hash(id, name);
  }
}
