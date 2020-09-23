package com.qf.wzx.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbUtils {
    private static String driver;
    private static String url;
    private static String password;
    private static String username;

    static {
        Properties properties = new Properties();
        ClassLoader classLoader = DbUtils.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("db.properties");
        try {
            properties.load(in);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void closeAll(Connection conn, Statement statement,ResultSet resultSet){
        try {
            if(null!= resultSet){
                resultSet.close();
            }
            if(null!=statement){
                statement.close();
            }
            if (null!=conn){
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
