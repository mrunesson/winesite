package org.linuxalert.wine;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.linuxalert.dropwizard.test.util.DropwizardDockerResource;
import org.linuxalert.wine.model.Grape;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class GrapeIT {

  public static final String CONFIG_PATH = "src/it/resources/winedb-test.yaml";
  private static Client client;

  @ClassRule
  public static DropwizardDockerResource RULE =
      new DropwizardDockerResource(GrapeApplication.class, CONFIG_PATH);


  @Before
  public void setup() throws Exception {
    RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    client = ClientBuilder.newClient();
  }

  @After
  public void tearDown() throws Exception {
    RULE.getApplication().run("db", "drop-all", "--confirm-delete-everything", CONFIG_PATH);
  }

  @Test
  public void postGrape() {
    SortedSet<String> aliases = new TreeSet<>();
    Grape grape = new Grape("foo", "bar", aliases);
    Entity<Grape> entity = Entity.entity(grape, MediaType.APPLICATION_JSON);
    Response response = client.target("http://localhost:8088/grape")
        .request(MediaType.APPLICATION_JSON)
        .buildPost(entity).invoke();
    Assert.assertEquals(204, response.getStatus());

    response = client.target("http://localhost:8088/grape/" + grape.getName())
        .request(MediaType.APPLICATION_JSON)
        .buildGet().invoke();
    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals(grape, response.readEntity(Grape.class));
  }

  @Test
  public void getGrapes() {
    SortedSet<String> aliases = new TreeSet<>();
    aliases.add("Syrah");
    Grape grape1 = new Grape("Shiraz",
        "A dark-skinned grape variety grown throughout the world", aliases);
    Entity<Grape> entity = Entity.entity(grape1, MediaType.APPLICATION_JSON);
    client.target("http://localhost:8088/grape")
        .request(MediaType.APPLICATION_JSON)
        .buildPost(entity).invoke();

    aliases = new TreeSet<>();
    Grape grape2 = new Grape("foo", "bar", aliases);
    entity = Entity.entity(grape2, MediaType.APPLICATION_JSON);
    client.target("http://localhost:8088/grape")
        .request(MediaType.APPLICATION_JSON)
        .buildPost(entity).invoke();

    Response response = client.target("http://localhost:8088/grape")
        .request(MediaType.APPLICATION_JSON)
        .buildGet().invoke();
    Assert.assertEquals(200, response.getStatus());
    GenericType<Collection<Grape>> grapeCollection = new GenericType<Collection<Grape>>() {};
    Collection<Grape> result = response.readEntity(grapeCollection);
    Assert.assertEquals(2, result.size());
    assertThat(result, containsInAnyOrder(grape1, grape2));
  }

}
