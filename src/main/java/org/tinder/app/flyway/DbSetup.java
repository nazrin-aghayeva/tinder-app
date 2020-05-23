package org.tinder.app.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

public class DbSetup {

  public static void migrate(String path, String user, String password) {
    migrate(path, user, password, false);
  }

  public static void migrate(String path, String user, String password, boolean clean) {
    FluentConfiguration config = new FluentConfiguration()
        .dataSource(path, user, password);
    Flyway flyway = new Flyway(config);
    if (clean) flyway.clean();
    flyway.migrate();
  }

}
