package org.linuxalert.wine;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.linuxalert.wine.dao.GrapeDao;
import org.linuxalert.wine.dao.WineDao;
import org.linuxalert.wine.resource.GrapeResource;
import org.linuxalert.wine.resource.WineResource;
import org.skife.jdbi.v2.DBI;


public class GrapeApplication extends Application<GrapeConfiguration> {

  public static void main(String[] args) throws Exception {
    new GrapeApplication().run(args);
  }

  @Override
  public String getName() {
    return "Grape";
  }

  @Override
  public void initialize(Bootstrap<GrapeConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<GrapeConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(GrapeConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });
  }

  @Override
  public void run(GrapeConfiguration configuration, Environment environment)
    throws ClassNotFoundException {

    final DBIFactory factory = new DBIFactory();
    final DBI dbContext =
        factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
    registerGrape(environment, dbContext);
    registerWine(environment, dbContext);
  }

  private void registerWine(Environment environment, DBI dbContext) {
    final WineDao wineDao = dbContext.onDemand(WineDao.class);
    environment.jersey().register(new WineResource(wineDao));
  }

  private void registerGrape(Environment environment, DBI dbContext) {
    final GrapeDao grapeDao = dbContext.onDemand(GrapeDao.class);
    environment.jersey().register(new GrapeResource(grapeDao));
  }

}
