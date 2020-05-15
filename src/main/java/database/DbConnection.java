package database;

import java.sql.*;

public class DbConnection {
    private final static String URL = "jjdbc:postgresql://ec2-52-207-25-133.compute-1.amazonaws.com:5432/d586nu2uql7581";
    private final static String NAME = "cpwtvtnlbkxxbn";
    private final static String PWD = "4b7acde75355e95e887da94d04ff9109ecacc6999d1ce86d54224b162e2273ca";

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, NAME, PWD);
        String SQL = "SELECT * FROM users";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            int id = rset.getInt("id");
            int age = rset.getInt("age");
            String name = rset.getString("name");
            System.out.printf("%5d : %5d : %20s\n", id, age, name);
        }
    }
}
