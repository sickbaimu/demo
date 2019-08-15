package elearning.sql;
import java.sql.*;


class BaseSQL {
    static String url = "jdbc:mysql://localhost:3306/elearning?useSSL=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String jdbc = "com.mysql.cj.jdbc.Driver";
    static String user = "root";
    static String password = "233907";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static void init()throws SQLException,ClassNotFoundException{
            Class.forName(jdbc);
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
    }
    static void closeQuery()throws SQLException{
            rs.close();
            stmt.close();
            conn.close();
    }
    static void closeUpdate()throws SQLException{
        stmt.close();
        conn.close();
    }

}
