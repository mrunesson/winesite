package org.linuxalert.wine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Wine {

  private long id;

  @Length(min = 1, max = 300) private String name;

  public Wine() {
    // Jackson deserialization
  }

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
}
