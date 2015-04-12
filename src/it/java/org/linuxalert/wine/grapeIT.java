package org.linuxalert.wine;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.linuxalert.dropwizard.test.util.DropwizardDockerResource;
import org.linuxalert.wine.model.Grape;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.SortedSet;
import java.util.TreeSet;

public class grapeIT {

  public static final String CONFIG_PATH = "src/it/resources/winedb-test.yaml";

  @ClassRule
  public static DropwizardDockerResource RULE =
      new DropwizardDockerResource(WineDbApplication.class, CONFIG_PATH);

  @BeforeClass
  public static void migrateDb() throws Exception {
    RULE.getApplication().run("db", "migrate", CONFIG_PATH);
  }

  @Test
  public void postGrape() {
    Client client = ClientBuilder.newClient();

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

}
