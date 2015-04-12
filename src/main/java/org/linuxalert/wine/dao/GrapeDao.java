package org.linuxalert.wine.dao;

import org.linuxalert.wine.model.Grape;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.Collection;


public interface GrapeDao {

  @SqlUpdate("insert into grape (name, description, aliases) values (:name, :description, :aliases)")
  void insert(@GrapeBinding() Grape grape);

  @SqlQuery("select * from grape where name = :name")
  @Mapper(GrapeMapper.class)
  Grape findGrapeByName(@Bind("name") String name);

  @SqlQuery("select * from grape")
  @Mapper(GrapeMapper.class)
  Collection<Grape> getAllGrapes();
}
