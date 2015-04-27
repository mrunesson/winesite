package org.linuxalert.wine.wine;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.linuxalert.wine.wine.dao.WineDao;
import org.linuxalert.wine.wine.resource.WineResource;
import org.skife.jdbi.v2.DBI;


public class WineApplication extends Application<WineConfiguration> {

  public static void main(String[] args) throws Exception {
    new WineApplication().run(args);
  }

  @Override
  public String getName() {
    return "Wine";
  }

  @Override
  public void initialize(Bootstrap<WineConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<WineConfiguration>() {
      @Override public DataSourceFactory getDataSourceFactory(WineConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });
  }

  @Override
  public void run(WineConfiguration configuration, Environment environment)
    throws ClassNotFoundException {

    final DBIFactory factory = new DBIFactory();
    final DBI dbContext =
        factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
    registerWine(environment, dbContext);
  }

  private void registerWine(Environment environment, DBI dbContext) {
    final WineDao wineDao = dbContext.onDemand(WineDao.class);
    environment.jersey().register(new WineResource(wineDao));
  }

}
