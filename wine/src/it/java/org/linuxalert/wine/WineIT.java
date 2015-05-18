package org.linuxalert.wine;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.linuxalert.wine.lib.dropwizard.test.util.DropwizardDockerResource;
import org.linuxalert.wine.wine.WineApplication;
import org.linuxalert.wine.wine.model.Wine;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;


public class WineIT {

  public static final String CONFIG_PATH = "src/it/resources/winedb-test.yaml";
  private static Client client;

  @ClassRule
  public static DropwizardDockerResource RULE =
      new DropwizardDockerResource(WineApplication.class, CONFIG_PATH);

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
  public void postWine() {
    Wine wine = new Wine("foo", "bar", new HashSet<>());
    Entity<Wine> entity = Entity.entity(wine, MediaType.APPLICATION_JSON);
    Response response = client.target("http://localhost:8088/wine")
        .request(MediaType.APPLICATION_JSON)
        .buildPost(entity).invoke();
    Assert.assertEquals(204, response.getStatus());

    response = client.target("http://localhost:8088/wine/" + wine.getId())
        .request(MediaType.APPLICATION_JSON)
        .buildGet().invoke();
    Assert.assertEquals(200, response.getStatus());
    Wine wineRespons = response.readEntity(Wine.class);
    Assert.assertEquals(wine, wineRespons);
  }

}
