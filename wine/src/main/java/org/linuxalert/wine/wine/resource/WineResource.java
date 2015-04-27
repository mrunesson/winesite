package org.linuxalert.wine.wine.resource;

import com.codahale.metrics.annotation.Timed;
import org.linuxalert.wine.wine.dao.WineDao;
import org.linuxalert.wine.wine.model.Wine;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/wine")
@Produces(MediaType.APPLICATION_JSON)
public class WineResource {
  private final WineDao wineDao;

  public WineResource(WineDao wineDao) {
    this.wineDao = wineDao;
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
