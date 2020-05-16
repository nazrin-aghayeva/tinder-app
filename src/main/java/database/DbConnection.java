package database;

import java.sql.*;

public class DbConnection {
    private final static String URL = "jdbc:postgresql://ec2-52-207-25-133.compute-1.amazonaws.com:5432/d586nu2uql7581";
    private final static String NAME = "cpwtvtnlbkxxbn";
    private final static String PWD = "4b7acde75355e95e887da94d04ff9109ecacc6999d1ce86d54224b162e2273ca";
    private Connection connection = null;


    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, NAME, PWD);
    }


    public Connection connection() {
        if (connection == null) {
            try {
                connection = connect();
            } catch (SQLException e) {
                throw new IllegalStateException();
            }
        }

        return this.connection;
    }
}
