package org.linuxalert.wine;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface WineDao {

  @SqlUpdate("insert into wine (id, name) values (:id, :name)")
  void insert(@Bind("id") long id, @Bind("name") String name);

  @SqlQuery("select name from wine where id = :id")
  String findWineById(@Bind("id") long id);
}
