package org.linuxalert.wine;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;


public class WineDbApplication extends Application<WineDbConfiguration> {

  public static void main(String[] args) throws Exception {
    new WineDbApplication().run(args);
  }

  @Override
  public String getName() {
    return "wine";
  }

  @Override
  public void initialize(Bootstrap<WineDbConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<WineDbConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(WineDbConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });
  }

  @Override
  public void run(WineDbConfiguration configuration, Environment environment)
    throws ClassNotFoundException {

    final DBIFactory factory = new DBIFactory();
    final DBI dbContext =
        factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
    final WineDao dao = dbContext.onDemand(WineDao.class);
    environment.jersey().register(new WineResource(dao));
  }


}
