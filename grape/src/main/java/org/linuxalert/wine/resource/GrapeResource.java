package org.linuxalert.wine.resource;

import com.codahale.metrics.annotation.Timed;
import org.linuxalert.wine.dao.GrapeDao;
import org.linuxalert.wine.model.Grape;

import java.util.Collection;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;


@Path("/grape")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GrapeResource {
  private final GrapeDao grapeDao;

  public GrapeResource(GrapeDao grapeDao) {
    this.grapeDao = grapeDao;
  }

  @GET
  @Timed
  public Collection<Grape> getGrape() {
    return grapeDao.getAllGrapes();
  }

  @GET
  @Path("/{name}")
  @Timed
  public Grape getGrape(@PathParam("name") String name) {
    return grapeDao.findGrapeByName(name);
  }

  @POST
  @Timed
  public void postGrape(@Valid Grape grape) {
    grapeDao.insert(grape);
  }
}
