package org.linuxalert.wine.wine.dao;

import org.linuxalert.wine.wine.model.Wine;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface WineDao {

  @SqlUpdate("insert into wine (id, data) values (:id, :data)")
  void insert(@WineBinding() Wine wine);

  @SqlQuery("select data from wine where id = :id")
  @Mapper(WineMapper.class)
  Wine findWineById(@Bind("id") String id);
}
