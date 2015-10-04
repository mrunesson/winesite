package org.linuxalert.wine;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import io.dropwizard.testing.ConfigOverride;
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
  private String baseUrl;
  private static DefaultDockerClient docker;

  static {
    try {
       docker = DefaultDockerClient.fromEnv().build();
    } catch (DockerCertificateException e) {
      e.printStackTrace();
      System.err.print(e.getMessage());
    }
  }

  private static ConfigOverride configOverride = ConfigOverride.config(
      "database.url", "jdbc:postgresql://" + docker.getHost() + "/postgres");;

  @ClassRule
  public static DropwizardDockerResource RULE =
      new DropwizardDockerResource(WineApplication.class, CONFIG_PATH, configOverride);

  @Before
  public void setup() throws Exception {
    RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    baseUrl = "http://localhost:8088/wine/";
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
    Response response = client.target(baseUrl)
        .request(MediaType.APPLICATION_JSON)
        .buildPost(entity).invoke();
    Assert.assertEquals(204, response.getStatus());

    response = client.target(baseUrl + wine.getId())
        .request(MediaType.APPLICATION_JSON)
        .buildGet().invoke();
    Assert.assertEquals(200, response.getStatus());
    Wine wineResponse = response.readEntity(Wine.class);
    Assert.assertEquals(wine, wineResponse);
  }

}
