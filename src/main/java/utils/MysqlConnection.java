package utils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class MysqlConnection {
    private static final Logger logger = LoggerFactory.getLogger(MysqlConnection.class);

    private static final String URL = "jdbc:mysql://%s:%s/%s?characterEncoding=UTF-8&characterSetResults=UTF-8&zeroDateTimeBehavior=convertToNull";

    static Connection getConnection(String ip, String port, String database, String username, String password){
        String driver = "com.mysql.jdbc.Driver";
        String url = String.format(URL, ip, port, database);
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("找不到MySQL数据库驱动类");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取数据库连接失败");
            return null;
        }
    }

    static void close(Statement statement){
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("关闭statement失败");
                logger.error(e.getMessage());
            }
        }
    }

    static void close(Connection connection){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("关闭数据库连接失败");
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    static void close(Statement statement, Connection connection){
        close(statement);
        close(connection);
    }

}
