import java.sql.*;

public class JDBCTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1.配置连接数据库的参数
        Class.forName("com.mysql.cj.jdbc.Driver"); // 加载连接数据库的类
        // SHZ是数据库名称
        String url = "jdbc:mysql://localhost:3306/SHZ-db"; // 指定数据库的位置
        String user = "root"; // 数据库的用户名
        String password = "sunyanhao"; //数据库密码

        // 2.获取数据库和Java的连接对象
        Connection connection = DriverManager.getConnection(url, user, password);

        // 3.定义sql语句
        String sql = "select * from users";

        // 4.将sql语句交给数据库
        PreparedStatement statement = connection.prepareStatement(sql);

        // 5.执行sql语句
        ResultSet rs = statement.executeQuery(); // 查询结果在rs对象中
        while (rs.next()) {
            //先移动再获取数据列
            // 列参数从1开始，使用的方法获取的类型需要和数据库元素类型一致
            // 例如：数据库中第一列是int类型所以用getInt方法，参数为1
            System.out.print("UserID = "+rs.getLong(1));
            System.out.print("\nUserName = "+rs.getString(2));
            System.out.print("\nPassword = "+rs.getString(3));
            System.out.print("\nBalance = "+rs.getDouble(4));
        }
        // 6.关闭连接
        connection.close();
    }
}