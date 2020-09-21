package utils;//package utils;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.poi.xssf.usermodel.*;
//import java.io.*;
//import java.sql.*;
//import java.util.*;
//public class ImportUtil {
//
//    private static final Logger log = LoggerFactory.getLogger(ImportUtil.class);
//
//    private static final String SELECT_SQL = "SELECT * FROM %s";
//    /**
//     * 将数据库中的数据导出到excel文件中
//     * 导出文件名为: 数据库名_export.xls
//     * @param ip 数据库ip地址
//     * @param port 数据库端口
//     * @param database 数据库名
//     * @param username 连接用户名
//     * @param password 连接密码
//     * @param filePath 导出文件保存路径 -- 不含文件名
//     * @param excludeTables 不作导出的数据库表名
//     * @throws SQLException
//     */
//    public static void exportFromMySQLToExcel(String ip, String port, String database,
//                                              String username, String password, String filePath, String ...excludeTables) throws SQLException {
//
//        Connection connection = MysqlConnection.getConnection(ip, port, database, username, password);
//        if(connection == null){
//            log.error("获取数据库连接失败");
//            throw new RuntimeException("数据库连接失败");
//        }
//
//        if(filePath == null){
//            log.error("文件导出路径不能为空");
//            throw new RuntimeException("文件导出路径不能为空");
//        }
//
//        ArrayList<String> excludes = new ArrayList<>(Arrays.asList(excludeTables));
//        //超出65536范围添加新表 存储后续数据
//        int index=1;
//        //设置临时计数变量
//        int tempVar=0;
//        //是否超出范围需要建表flag
//        boolean flag=false;
//        //获取数据库所有表信息
//        DatabaseMetaData metaData = connection.getMetaData();
//        ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
//
//        //创建工作簿
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        while(rs.next()){
//            String tableName = rs.getString("TABLE_NAME");
//            if(!excludes.contains(tableName)){
//                //获取表中的数据
//                Map<Integer, Map<String, Object>> datas = getAllDataFromTable(tableName, (Connection) connection);
//                if(datas == null) break;
//
//                //获取字段名和类型
//                Map<String, String> nameType = getDataNameAndType(tableName, connection);
//                if(nameType == null) break;
//
//                //创建一张表
//                HSSFSheet sheet = workbook.createSheet(tableName);
//                //初始化第一行 显示表中的列
//                Object[] columnNames = nameType.keySet().toArray();
//                HSSFRow firstRow = sheet.createRow(0);
//                firstRow.createCell( 0).setCellValue("行数");
//                //填充表名
//                for(int i = 0; i < columnNames.length; i++){
//                    firstRow.createCell(i + 1).setCellValue((String)columnNames[i]);
//                }
//                //向工作簿写入数据
//                for(int row = 0; row < datas.size(); row++){
//                    Map<String, Object> data = datas.get(row);
//                    HSSFRow rows = sheet.createRow(row + 1);
//                    //设置第一列数据为数据行数
//                    rows.createCell(0).setCellValue(row + 1);
//                    int col=0;
//                    for(col = 0,tempVar=col; col < data.size(); col++){
//                        Object value = data.get((String) columnNames[col]);
//                        if(value == null) break;
//                        rows.createCell(col + 1).setCellValue(value.toString());
//                        }
//
//
//                }
//            }
//        }
//
//        //保存工作簿
//        try {
//            File file = new File(filePath);
//            if(!file.exists()) file.mkdirs();
//            filePath = filePath + "\\" + database + "_export.xls";
//            FileOutputStream fos = new FileOutputStream(filePath);
//            workbook.write(fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            log.error(e.getLocalizedMessage());
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
////获取table
//    private static Map<Integer, Map<String, Object>> getAllDataFromTable(String tableName, Connection conn){
//        LinkedHashMap<Integer, Map<String, Object>> res = null;
//        PreparedStatement statement = null;
//
//        try {
//            res = new LinkedHashMap<>();
//
//            //根据表名查询表中所有数据
//            statement = conn.prepareStatement(String.format(SELECT_SQL, tableName));
//            ResultSet rs = statement.executeQuery();
//
//            //获取表数据结果集
//            ResultSetMetaData metaData = rs.getMetaData();
//
//            //获取表中行总数
//            int columnCount = metaData.getColumnCount();
//            int count = 0;
//
//            while(rs.next()){
//                // 数据字段名，数据
//                LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
//                for(int i = 1; i <= columnCount; i++){
//
//                    String columnName = metaData.getColumnName(i);
//                    Object obj = rs.getObject(i);
//                    tmp.put(columnName, obj);
//                }             //数据字段名，数据
//                //      第几行，<String,Object>
//                res.put(count++, tmp);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            log.error("查询数据库表: " + tableName + " 信息失败");
//            log.error(e.getMessage());
//            return null;
//        } finally {
//            MysqlConnection.close(statement);
//        }
//
//        return res;
//    }
//    //获取数据在表中类型和实际存储类型
//    private static Map<String, String> getDataNameAndType(String tableName, Connection conn){
//        LinkedHashMap<String, String> res = null;
//        PreparedStatement statement = null;
//
//        try {
//            statement = conn.prepareStatement(String.format(SELECT_SQL, tableName));
//            ResultSet rs = statement.executeQuery();
//
//            res = new LinkedHashMap<>();
//            //获取数据
//            ResultSetMetaData metaData = rs.getMetaData();
//            //获取行数
//            int count = metaData.getColumnCount();
//
//            for(int i = 1; i <= count; i++){
//                res.put(metaData.getColumnName(i), metaData.getColumnClassName(i));
//                System.out.println(res);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            log.error("查询数据库表: " + tableName + " 信息失败");
//            log.error(e.getMessage());
//            return null;
//        } finally {
//            MysqlConnection.close(statement);
//        }
//
//        return res;
//    }
//
//    //表是否存在
//    private static boolean isTableExist(Connection conn, String tableName){
//        try {
//            ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            log.error("获取数据库metaData失败");
//            log.error(e.getMessage());
//            return false;
//        }
//    }
//
//}
//
