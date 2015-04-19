package org.linuxalert.wine.resource;

import com.codahale.metrics.annotation.Timed;
import org.linuxalert.wine.dao.WineDao;
import org.linuxalert.wine.model.Wine;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;


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
