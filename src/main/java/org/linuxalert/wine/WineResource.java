package org.linuxalert.wine;

import com.google.common.base.Optional;

import com.codahale.metrics.annotation.Timed;
import org.linuxalert.wine.model.Wine;

import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/wine")
@Produces(MediaType.APPLICATION_JSON)
public class WineResource {
  private final WineDao wineDao;

  public WineResource(WineDao wineDao) {
    this.wineDao = wineDao;
    this.counter = new AtomicLong();
  }

  @GET
  @Path("/{id}")
  @Timed
  public Wine getWine(@PathParam("id") Long id) {
    String wine = wineDao.findWineById(id);
    return new Wine(id, wine);
  }

  @POST
  @Timed
  public void postWine(@Valid Wine wine) {
    wineDao.insert(wine.getId(), wine.getName());
  }
}
