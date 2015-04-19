package org.linuxalert.wine.dao;

import org.linuxalert.wine.model.Grape;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class GrapeMapper implements ResultSetMapper<Grape> {

  @Override public Grape map(int index, ResultSet resultSet, StatementContext statementContext)
      throws SQLException {
    String[] aliasArray = (String[])resultSet.getArray("aliases").getArray();
    SortedSet<String> aliases = new TreeSet<>(Arrays.asList(aliasArray));
    return new Grape(resultSet.getString("name"),
        resultSet.getString("description"),
        aliases);
  }
}
