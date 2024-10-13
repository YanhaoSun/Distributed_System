import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class DatabaseUtil {
//
//    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/stock";
//    private static final String JDBC_USER = "root";
//    private static final String JDBC_PASSWORD = "123";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//    }
//}

 public class DatabaseUtil {

     public static Connection getConnection() throws SQLException {
         String jdbcUrl = System.getenv("JDBC_URL");
         String jdbcUser = System.getenv("JDBC_USER");
         String jdbcPassword = System.getenv("JDBC_PASSWORD");
         return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
     }
 }

