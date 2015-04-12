package org.linuxalert.wine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.SortedSet;

public class Grape {

  @Length(min = 2, max = 50) private String name;
  private String description;
  private SortedSet<String> aliases;

  public Grape() {}

  /**
   * Create a grape.
   * @param name Name of grape
   * @param description Description of grape
   * @param aliases Aliases for the grape
   */
  public Grape(String name, String description, SortedSet<String> aliases) {
    this.name = name;
    this.description = description;
    this.aliases = aliases;
  }

  @JsonProperty public String getName() {
    return name;
  }

  @JsonProperty public String getDescription() {
    return description;
  }

  @JsonProperty public SortedSet<String> getAliases() {
    return aliases;
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Grape grape = (Grape) obj;
    return Objects.equals(name, grape.name)
        && Objects.equals(description, grape.description)
        && Objects.equals(aliases, grape.aliases);
  }

  @Override public int hashCode() {
    return Objects.hash(name, description, aliases);
  }
}
