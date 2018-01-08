package com.liqw.util.basic;


import java.sql.*;
import java.util.*;

/**
 * Created by liqw on 2017/11/22.
 * <p>
 * 简单的数据库操作
 */
public class JdbcUtil {

    public static final String SQLITE_DRIVER_CLASS = "org.sqlite.JDBC";
    public static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String PHOENIX_DRIVER_CLASS = "org.apache.phoenix.jdbc.PhoenixDriver";

    /**
     * @param driver 比如com.mysql.jdbc.Driver
     */
    public static JdbcUtil getInstance(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载Mysql数据库驱动失败！", e);
        }
        return new JdbcUtil();
    }

    /**
     * 获取Connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败！", e);
        }
        return conn;
    }

    /**
     * 关闭ResultSet
     *
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("关闭ResultSet失败！", e);
            }
        }
    }

    /**
     * 关闭Statement
     *
     * @param stmt
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                throw new RuntimeException("关闭Statement失败！", e);
            }
        }
    }

    /**
     * 关闭ResultSet、Statement
     *
     * @param rs
     * @param stmt
     */
    public static void closeStatement(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        closeStatement(stmt);
    }

    /**
     * 关闭PreparedStatement
     *
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(PreparedStatement pstmt) throws SQLException {
        pstmt.close();
    }

    /**
     * 关闭ResultSet、PreparedStatement
     *
     * @param rs
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException {
        rs.close();
        pstmt.close();
    }

    /**
     * 关闭ResultSet、Statement、Connection
     *
     * @param rs
     * @param stmt
     * @param con
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(con);
    }

    /**
     * 关闭Statement、Connection
     *
     * @param stmt
     * @param con
     */
    public static void closeConnection(Statement stmt, Connection con) {
        closeStatement(stmt);
        closeConnection(con);
    }

    /**
     * 关闭Connection
     *
     * @param con
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                throw new RuntimeException("关闭连接失败！", e);
            }
        }
    }

    private static List<Map<String, Object>> resultSetToList(ResultSet rs)
            throws java.sql.SQLException {
        if (rs == null)
            return Collections.EMPTY_LIST;

        ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowData = new HashMap<String, Object>();
        while (rs.next()) {
            rowData = new HashMap<String, Object>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * 执行sql 语句
     *
     * @param sql
     * @return 如果有返回比如查询操作，返回list数据 ；如果没有返回比如删除，返回[{count=update count}]
     */
    public List<Map<String, Object>> excute(Connection connection, String sql) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            boolean hasResultSet = preparedStatement.execute();
            if (hasResultSet) {
                ResultSet resultSet = preparedStatement.getResultSet();
                mapList = resultSetToList(resultSet);
                closeStatement(resultSet, preparedStatement);
            } else {
                Map map = new HashMap();
                map.put("count", preparedStatement.getUpdateCount());
                mapList.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapList;
    }
}
