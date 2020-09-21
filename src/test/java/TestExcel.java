import org.junit.Test;
import utils.ImportExcelUtil;

import java.sql.SQLException;

public class TestExcel {
    @Test
    public void importExcel() throws SQLException {
//        String ip = "192.168.88.140";
        String ip = "127.0.0.1";
        ip = "localhost";
        String port = "3306";           //MySQL
        port = "5432";                  //PostgreSQL
        String username = "root";
        username = "postgres";
        String password = "123456";
//        String database = "wodpoc";
        String database = "database";
        database = "postgres";
        String filePath = "/Users/asthmas/Desktop/";
//        ImportExcelUtil.exportFromSQLToExcel(ip, port, database, username, password, filePath);
    }
}
