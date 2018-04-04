package com.liqw.util.test;

import com.liqw.util.basic.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by liqw on 2017/11/23.
 */

public class TestJdbc {

    @Test
    public void testMysql() {
        String url = "jdbc:mysql://192.168.1.199:3306/usercenter?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "123456";
        JdbcUtil instance = JdbcUtil.getInstance(JdbcUtil.MYSQL_DRIVER_CLASS);
        Connection connection = instance.getConnection(url, user, password);

        String sql = "SELECT ID,APPID,APPKEY,MD5_kEY,AES_kEY,PERMISSION  FROM aPP_iNFO;";
        System.out.println(instance.excute(connection, sql));

        String sql2 = "Delete from aPP_iNFO where ID='1'";
        System.out.println(instance.excute(connection, sql2));
    }

    @Test
    public void testSqlite() {

        String url = "jdbc:sqlite:./test.db";
        JdbcUtil instance = JdbcUtil.getInstance(JdbcUtil.SQLITE_DRIVER_CLASS);
        Connection connection = instance.getConnection(url, null, null);
        String sql = "DROP TABLE IF EXISTS `COMPANY`;";
//        System.out.println(instance.excute(connection, sql));

        sql = "CREATE TABLE IF NOT EXISTS `COMPANY`  " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " AGE            INT     NOT NULL, " +
                " ADDRESS        CHAR(50), " +
                " SALARY         REAL)";
        System.out.println(instance.excute(connection, sql));

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
        System.out.println(instance.excute(connection, sql));


        sql = "SELECT * FROM COMPANY;";
        List<Map<String, Object>> excute = instance.excute(connection, sql);
        System.out.println(excute);

        sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
        System.out.println(instance.excute(connection, sql));

        sql = "DELETE from COMPANY where ID=1;";
        System.out.println(instance.excute(connection, sql));
    }
}
