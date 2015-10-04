package org.linuxalert.wine.wine.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.linuxalert.wine.wine.model.Wine;
import org.postgresql.util.PGobject;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.SQLException;

@BindingAnnotation(WineBinding.WineBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface WineBinding {

  class WineBinderFactory implements BinderFactory {
    public Binder build(Annotation annotation) {
      return new Binder<WineBinding, Wine>() {
        public void bind(SQLStatement statement, WineBinding bind, Wine wine) {
          statement.bind("id", wine.getId());
          ObjectMapper mapper = new ObjectMapper();
          PGobject dataObject = new PGobject();
          dataObject.setType("jsonb");
          try {
            dataObject.setValue(mapper.writeValueAsString(wine));
            statement.bind("data", dataObject);
          } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
          }
        }
      };
    }
  }
}
