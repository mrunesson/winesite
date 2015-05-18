package org.linuxalert.wine.wine.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.linuxalert.wine.wine.model.Wine;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WineMapper implements ResultSetMapper<Wine> {

  @Override public Wine map(int index, ResultSet resultSet, StatementContext statementContext)
      throws SQLException {
    Wine wine;
    ObjectMapper mapper = new ObjectMapper();
    try {
      wine = mapper.readValue(resultSet.getString("data"), Wine.class);
    } catch (IOException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
    return wine;
  }
}
