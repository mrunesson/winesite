package org.linuxalert.wine;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.linuxalert.wine.lib.dropwizard.test.util.DropwizardDockerResource;
import org.linuxalert.wine.wine.WineApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


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

}
