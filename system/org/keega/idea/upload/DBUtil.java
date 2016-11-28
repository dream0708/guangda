package org.keega.idea.upload;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Administrator
 */
public class DBUtil {

    // 定义数据库连接参数
   /* public static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=gdgj";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "123";*/
   // private static Connection connection = null;

    @Resource(name="dataSource")
    private DataSource dataSource;
    private  String DRIVER_CLASS_NAME ;
    private  String URL ;
    private  String username ;
    private  String PASSWORD ;

    public void setDRIVER_CLASS_NAME(String DRIVER_CLASS_NAME) {
        this.DRIVER_CLASS_NAME = DRIVER_CLASS_NAME;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    // 注册数据库驱动
  /*  static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("注册失败！");
            e.printStackTrace();
        }
    }*/

    // 获取连接
    public Connection getConn() throws SQLException {
       /* System.out.println(username);
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("注册失败！");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, username, PASSWORD);*/
       return DataSourceUtils.getConnection(dataSource);
    }

    // 关闭连接
    public static void closeConn(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("关闭连接失败！");
                e.printStackTrace();
            }
        }
    }

 /*   //测试
    public static void main(String[] args) throws SQLException {
        System.out.println(DBUtil.getConn());
    }
*/
}