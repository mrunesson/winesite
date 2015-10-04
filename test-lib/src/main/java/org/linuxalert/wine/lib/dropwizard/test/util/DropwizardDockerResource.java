package org.linuxalert.wine.lib.dropwizard.test.util;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


/**
 * A JUnit rule for starting and stopping your application and postgres database at
 * the start and end of a test class.
 *
 * @param <C> the configuration type
 */
public class DropwizardDockerResource<C extends Configuration> extends DropwizardAppRule {

  private static final String POSTGRES_PORT = "5432";

  public DefaultDockerClient docker;
  private String containerId;

  public DropwizardDockerResource(Class<? extends Application<C>> applicationClass,
      @Nullable String configPath,
      ConfigOverride... configOverrides) {
    super(applicationClass, configPath, configOverrides);
  }

  @Override public Statement apply(Statement base, Description description) {
    return super.apply(base, description);
  }

  @Override public void before() {
    try {
      docker = DefaultDockerClient.fromEnv().build();
      docker.pull("postgres:latest");
      final Map<String, List<PortBinding>> portBindings = new HashMap<>();
      List<PortBinding> hostPorts = new ArrayList<>();
      hostPorts.add(PortBinding.of("0.0.0.0", POSTGRES_PORT));
      portBindings.put(POSTGRES_PORT, hostPorts);
      ContainerConfig config =
          ContainerConfig.builder().image("postgres").env("POSTGRES_PASSWORD=pwd")
              .hostConfig(HostConfig.builder().portBindings( portBindings).build())
              .exposedPorts(POSTGRES_PORT).cmd("postgres")
              .build();
      ContainerCreation creation = docker.createContainer(config);
      containerId = creation.id();

      docker.startContainer(containerId);
      Thread.sleep(10000); // TODO: Might be a cleaner way to do this.
    } catch (Exception e) {
      e.printStackTrace();
      System.err.print(e.getMessage());
    }

    super.before();
  }

  @Override public void after() {
    super.after();
    try {
      docker.killContainer(containerId);
      docker.removeContainer(containerId);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.print("Failed to kill docker container " + containerId + ".");
      System.err.print(e.getMessage());
    }
  }

}
