package com.github.codingdebugallday.elasticjob;

import java.sql.*;
import java.util.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/12 23:16
 * @since 1.0.0
 */
public class JdbcUtil {

    private JdbcUtil() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/just_test?characterEncoding=utf8&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "tse@9527";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /***
     * 调用者只需传入一个sql语句，和一个Object数组。该数组存储的是SQL语句中的占位符
     * DML操作（增删改）
     * 1.获取连接数据库对象
     * 2.预处理
     * 3.执行更新操作
     */
    public static void executeUpdate(String sql, Object... obj) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
    }


    /***
     * DQL查询
     * Result获取数据集
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... obj) {
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            rs = ps.executeQuery();
            // new一个空的list集合用来存放查询结果
            List<Map<String, Object>> list = new ArrayList<>();
            // 获取结果集的列数
            int count = rs.getMetaData().getColumnCount();
            // 对结果集遍历每一条数据是一个Map集合，列是k,值是v
            while (rs.next()) {
                // 一个空的map集合，用来存放每一行数据
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < count; i++) {
                    Object ob = rs.getObject(i + 1);
                    String key = rs.getMetaData().getColumnName(i + 1);
                    map.put(key, ob);
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return Collections.emptyList();
    }
}