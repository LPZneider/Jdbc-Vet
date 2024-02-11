package org.lpzneider.veterinaria.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ConexionBaseDatos {
    private  static String url = "jdbc:mysql://localhost:3306/veterinaria?serverTimezone=America/Bogota";
    private  static String username = "root";
    private  static String password = "sasa";

    private static BasicDataSource pool;

    private ConexionBaseDatos() {
    }

    public static BasicDataSource getInstace() {
        if (pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(8);
        }
        return pool;
    }

    public static Connection getConnetion () throws SQLException {
        return getInstace().getConnection();
    }
}
