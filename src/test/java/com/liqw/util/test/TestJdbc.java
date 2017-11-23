package com.liqw.util.test;

import com.liqw.util.basic.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by liqw on 2017/11/23.
 */

public class TestJdbc {

    @Test
    public void test() {
        String url = "jdbc:mysql://192.168.1.71:3306/usercenter_new2?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "123456";
        JdbcUtil instance = JdbcUtil.getInstance("com.mysql.jdbc.Driver");
        Connection connection = instance.getConnection(url, user, password);

        String sql = "SELECT ID,APPID,APPKEY,MD5_kEY,AES_kEY,PERMISSION  FROM aPP_iNFO;";
        System.out.println(instance.excute(connection, sql));

        String sql2 = "Delete from aPP_iNFO where ID='1'";
        System.out.println(instance.excute(connection, sql2));
    }
}
