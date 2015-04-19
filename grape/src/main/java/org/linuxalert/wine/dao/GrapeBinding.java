package org.linuxalert.wine.dao;


import org.linuxalert.wine.model.Grape;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import org.skife.jdbi.v2.SQLStatement;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Array;
import java.sql.SQLException;

@BindingAnnotation(GrapeBinding.GrapeBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface GrapeBinding {

  class GrapeBinderFactory implements BinderFactory {
    public Binder build(Annotation annotation) {
      return new Binder<GrapeBinding, Grape>() {
        public void bind(SQLStatement statement, GrapeBinding bind, Grape arg) {
          statement.bind("name", arg.getName());
          statement.bind("description", arg.getDescription());
          Array aliasesSqlArray = null;
          try {
            aliasesSqlArray =
                statement.getContext().getConnection()
                    .createArrayOf("text", arg.getAliases().toArray());
          } catch (SQLException e) {
            e.printStackTrace();
            aliasesSqlArray = null;
          }
          statement.bind("aliases", aliasesSqlArray);
        }
      };
    }
  }
}
